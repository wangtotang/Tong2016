package com.tong.tong2016.listener;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by Martin on 2016/6/11.
 */
public class HostSelectionInterceptor implements Interceptor{

    private volatile String host;

    public void setHost(String host) {
        this.host = host;
    }

    @Override public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String host = this.host;
        if (host != null) {
            HttpUrl newUrl = request.url().newBuilder()
                    .host(host)
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }
        return chain.proceed(request);
    }

}
