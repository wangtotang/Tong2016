package com.tong.tong2016.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;

import com.tong.baselibrary.common.util.NetworkStateUtil;
import com.tong.tong2016.R;
import com.tong.tong2016.TongApplication;
import com.tong.tong2016.adapter.ArticleCardAdapter;
import com.tong.tong2016.bean.json.Article;
import com.tong.tong2016.bean.json.Latest;
import com.tong.tong2016.bean.db.News;
import com.tong.tong2016.bean.json.Question;
import com.tong.tong2016.bean.json.Story;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EActivity(R.layout.activity_collect)
public class CollectActivity extends BaseNavActivity {

    @ViewById
    RecyclerView rv;

    List<Story> stories = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    ArticleCardAdapter mAdapter;

    @AfterViews
    void init(){
        initBackup();
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        mAdapter = new ArticleCardAdapter(this,newsList);
        rv.setAdapter(mAdapter);

        if ( !NetworkStateUtil.networkConneted(this)){
            loadFromDB();
        } else {
            doRefresh();
        }
    }

    /**
     * 从数据库加载
     */
    private void loadFromDB() {
       /* tongPreferences = TongPreferences.getPreferencesUtil(getContext());
        long lastDay = tongPreferences.getLastDay();
        dataSource = TongApplication.getDataSource();
        if(lastDay==0){
          //显示没有内容
        }else{
            String date = Constants.Dates.simpleDateFormat.format(new Date(lastDay));
            List<Article> originalData = dataSource.newsOfTheDay(date);

            if (originalData != null) {
              *//*  ArticleFromDatabaseObservable.ofDate(date)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this);*//*
            }

        }*/
    }

    /**
     * 刷新内容页
     */
    private void doRefresh() {
        getLatest();
    }

    @Background(id = "latest")
    void getLatest(){
        TongApplication.getNetService().getLatest().enqueue(new Callback<Latest>(){

            @Override
            public void onResponse(Call<Latest> call, Response<Latest> response) {
                Latest latest = response.body();
                if (!TextUtils.isEmpty(latest.getDate())) {
                    stories = latest.getStories();
                    getArticle(stories);
                }
            }

            @Override
            public void onFailure(Call<Latest> call, Throwable t) {

            }
        });
    }

    @Background(id = "article")
    void getArticle(List<Story> stories){
        newsList = new ArrayList<>();
        for (Story story : stories) {
            try {
                Article article = TongApplication.getNetService()
                        .getArticle(story.getId()).execute().body();
                newsList.add(convertToNews(createPair(article,getArticleDocument(article.getBody()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateState(newsList);
    }

    @UiThread
    void updateState(List<News> newsList){
        mAdapter.updateNewsList(newsList);
        //saveArticleList(newsList);
    }

    private Document getArticleDocument(String body) {
        return body != null ? Jsoup.parse(body) : null;
    }

    private Pair<Article, Document> createPair(Article article, Document document) {
        return Pair.create(article, document);
    }

    private News convertToNews(Pair<Article, Document> pair) {
        News result = new News();
        Article article = pair.first;
        Document document = pair.second;
        String title = article.getTitle();
        result.setIds(article.getId());
        result.setImage(article.getImage());
        result.setTitle(title);
        if(document!=null){
            List<Question> questions = getQuestions(document, title);
            if(questions.size()>1){
                result.setTitle("这里包含多个问题讨论，请点击后选择");
                result.setQuestion(title);
            }else{
                result.setQuestion(questions.get(0).getTitle());
            }
        }else{
            result.setQuestion(title);
        }
        return result;
    }

    private static List<Question> getQuestions(Document document, String dailyTitle) {
        List<Question> result = new ArrayList<>();
        Elements questionElements = getQuestionElements(document);

        for (Element questionElement : questionElements) {
            Question question = new Question();

            String questionTitle = getQuestionTitleFromQuestionElement(questionElement);
            String questionUrl = getQuestionUrlFromQuestionElement(questionElement);
            // 确保问题不为空.
            questionTitle = TextUtils.isEmpty(questionTitle) ? dailyTitle : questionTitle;

            question.setTitle(questionTitle);
            question.setUrl(questionUrl);

            result.add(question);
        }

        return result;
    }

    private static Elements getQuestionElements(Document document) {
        return document.select("div.question");
    }

    private static String getQuestionTitleFromQuestionElement(Element questionElement) {
        Element questionTitleElement = questionElement.select("h2.question-title").first();

        if (questionTitleElement == null) {
            return null;
        } else {
            return questionTitleElement.text();
        }
    }

    private static String getQuestionUrlFromQuestionElement(Element questionElement) {
        Element viewMoreElement = questionElement.select("div.view-more a").first();

        if (viewMoreElement == null) {
            return null;
        } else {
            return viewMoreElement.attr("href");
        }
    }

}
