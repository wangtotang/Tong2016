package com.tong.tong2016.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.tong.baselibrary.common.bases.BaseFragment;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.adapter.FindListAdapter;
import com.tong.tong2016.bean.json.Other;
import com.tong.tong2016.bean.json.Theme;
import com.tong.tong2016.ui.activities.SearchActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 2016/5/5.
 */
@EFragment(R.layout.fragment_find)
public class FindFragment extends BaseFragment {

    @ViewById
    RecyclerView rv;
    @ViewById
    ImageButton ibSearch;

    FindListAdapter adapter;
    List<Other> others = new ArrayList<>();

    @AfterViews
    void init(){
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SearchActivity_.class);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        adapter = new FindListAdapter(getActivity(),others);
        rv.setAdapter(adapter);
        getTheme();
    }

    @Background(id = "theme")
    void getTheme(){
        try {
            Theme theme = TongApplication.getNetService().getThemes().execute().body();
            others = theme.getOthers();
            updateState(others);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void updateState(List<Other> others){
        adapter.updateThemes(others);
    }

}
