package com.tong.tong2016.bean.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by Martin on 2016/6/12.
 */
public class MyUser extends BmobObject {

    private static final long serialVersionUID = 1L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
