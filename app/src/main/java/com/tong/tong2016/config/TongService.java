package com.tong.tong2016.config;

import com.tong.tong2016.bean.json.Article;
import com.tong.tong2016.bean.json.Hot;
import com.tong.tong2016.bean.json.Latest;
import com.tong.tong2016.bean.json.Search;
import com.tong.tong2016.bean.json.Theme;
import com.tong.tong2016.bean.json.ThemeArticle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Martin on 2016/6/6.
 */
public interface TongService {

    @GET("/api/4/news/latest")
    Call<Latest> getLatest();

    @GET("/api/4/news/{id}")
    Call<Article> getArticle(@Path("id") String id);

    @GET("/api/3/news/hot")
    Call<Hot> getHot();

    @GET("/api/4/themes")
    Call<Theme> getThemes();

    @GET("/api/4/theme/{id}")
    Call<ThemeArticle> getThemeArticle(@Path("id") String id);

    @GET("/search/")
    Call<Search> getSearch(@Query("q")String q);

}
