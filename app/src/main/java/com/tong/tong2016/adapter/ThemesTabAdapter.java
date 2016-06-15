package com.tong.tong2016.adapter;

import android.support.v4.app.FragmentManager;

import com.tong.baselibrary.common.bases.BasePagerAdapter;
import com.tong.tong2016.ui.fragments.PageFragment;

import java.util.List;

/**
 * Created by Martin on 2016/6/8.
 */
public class ThemesTabAdapter extends BasePagerAdapter {

    public ThemesTabAdapter(FragmentManager fm,List<String> titles,List<PageFragment> pageFragments) {
        super(fm,titles.toArray(new String[titles.size()]), pageFragments.toArray(new PageFragment[pageFragments.size()]));
    }

    public void updateThemes(List<String> titles,List<PageFragment> pageFragments){
        super.titles = titles.toArray(new String[titles.size()]);
        super.fragments = pageFragments.toArray(new PageFragment[pageFragments.size()]);
        super.notifyDataSetChanged();
    }

}
