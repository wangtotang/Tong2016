package com.tong.tong2016.ui.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tong.baselibrary.common.bases.BaseFragment;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.adapter.ArticleCardAdapter;
import com.tong.tong2016.bean.db.News;
import com.tong.tong2016.bean.json.Article;
import com.tong.tong2016.bean.json.Story;
import com.tong.tong2016.bean.json.ThemeArticle;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.listener.AnimateFirstDisplayListener;
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
 * Created by Martin on 2016/6/8.
 */
@EFragment(R.layout.fragment_page)
public class PageFragment extends BaseFragment {

    @ViewById
    ImageView ivTheme;
    @ViewById
    TextView tvThemeDescription;
    @ViewById
    RecyclerView rv;

    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    List<Story> stories = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    ArticleCardAdapter mAdapter;

    @AfterViews
    void init(){
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        mAdapter = new ArticleCardAdapter(getActivity(),newsList);
        rv.setAdapter(mAdapter);
        getPages(getArguments().getString(SubscribeFragment.IDS));
    }

    @Background(id = "page")
    void getPages(String ids){
        try {
            ThemeArticle themeArticle = TongApplication.getNetService().getThemeArticle(ids).execute().body();
            initHeader(themeArticle.getImage(),themeArticle.getDescription());
            stories = themeArticle.getStories();
            getArticle(stories);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Background(id = "article1")
    void getArticle(List<Story> stories){
        newsList = new ArrayList<>();
        for (int i = 0;i < stories.size();i++) {
            if(i!=0) {
                try {
                    Article article = TongApplication.getNetService()
                            .getArticle(stories.get(i).getId()).execute().body();
                    if(article!=null) {
                        newsList.add(ParseArticleUtil.convertToNews(ParseArticleUtil.createPair(article, ParseArticleUtil.getArticleDocument(article.getBody()))));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        updateState(newsList);
    }

    @UiThread
    void initHeader(String image,String description){
        imageLoader.displayImage(image,ivTheme, Constants.options, animateFirstListener);
        tvThemeDescription.setText(description);

    }

    @UiThread
    void updateState(List<News> newsList){
        mAdapter.updateNewsList(newsList);
    }

}
