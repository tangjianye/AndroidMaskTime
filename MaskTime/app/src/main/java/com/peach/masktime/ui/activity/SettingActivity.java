package com.peach.masktime.ui.activity;

import android.os.Bundle;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IStatus;
import com.peach.masktime.ui.base.BaseTitleActivity;

public class SettingActivity extends BaseTitleActivity implements IStatus {
    public static final String TAG = "CommunityActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initTitles();
        initViews();
        initEvents();
    }

    @Override
    protected void setContentLayer() {
        setContentView(R.layout.activity_setting);
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
        mTitleTips.setText(R.string.main_setting);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }
}
