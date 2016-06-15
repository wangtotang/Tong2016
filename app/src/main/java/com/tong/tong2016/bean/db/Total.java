package com.tong.tong2016.bean.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by Martin on 2016/6/13.
 */
public class Total extends BmobObject {

    private static final long serialVersionUID = 1L;

    private String ids;
    private String userId;
    private Boolean liked;
    private Boolean collectd;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Boolean getCollectd() {
        return collectd;
    }

    public void setCollectd(Boolean collectd) {
        this.collectd = collectd;
    }
}
