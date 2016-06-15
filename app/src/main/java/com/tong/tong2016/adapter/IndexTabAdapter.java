package com.tong.tong2016.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tong.baselibrary.common.bases.BasePagerAdapter;
import com.tong.tong2016.ui.fragments.FreshFragment_;
import com.tong.tong2016.ui.fragments.HotFragment_;

/**
 * Created by Martin on 2016/5/5.
 */
public class IndexTabAdapter extends BasePagerAdapter {

    public IndexTabAdapter(FragmentManager fm) {
        super(fm, new String[]{"新鲜","热门"},
                new Fragment[]{new FreshFragment_(),
                               new HotFragment_(),});
    }
}
