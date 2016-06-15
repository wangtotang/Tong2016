package com.tong.tong2016.ui.activities;

import android.view.View;
import android.widget.ImageButton;

import com.tong.baselibrary.common.bases.BaseActivity;
import com.tong.tong2016.R;

/**
 * Created by Martin on 2016/5/14.
 */
public class BaseNavActivity extends BaseActivity{

    ImageButton ibBack;

    /**
     * 初始化返回键
     */
    protected void initBackup(){
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        if(ibBack!=null){
            ibBack.setOnClickListener(new OnBackupListener());
        }
    }

    /**
     * 返回键监听器（默认按钮的监听器）
     */
    public class OnBackupListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }

    }
}
