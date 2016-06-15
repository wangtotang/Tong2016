package com.tong.baselibrary.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Martin on 2016/3/3.
 */
public class CacheUtil {

    private static final String DISK_CACHE_SUBDIR = "thumbnails";

    public static void addBitmapToMemoryCache(File cacheFile,Bitmap bitmap) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(cacheFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap getBitmapFromMemCache(File cacheFile) {
        Bitmap bitmap = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(cacheFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {}
        }
        return bitmap;
    }

    public static File getDiskCacheFile(Context context, String key) {
        final String cachePath = context.getFilesDir().getPath();
        File cacheDir = new File(cachePath + File.separator + DISK_CACHE_SUBDIR);
        if(!cacheDir.exists()){
            cacheDir.mkdir();
        }
        return new File(cacheDir+File.separator+key);
    }

}
