package com.tong.tong2016.config;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.tong.tong2016.R;

/**
 * Created by Martin on 2016/5/19.
 */
public interface Constants {

     DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_noimage)
            .showImageOnFail(R.drawable.ic_noimage)
            .showImageForEmptyUri(R.drawable.ic_noimage)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build();

    final class Urls {
        public static final String ZHIHU_URL = "http://news-at.zhihu.com";
        public static final String ZHIHU_HOST = "news-at.zhihu.com";
        public static final String SEARCH_HOST = "zhihu-daily-purify.azurewebsites.net";
    }

    final class Key {
        public static String Bmob_APPID = "51a5d1c6f60d94746c411c08e538c061";
    }

}
