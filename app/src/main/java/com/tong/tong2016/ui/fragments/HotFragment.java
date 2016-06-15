package com.tong.tong2016.ui.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tong.baselibrary.common.bases.BaseFragment;
import com.tong.baselibrary.common.util.NetworkStateUtil;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.adapter.ArticleCardAdapter;
import com.tong.tong2016.bean.db.News;
import com.tong.tong2016.bean.json.Article;
import com.tong.tong2016.bean.json.Hot;
import com.tong.tong2016.bean.json.Recent;
import com.tong.tong2016.util.ParseArticleUtil;

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
@EFragment(R.layout.fragment_hot)
public class HotFragment extends BaseFragment {

    @ViewById
    SwipeRefreshLayout srl;

    @ViewById
    RecyclerView rv;

    List<Recent> recentList = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    ArticleCardAdapter mAdapter;

    @AfterViews
    void init(){

        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));

        mAdapter = new ArticleCardAdapter(getActivity(),newsList);
        rv.setAdapter(mAdapter);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doRefresh();
            }
        });
        srl.setColorSchemeResources(R.color.colorPrimary);

        if (true){
            doRefresh();
        }
    }

    /**
     * 刷新内容页
     */
    private void doRefresh() {
        if (srl != null) {
            srl.setRefreshing(true);
        }
        if ( !NetworkStateUtil.networkConneted(getActivity())){
            srl.setRefreshing(false);
        }else {
            getHot();
        }
    }

    @Background(id = "hot")
    void getHot(){
        try {
            Hot hot = TongApplication.getNetService().getHot().execute().body();
            if(hot!=null) {
                recentList = hot.getRecent();
                getArticle(recentList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background(id = "article2")
    void getArticle(List<Recent> recentList){
        newsList = new ArrayList<>();
        for (Recent recent : recentList) {
            try {
                Article article = TongApplication.getNetService()
                        .getArticle(recent.getNewsId()).execute().body();
                newsList.add(ParseArticleUtil.convertToNews(ParseArticleUtil.createPair(article,ParseArticleUtil.getArticleDocument(article.getBody()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateState(newsList);
    }

    @UiThread
    void updateState(List<News> newsList){
        srl.setRefreshing(false);
        mAdapter.updateNewsList(newsList);
    }

}
