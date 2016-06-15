package com.tong.tong2016;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tong.tong2016.config.Constants;
import com.tong.tong2016.config.TongService;
import com.tong.tong2016.listener.HostSelectionInterceptor;
import com.tong.tong2016.storage.DaoMaster;

import cn.bmob.v3.Bmob;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class TongApplication extends Application {

    private static TongApplication applicationContext;
    private static TongService service;
    private static HostSelectionInterceptor hostSelectionInterceptor;
    private static SQLiteDatabase db;

    public void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public void initRetrofit(){
         hostSelectionInterceptor = new HostSelectionInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(hostSelectionInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.Urls.ZHIHU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(okHttpClient)
                .build();

        service = retrofit.create(TongService.class);
    }

    public void initDataBase(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "tong-db", null);
        db = helper.getWritableDatabase();
    }

    public static TongApplication getInstance() {
        return applicationContext;
    }

    public static TongService getNetService(){
        hostSelectionInterceptor.setHost(Constants.Urls.ZHIHU_HOST);
        return service;
    }

    public static HostSelectionInterceptor getIntercepter(){
        return hostSelectionInterceptor;
    }

    public static SQLiteDatabase getDataSource() {
        return db;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        initImageLoader(this);
        initRetrofit();
        initDataBase(this);
        Bmob.initialize(this,Constants.Key.Bmob_APPID);
    }

}
