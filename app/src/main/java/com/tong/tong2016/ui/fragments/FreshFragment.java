package com.tong.tong2016.ui.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.tong.baselibrary.common.bases.BaseFragment;
import com.tong.baselibrary.common.util.NetworkStateUtil;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.adapter.ArticleCardAdapter;
import com.tong.tong2016.bean.db.News;
import com.tong.tong2016.bean.json.Article;
import com.tong.tong2016.bean.json.Latest;
import com.tong.tong2016.bean.json.Story;
import com.tong.tong2016.storage.DaoMaster;
import com.tong.tong2016.storage.NewsDao;
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
@EFragment(R.layout.fragment_fresh)
public class FreshFragment extends BaseFragment{

    @ViewById
    SwipeRefreshLayout srl;

    @ViewById
    RecyclerView rv;

    List<Story> stories = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    ArticleCardAdapter mAdapter;
    NewsDao dao;

    @AfterViews
    void init(){

        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));

        dao = new DaoMaster(TongApplication.getDataSource()).newSession().getNewsDao();
        newsList = dao.loadAll();

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
            getLatest();
        }
    }

    @Background(id = "latest")
    void getLatest(){
        try {
            Latest latest = TongApplication.getNetService().getLatest().execute().body();
            if (!TextUtils.isEmpty(latest.getDate())) {
                stories = latest.getStories();
                getArticle(stories);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Background(id = "article1")
    void getArticle(List<Story> stories){
        newsList = new ArrayList<>();
        for (Story story : stories) {
            try {
                Article article = TongApplication.getNetService()
                        .getArticle(story.getId()).execute().body();
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
        saveArticleList(newsList);
    }

    @Background(id = "save")
    void saveArticleList(List<News> newsList) {
        if (newsList != null && newsList.size() > 0) {
            if(dao.count()>20){
                dao.deleteAll();
            }
            dao.insertOrReplaceInTx(newsList);
        }
    }

}
