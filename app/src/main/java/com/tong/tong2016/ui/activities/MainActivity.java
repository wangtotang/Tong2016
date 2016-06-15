package com.tong.tong2016.ui.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tong.tong2016.R;
import com.tong.tong2016.adapter.MainTabAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.sharesdk.framework.ShareSDK;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    ViewPager vpMain;

    @ViewById
    TabLayout tlMain;

    private MainTabAdapter mainAdapter;

    @AfterViews
    public void init(){
        ShareSDK.initSDK(this);
        setUpViewPager();
    }

    /**
     * 设置Viewpager
     */
    private void setUpViewPager() {
        vpMain.setOffscreenPageLimit(4);//此方法可以有效解决滑动卡顿
        mainAdapter = new MainTabAdapter(getSupportFragmentManager());
        vpMain.setAdapter(mainAdapter);
        tlMain.setupWithViewPager(vpMain);
        setUpTab();
    }

    /**
     * 设置tabLayout
     */
    private void setUpTab() {

        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_custom_tab, null);
            final TextView tab = (TextView) view.findViewById(R.id.tv_tab);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab_icon);
            tab.setText(mainAdapter.getPageTitle(i));
            imageView.setBackgroundResource(mainAdapter.getBackgroundResource(i));
            if(i == 0) {
                view.setSelected(true);
            }
            tlMain.getTabAt(i).setCustomView(view);
        }

    }

    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }
}
