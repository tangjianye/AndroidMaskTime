package com.peach.masktime.ui.activity;

import android.os.Bundle;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.ui.base.BaseActivity;

public class RecordActivity extends BaseActivity implements IInit {
    public static final String TAG = "CommunityActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initDatas();
        initTitles();
        initViews();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initDatas() {

    }

    @Override
    public void initTitles() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }
}
