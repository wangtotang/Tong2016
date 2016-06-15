package com.tong.tong2016.ui.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tong.baselibrary.common.bases.BaseFragment;
import com.tong.tong2016.R;
import com.tong.tong2016.adapter.IndexTabAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Martin on 2016/5/5.
 */
@EFragment(R.layout.fragment_index)
public class IndexFragment extends BaseFragment {

    @ViewById
    TabLayout tlIndex;

    @ViewById
    ViewPager vpIndex;

    private IndexTabAdapter indexAdapter;

    @AfterViews
    public void init(){
        setUpViewPager();
    }

    /**
     * 设置Viewpager
     */
    private void setUpViewPager() {
        vpIndex.setOffscreenPageLimit(2);
        indexAdapter = new IndexTabAdapter(getActivity().getSupportFragmentManager());
        vpIndex.setAdapter(indexAdapter);
        tlIndex.setupWithViewPager(vpIndex);
    }

}
