
package com.tong.tong2016.bean.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStory {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("type")
    @Expose
    private int type;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("ga_prefix")
    @Expose
    private String gaPrefix;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The type
     */
    public int getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The gaPrefix
     */
    public String getGaPrefix() {
        return gaPrefix;
    }

    /**
     * 
     * @param gaPrefix
     *     The ga_prefix
     */
    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
