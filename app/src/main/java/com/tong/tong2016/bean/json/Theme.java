
package com.tong.tong2016.bean.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Theme {

    @SerializedName("limit")
    @Expose
    private int limit;
    @SerializedName("subscribed")
    @Expose
    private List<Object> subscribed = new ArrayList<Object>();
    @SerializedName("others")
    @Expose
    private List<Other> others = new ArrayList<Other>();

    /**
     * 
     * @return
     *     The limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * 
     * @param limit
     *     The limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 
     * @return
     *     The subscribed
     */
    public List<Object> getSubscribed() {
        return subscribed;
    }

    /**
     * 
     * @param subscribed
     *     The subscribed
     */
    public void setSubscribed(List<Object> subscribed) {
        this.subscribed = subscribed;
    }

    /**
     * 
     * @return
     *     The others
     */
    public List<Other> getOthers() {
        return others;
    }

    /**
     * 
     * @param others
     *     The others
     */
    public void setOthers(List<Other> others) {
        this.others = others;
    }

}
