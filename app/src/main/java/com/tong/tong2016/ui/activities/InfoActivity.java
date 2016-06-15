package com.tong.tong2016.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import com.tong.baselibrary.common.util.CacheUtil;
import com.tong.baselibrary.common.widget.CircleImageView;
import com.tong.tong2016.R;
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

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;

@EActivity(R.layout.activity_info)
public class InfoActivity extends BaseNavActivity {

    @ViewById
    CircleImageView civInfoAvatar;
    @ViewById
    TextView tvUserName;
    @ViewById
    TextView tvUserSex;
    @ViewById
    TextView tvUserDescribe;

    TongPreferences tongPreferences;
    Platform plat;
    User user;

    @AfterViews
    void init(){
        initBackup();
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
            tvUserSex.setText(user.getUserGender()== User.Gender.FEMALE?"女":"男");
            tvUserDescribe.setText("你没有自我介绍喔。");
            loadIcon();
        }
    }

    /**加载用户登陆后返回的头像*/
    @Background(id = "info")
    void loadIcon() {
        File cacheFile = CacheUtil.getDiskCacheFile(this,user.getUserId());
        Bitmap bitmap = CacheUtil.getBitmapFromMemCache(cacheFile);
        if (bitmap != null) {
            setImage(civInfoAvatar,bitmap);
        } else {
            try {
                String icon = user.getUserIcon();
                URL url = new URL(icon);
                bitmap = BitmapFactory.decodeStream(url.openStream());
            } catch (IOException e){
                e.printStackTrace();
            }
            if(bitmap != null){
                setImage(civInfoAvatar,bitmap);
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
