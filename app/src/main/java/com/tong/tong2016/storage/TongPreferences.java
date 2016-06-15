package com.tong.tong2016.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Martin on 2016/5/18.
 */
public class TongPreferences {

    private static TongPreferences preferencesUtil = new TongPreferences();
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static final String SHAREDPREFERENCE_NAME = "tong2016";
    private final String KEY_TONG_PLATFORM = "platform";
    private final String KEY_TONG_THEME_ID = "theme_id_";

    private TongPreferences(){}

    public static TongPreferences getPreferencesUtil(Context context){
        if(preferences == null){
            preferences = context.getSharedPreferences(SHAREDPREFERENCE_NAME,Context.MODE_PRIVATE);
            editor = preferences.edit();
        }
        return preferencesUtil;
    }

    public String getPlatform() {
        return preferences.getString(KEY_TONG_PLATFORM,"");
    }

    public void setPlatform(String platform) {
        editor.putString(KEY_TONG_PLATFORM,platform);
        editor.commit();
    }

    public boolean themeById(String id) {
        return preferences.getBoolean(KEY_TONG_THEME_ID + id, false);
    }


    public void setThemeById(String id,boolean subscribe) {
        editor.putBoolean(KEY_TONG_THEME_ID + id,subscribe);
        editor.commit();
    }

}
