package com.tong.baselibrary.common.bases;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Martin on 2016/5/5.
 */
public class BasePagerAdapter extends FragmentStatePagerAdapter{

    protected int[] resId;
    protected String[] titles;
    protected Fragment[] fragments;

    public BasePagerAdapter(FragmentManager fm, String[] titles, Fragment[] fragments){
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    public BasePagerAdapter(FragmentManager fm,int[] resId, String[] titles, Fragment[] fragments){
        this(fm,titles,fragments);
        this.resId = resId;
    }

    @Override
    public int getCount() {
        return fragments!=null?fragments.length:0;
    }

    @Override
    public Fragment getItem(int i) {
        return (fragments!=null&&fragments.length!=0)?fragments[i]:null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public int getBackgroundResource(int position) {
        return resId[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
