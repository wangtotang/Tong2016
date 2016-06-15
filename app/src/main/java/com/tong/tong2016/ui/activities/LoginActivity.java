package com.tong.tong2016.ui.activities;

import android.text.TextUtils;
import android.view.View;

import com.tong.baselibrary.common.bases.BaseActivity;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.storage.TongPreferences;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    TongPreferences tongPreferences;

    @AfterViews
    void init(){
        ShareSDK.initSDK(TongApplication.getInstance());
        tongPreferences = TongPreferences.getPreferencesUtil(this);
    }

    @Click({R.id.btn_login_qq,R.id.btn_login_weibo,R.id.btn_login_wechat})
    void login(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_login_qq:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                authorize(qq);
                break;
            case R.id.btn_login_weibo:
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                authorize(sina);
                break;
            case R.id.btn_login_wechat:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                authorize(wechat);
                break;
        }
    }


    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }
        if(plat.isAuthValid()){
            plat.removeAccount(true);
        }
        //关闭SSO授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new LoginListener());
        plat.showUser(null);
    }

    public void checkLoginState(){
        String platform = tongPreferences.getPlatform();
        if(!TextUtils.isEmpty(platform)){
            Platform plat = ShareSDK.getPlatform(platform);
            if(plat!=null&&plat.isAuthValid()){
                startActivity(MainActivity_.class);
                finish();
            }
        }
    }

    @UiThread
    void onSuccess(String plat) {
        showProgressDialog("正在登陆中...");
        tongPreferences.setPlatform(plat);
        startActivity(MainActivity_.class);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        checkLoginState();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    private class LoginListener implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> info) {
            if (action == Platform.ACTION_USER_INFOR) {
                onSuccess(platform.getName());
            }
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    }

}
