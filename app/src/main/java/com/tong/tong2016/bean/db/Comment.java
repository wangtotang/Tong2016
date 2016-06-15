package com.tong.tong2016.bean.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by Martin on 2016/6/7.
 */
public class Comment extends BmobObject{

    private static final long serialVersionUID = 1L;

    private String title;
    private String content;
    private String date;
    private Post post;
    private MyUser user;

    public Comment() {
    }

    public Comment(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
