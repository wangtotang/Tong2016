package com.tong.baselibrary.common.bases;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by martin on 2016/5/5.
 */
public abstract class BaseActivity extends AppCompatActivity{

    protected final long UI_ID = Thread.currentThread().getId();
    protected Toast toast;
    protected ProgressDialog progressDialog;

    /**
     * 初始化 Toast
     * @param text 提醒文本
     */
    private void createToast(CharSequence text){
        if (toast == null) {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    /**
     * 显示提醒
     * @param text 提醒文本
     */
    public void showToast(final CharSequence text){
        if(Thread.currentThread().getId() != UI_ID){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   createToast(text);
                }
            });
        }else{
            createToast(text);
        }
    }

    /**
     * 初始化 Toast
     * @param resId 提醒文本
     */
    private void createToast(int resId){
        if (toast == null) {
            toast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }

    /**
     * 显示提醒
     * @param resId 提醒文本
     */
    public void showToast(final int resId){
        if(Thread.currentThread().getId() != UI_ID){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createToast(resId);
                }
            });
        }else{
            createToast(resId);
        }
    }

    /**
     * 初始化 progressDialog
     * @param text 提醒文本
     */
    private void createProgressDialog(CharSequence text){
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(text);
        progressDialog.show();
    }

    public ProgressDialog showProgressDialog(final CharSequence msg){
        if(Thread.currentThread().getId() != UI_ID){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    createProgressDialog(msg);
                }
            });
        }else{
            createProgressDialog(msg);
        }
        return progressDialog;
    }

    public void dismissProgressDialog(){
        if(Thread.currentThread().getId() != UI_ID){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(progressDialog!=null){
                        progressDialog.dismiss();
                    }
                }
            });
        }else{
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }

    /**
     * 开始新Activity
     * @param cls 新Activity的Class
     */
    protected void startActivity(@NonNull Class cls){
        Intent intent = new Intent(this,cls);
        super.startActivity(intent);
    }

}
