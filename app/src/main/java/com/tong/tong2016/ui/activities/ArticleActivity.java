package com.tong.tong2016.ui.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tong.baselibrary.common.util.NetworkStateUtil;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.bean.db.MyUser;
import com.tong.tong2016.bean.db.Post;
import com.tong.tong2016.bean.db.Total;
import com.tong.tong2016.bean.json.Article;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.listener.AnimateFirstDisplayListener;
import com.tong.tong2016.storage.TongPreferences;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

@EActivity(R.layout.activity_article)
public class ArticleActivity extends BaseNavActivity {

    @ViewById
    FloatingActionButton fab;
    @ViewById
    ImageView headImg;
    @ViewById
    TextView tvCopyright;
    @ViewById
    WebView wbRead;
    @ViewById
    Button btnView;
    @ViewById
    Button btnLike;
    @ViewById
    Button btnColllect;
    @ViewById
    Button btnComment;
    @ViewById
    LinearLayout llArticle;

    private String ids;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private String shareUrl = null;
    private String title = null;
    private Post post = new Post();
    private int views = 0;
    private int likes = 0;
    private int collects = 0;
    private int comments = 0;
    MyUser myUser = new MyUser();
    boolean liked = false;
    boolean collected = false;
    Total total = new Total();

    @AfterViews
    void init(){

        initBackup();

        Intent intent = getIntent();
        ids = intent.getStringExtra("ids");

        //能够和js交互
        wbRead.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        wbRead.getSettings().setBuiltInZoomControls(false);
        //缓存
        wbRead.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        wbRead.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        wbRead.getSettings().setAppCacheEnabled(false);
        //不调用第三方浏览器即可进行页面反应
        wbRead.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                wbRead.loadUrl(url);
                return true;
            }
        });
        // 设置是否加载图片，true不加载，false加载图片
        wbRead.getSettings().setBlockNetworkImage(false);

        if ( !NetworkStateUtil.networkConneted(this)){
            llArticle.setVisibility(View.GONE);
        }else {
            btnView.setText(views+"");
            btnLike.setText(likes+"");
            btnColllect.setText(collects+"");
            btnComment.setText(comments+"");
            btnLike.setActivated(liked);
            btnColllect.setActivated(collected);
            initData();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(title!=null&&shareUrl!=null){
                    Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
                    String shareText = title + " "+ shareUrl + " 分享自 同格.";
                    shareIntent.putExtra(Intent.EXTRA_TEXT,shareText);
                    startActivity(Intent.createChooser(shareIntent,"分享到"));
                }
            }
        });
        btnComment.setVisibility(View.GONE);
    }

    @Background(id = "article")
    void initData(){
        showProgressDialog("加载中...");
        try {
            Article article = TongApplication.getNetService()
                    .getArticle(ids).execute().body();
            if(article!=null) {
                post.setIds(ids);
                myUser.setUserId(getInfo());
                total.setIds(ids);
                total.setUserId(getInfo());
                queryData();
                showArticle(article);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void showArticle(Article article){
        shareUrl = article.getShareUrl();
        title = article.getTitle();
        // 如果没有body，则加载share_url中内容
        if (TextUtils.isEmpty(article.getBody())){
            wbRead.loadUrl(article.getShareUrl());
            headImg.setImageResource(R.drawable.ic_noimage);
            headImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // body不为null
        } else {
            imageLoader.displayImage(article.getImage(), headImg, Constants.options, animateFirstListener);
            tvCopyright.setText(article.getImageSource());

            // 不再选择加载网络css，而是加载本地assets文件夹中的css
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">";

            // 如果没有去除这个div，那么整个网页的头部将会出现一部分的空白区域

            String content = article.getBody().replace("<div class=\"img-place-holder\">", "");

            String parseByTheme = "<body>\n";
            String html = "<!DOCTYPE html>\n"
                    + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                    + "<head>\n"
                    + "\t<meta charset=\"utf-8\" />\n</head>\n"
                    + parseByTheme
                    + css
                    + content
                    + "\n<body>";
            wbRead.loadDataWithBaseURL("x-data://base",html,"text/html","utf-8",null);
            llArticle.setVisibility(View.VISIBLE);
            dismissProgressDialog();
        }
    }

    private String getInfo(){
        TongPreferences tongPreferences = TongPreferences.getPreferencesUtil(this);
        String platform = tongPreferences.getPlatform();
        Platform plat = ShareSDK.getPlatform(platform);
        return plat.getDb().getUserId();

    }

    @Click({R.id.btn_like,R.id.btn_colllect,R.id.btn_comment})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_like:
                if(btnLike.isActivated()){
                    btnLike.setActivated(false);
                    total.setLiked(false);
                    likes = likes - 1;
                    btnLike.setText(likes+"");
                }else{
                    btnLike.setActivated(true);
                    total.setLiked(true);
                    likes = likes + 1;
                    btnLike.setText(likes+"");
                }
                total.update(this);
                post.setLikes(likes);
                post.update(this);
                break;
            case R.id.btn_colllect:
                if(btnColllect.isActivated()){
                    btnColllect.setActivated(false);
                    total.setCollectd(false);
                    collects = collects - 1;
                    btnColllect.setText(collects+"");
                }else{
                    btnColllect.setActivated(true);
                    total.setCollectd(true);
                    collects = collects + 1;
                    btnColllect.setText(collects+"");
                }
                total.update(this);
                post.setCollects(collects);
                post.update(this);
                break;
            case R.id.btn_comment:
                break;
        }
    }

    @UiThread
    void queryData(){
        BmobQuery<Post> queryPost = new BmobQuery<>();
        queryPost.addWhereEqualTo("ids",ids);
        queryPost.findObjects(this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                  if(list.size()>0){
                      post = list.get(0);
                      views = post.getViews();
                      likes = post.getLikes();
                      collects = post.getCollects();
                      comments = post.getComments();
                      post.increment("views");
                      post.update(ArticleActivity.this);
                      btnView.setText(views+"");
                      btnLike.setText(likes+"");
                      btnColllect.setText(collects+"");
                      btnComment.setText(comments+"");
                  }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        BmobQuery<MyUser> queryUser = new BmobQuery<>();
        queryUser.addWhereEqualTo("userId",getInfo());
        queryUser.findObjects(this, new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                if(list.size()>0){
                    myUser = list.get(0);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        BmobQuery<Total> queryTotal = new BmobQuery<>();
        queryTotal.addWhereEqualTo("ids",ids);
        queryTotal.addWhereEqualTo("userId",getInfo());
        queryTotal.findObjects(this, new FindListener<Total>() {
            @Override
            public void onSuccess(List<Total> list) {
                if(list.size()>0){
                    total = list.get(0);
                    liked = total.getLiked();
                    collected = total.getCollectd();
                    btnLike.setActivated(liked);
                    btnColllect.setActivated(collected);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        post.setViews(views);
        post.setTitle(title);
        post.setLikes(likes);
        post.setCollects(collects);
        post.setComments(comments);
        post.save(this);
        total.setLiked(liked);
        total.setCollectd(collected);
        total.save(this);
        super.onDestroy();
    }
}
