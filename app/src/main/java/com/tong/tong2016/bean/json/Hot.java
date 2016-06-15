
package com.tong.tong2016.bean.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Hot {

    @SerializedName("recent")
    @Expose
    private List<Recent> recent = new ArrayList<Recent>();

    /**
     * 
     * @return
     *     The recent
     */
    public List<Recent> getRecent() {
        return recent;
    }

    /**
     * 
     * @param recent
     *     The recent
     */
    public void setRecent(List<Recent> recent) {
        this.recent = recent;
    }

}
