package com.tong.tong2016.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tong.baselibrary.common.bases.BasePagerAdapter;
import com.tong.tong2016.R;
import com.tong.tong2016.ui.fragments.FindFragment_;
import com.tong.tong2016.ui.fragments.IndexFragment_;
import com.tong.tong2016.ui.fragments.InfoFragment_;
import com.tong.tong2016.ui.fragments.SubscribeFragment_;

/**
 * Created by Martin on 2016/5/5.
 */
public class MainTabAdapter extends BasePagerAdapter {

    public MainTabAdapter(FragmentManager fm) {
        super(fm,new int[]{R.drawable.selector_ic_main_tab_index,
                           R.drawable.selector_ic_main_tab_subscribe,
                           R.drawable.selector_ic_main_tab_find,
                           R.drawable.selector_ic_main_tab_info},
                new String[]{"首页","关注","发现","我的"},
                new Fragment[]{new IndexFragment_(),
                               new SubscribeFragment_(),
                               new FindFragment_(),
                               new InfoFragment_()});
    }
}
