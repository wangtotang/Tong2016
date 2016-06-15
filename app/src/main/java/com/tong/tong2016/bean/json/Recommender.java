
package com.tong.tong2016.bean.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recommender {

    @SerializedName("avatar")
    @Expose
    private String avatar;

    /**
     * 
     * @return
     *     The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 
     * @param avatar
     *     The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
