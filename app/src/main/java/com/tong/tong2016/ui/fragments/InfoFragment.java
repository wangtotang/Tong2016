package com.tong.tong2016.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tong.baselibrary.common.bases.BaseAppBarStateChangeListener;
import com.tong.baselibrary.common.bases.BaseFragment;
import com.tong.baselibrary.common.util.CacheUtil;
import com.tong.baselibrary.common.widget.CircleImageView;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.bean.db.MyUser;
import com.tong.tong2016.bean.db.User;
import com.tong.tong2016.storage.TongPreferences;
import com.tong.tong2016.ui.activities.CollectActivity_;
import com.tong.tong2016.ui.activities.CommentActivity_;
import com.tong.tong2016.ui.activities.InfoActivity_;
import com.tong.tong2016.ui.activities.LoginActivity_;
import com.tong.tong2016.ui.activities.ReadActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Martin on 2016/5/5.
 */
@EFragment(R.layout.fragment_info)
public class InfoFragment extends BaseFragment {

    @ViewById
    AppBarLayout appbar;
    @ViewById
    CollapsingToolbarLayout ctlInfo;
    @ViewById
    ImageView ivFragmentFindAppbar;
    @ViewById
    CircleImageView civInfoAvatar;
    @ViewById
    TextView tvInfoName;
    @ViewById
    LinearLayout llHistory;

    TongPreferences tongPreferences;
    Platform plat;
    User user;
    boolean had = false;

    @AfterViews
    void init(){
        appbar.addOnOffsetChangedListener(new BaseAppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if(state==State.COLLAPSED){
                    ctlInfo.setTitle("我的资料");
                }else if(state==State.IDLE){
                    ctlInfo.setTitle("");
                }
            }
        });
        updateInfo();
        llHistory.setVisibility(View.GONE);
    }

    @Click({R.id.rl_user_info,R.id.rl_user_comment,R.id.rl_user_read,R.id.rl_user_collect,R.id.rl_more_logout})
    void infoButton(View view){

        int id = view.getId();
        switch (id){
            case R.id.rl_user_info:
                startActivity(InfoActivity_.class);
                break;
            case R.id.rl_user_comment:
                startActivity(CommentActivity_.class);
                break;
            case R.id.rl_user_read:
                startActivity(ReadActivity_.class);
                break;
            case R.id.rl_user_collect:
                startActivity(CollectActivity_.class);
                break;
            case R.id.rl_more_logout:
                plat.removeAccount(true);
                tongPreferences.setPlatform("");
                startActivity(LoginActivity_.class);
                getActivity().finish();
                break;
        }

    }

    private void updateInfo(){
        tongPreferences = TongPreferences.getPreferencesUtil(getContext());
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
            saveUser(user.getUserId());
            tvInfoName.setText(user.getUserName());
            loadIcon();
        }
    }

    @Background(id = "user")
    void saveUser(String userId){
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("userId",userId);
        query.findObjects(getActivity(), new FindListener<MyUser>() {
            @Override
            public void onSuccess(List<MyUser> list) {
                if(list.size()>0){
                    had = true;
                }else {
                    had = false;
                }
            }

            @Override
            public void onError(int i, String s) {}
        });
        if(!had){
            final MyUser myUser = new MyUser();
            myUser.setUserId(userId);
            myUser.save(TongApplication.getInstance());
        }
    }

    /**加载用户登陆后返回的头像*/
    @Background(id = "info")
    void loadIcon() {
        File cacheFile = CacheUtil.getDiskCacheFile(getContext(),user.getUserId());
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
    void setImage(ImageView imageView,Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

}
