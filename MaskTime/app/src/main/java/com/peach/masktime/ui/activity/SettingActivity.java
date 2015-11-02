package com.peach.masktime.ui.activity;

import android.os.Bundle;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IStatus;
import com.peach.masktime.ui.base.BaseActivity;

public class SettingActivity extends BaseActivity implements IStatus {
    public static final String TAG = "CommunityActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
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
