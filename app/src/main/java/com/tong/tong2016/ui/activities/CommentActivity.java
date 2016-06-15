package com.tong.tong2016.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tong.baselibrary.common.bases.BaseAppBarStateChangeListener;
import com.tong.baselibrary.common.util.CacheUtil;
import com.tong.baselibrary.common.widget.CircleImageView;
import com.tong.tong2016.R;
import com.tong.tong2016.adapter.CommentStepperAdapter;
import com.tong.tong2016.bean.db.Comment;
import com.tong.tong2016.bean.db.User;
import com.tong.tong2016.storage.TongPreferences;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;

@EActivity(R.layout.activity_comment)
public class CommentActivity extends BaseNavActivity {

    @ViewById
    AppBarLayout appbar;

    @ViewById
    CollapsingToolbarLayout ctlInfo;

    @ViewById
    ImageView ivActivityCommentAppbar;

    @ViewById
    CircleImageView civCommentAvatar;

    @ViewById
    RecyclerView rv;

    @ViewById
    TextView tvUserName;

    List<Comment> comments = new ArrayList<>();
    CommentStepperAdapter adapter;
    TongPreferences tongPreferences;
    Platform plat;
    User user;

    @AfterViews
    void init(){
        initBackup();
        appbar.addOnOffsetChangedListener(new BaseAppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state==State.COLLAPSED){
                    ctlInfo.setTitle("我的评论");
                }else if(state==State.IDLE){
                    ctlInfo.setTitle("");
                }
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentStepperAdapter(comments);
        rv.setAdapter(adapter);
        updateInfo();
    }

    private void updateInfo(){
        tongPreferences = TongPreferences.getPreferencesUtil(this);
        String platform = tongPreferences.getPlatform();
        plat = ShareSDK.getPlatform(platform);
        initData();
    }

    /*初始化用户数据*/
    private void initData(){
        if(plat != null){
            if(user==null){
                PlatformDb db = plat.getDb();
                user = new User(db.getUserId(), db.getUserIcon(), db.getUserName(),
                        "m".equals(db.getUserGender())? User.Gender.MALE: User.Gender.FEMALE);
            }
            tvUserName.setText(user.getUserName());
            comments.add(new Comment("为什么一眼就能辨认出人物的形象？","说的很好，赞！","2016-06-06"));
            adapter.updateCommentList(comments);
            loadIcon();
        }
    }

    /**加载用户登陆后返回的头像*/
    @Background(id = "info")
    void loadIcon() {
        File cacheFile = CacheUtil.getDiskCacheFile(this,user.getUserId());
        Bitmap bitmap = CacheUtil.getBitmapFromMemCache(cacheFile);
        if (bitmap != null) {
            setImage(civCommentAvatar,bitmap);
        } else {
            try {
                String icon = user.getUserIcon();
                URL url = new URL(icon);
                bitmap = BitmapFactory.decodeStream(url.openStream());
            } catch (IOException e){
                e.printStackTrace();
            }
            if(bitmap != null){
                setImage(civCommentAvatar,bitmap);
                CacheUtil.addBitmapToMemoryCache(cacheFile,bitmap);
            }else{
                showToast("获取图片失败");
            }
        }
    }

    @UiThread
    void setImage(ImageView imageView, Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }


}
