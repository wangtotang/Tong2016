package com.tong.tong2016.ui.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tong.tong2016.R;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.listener.AnimateFirstDisplayListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_article)
public class QuestionActivity extends BaseNavActivity {

    @ViewById
    FloatingActionButton fab;
    @ViewById
    ImageView headImg;
    @ViewById
    TextView tvCopyright;
    @ViewById
    WebView wbRead;

    private String url;
    private String image_url;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    
    @AfterViews
    void init(){

        initBackup();

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        image_url = intent.getStringExtra("image_url");

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

        showArticle(url,image_url);
    }

    @UiThread
    void showArticle(String url,String image_url){
        wbRead.loadUrl(url);
        imageLoader.displayImage(image_url, headImg, Constants.options, animateFirstListener);
        dismissProgressDialog();
    }

}
