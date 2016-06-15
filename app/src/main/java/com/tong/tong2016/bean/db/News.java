package com.tong.tong2016.bean.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "NEWS".
 */
public class News {

    private Long id;
    /** Not-null value. */
    private String question;
    private String title;
    private String image;
    /** Not-null value. */
    private String ids;

    public News() {
    }

    public News(Long id) {
        this.id = id;
    }

    public News(Long id, String question, String title, String image, String ids) {
        this.id = id;
        this.question = question;
        this.title = title;
        this.image = image;
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getQuestion() {
        return question;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /** Not-null value. */
    public String getIds() {
        return ids;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setIds(String ids) {
        this.ids = ids;
    }

}
