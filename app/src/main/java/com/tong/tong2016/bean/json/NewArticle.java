
package com.tong.tong2016.bean.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class NewArticle {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("thumbnailUrl")
    @Expose
    private String thumbnailUrl;
    @SerializedName("dailyTitle")
    @Expose
    private String dailyTitle;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = new ArrayList<Question>();

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The thumbnailUrl
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * 
     * @param thumbnailUrl
     *     The thumbnailUrl
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * 
     * @return
     *     The dailyTitle
     */
    public String getDailyTitle() {
        return dailyTitle;
    }

    /**
     * 
     * @param dailyTitle
     *     The dailyTitle
     */
    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    /**
     * 
     * @return
     *     The questions
     */
    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * 
     * @param questions
     *     The questions
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
