
package com.tong.tong2016.bean.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Search {

    @SerializedName("news")
    @Expose
    private List<NewArticle> news = new ArrayList<NewArticle>();

    /**
     * 
     * @return
     *     The news
     */
    public List<NewArticle> getNews() {
        return news;
    }

    /**
     * 
     * @param news
     *     The news
     */
    public void setNews(List<NewArticle> news) {
        this.news = news;
    }

}
