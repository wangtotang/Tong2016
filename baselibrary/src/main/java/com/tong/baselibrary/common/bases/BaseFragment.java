package com.tong.baselibrary.common.bases;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * 通用Fragment
 * Created by Martin on 2016/5/5.
 */
public class BaseFragment extends Fragment {

    private final long UI_ID = Thread.currentThread().getId();
    private Toast toast;

    /**
     * 初始化 Toast
     * @param text 提醒文本
     */
    private void createToast(CharSequence text){
        if (toast == null) {
            toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
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
            getActivity().runOnUiThread(new Runnable() {
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
            toast = Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT);
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
            getActivity().runOnUiThread(new Runnable() {
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
     * 开始新Activity
     * @param cls 新Activity的Class
     */
    protected void startActivity(@NonNull Class cls){
        Intent intent = new Intent(getActivity(),cls);
        super.startActivity(intent);
    }

}
