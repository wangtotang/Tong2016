package com.tong.tong2016.ui.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tong.baselibrary.common.bases.BaseFragment;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.adapter.ThemesTabAdapter;
import com.tong.tong2016.bean.event.ThemesEvent;
import com.tong.tong2016.bean.json.Other;
import com.tong.tong2016.bean.json.Theme;
import com.tong.tong2016.storage.TongPreferences;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 2016/5/5.
 */
@EFragment(R.layout.fragment_subscribe)
public class SubscribeFragment extends BaseFragment {

    @ViewById
    TabLayout tabLayout;
    @ViewById
    ViewPager viewpager;

    public static final String IDS = "ids";
    List<Other> themes = new ArrayList<>();
    TongPreferences preferences;
    ThemesTabAdapter adapter;
    Theme theme;
    List<PageFragment> pageFragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    @AfterViews
    void init(){
        preferences = TongPreferences.getPreferencesUtil(getActivity());
        preferences.setThemeById("12",true);
        viewpager.setOffscreenPageLimit(12);
        adapter = new ThemesTabAdapter(getActivity().getSupportFragmentManager(),titles,pageFragments);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        getTheme();
    }

    @Background(id = "theme2")
    void getTheme(){
        try {
            theme = TongApplication.getNetService().getThemes().execute().body();
            updateThemes(theme.getOthers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onThemesEvent(ThemesEvent event){
        updateThemes(theme.getOthers());
    }

    @UiThread
    void updateThemes(List<Other> others){
        themes = new ArrayList<>();
        pageFragments = new ArrayList<>();
        titles = new ArrayList<>();
        for (Other other : others) {
            if(preferences.themeById(other.getId())){
                themes.add(other);
                pageFragments.add(new PageFragment_.FragmentBuilder_().arg(IDS,other.getId()).build());
                titles.add(other.getName().replace("日报",""));
            }
        }
        adapter.updateThemes(titles,pageFragments);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
