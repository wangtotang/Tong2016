package com.tong.tong2016.ui.activities;

import android.support.v7.widget.RecyclerView;

import com.tong.tong2016.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_read)
public class ReadActivity extends BaseNavActivity {

    @ViewById
    RecyclerView rv;


    @AfterViews
    void init(){
        initBackup();

    }

}
