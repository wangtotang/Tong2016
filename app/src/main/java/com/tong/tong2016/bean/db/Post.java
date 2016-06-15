package com.tong.tong2016.bean.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by Martin on 2016/6/12.
 */
public class Post extends BmobObject{

    private static final long serialVersionUID = 1L;

    private String ids;
    private String title;
    private Integer views;
    private Integer likes;
    private Integer collects;
    private Integer comments;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getCollects() {
        return collects;
    }

    public void setCollects(Integer collects) {
        this.collects = collects;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
