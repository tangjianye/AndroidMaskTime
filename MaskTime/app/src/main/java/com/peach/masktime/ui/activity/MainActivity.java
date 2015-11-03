package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IStatus;
import com.peach.masktime.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements IStatus, View.OnClickListener {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = null;
        view = findViewById(R.id.txt_community);
        if (null == view) {
            showToast("view null");
            return;
        }
        initDatas();
        initTitles();
        initViews();
        initEvents();
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
        findViewById(R.id.txt_community).setOnClickListener(this);
        findViewById(R.id.txt_record).setOnClickListener(this);
        findViewById(R.id.txt_store).setOnClickListener(this);
        findViewById(R.id.txt_setting).setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_community:
                openActivity(CommunityActivity.class);
                break;
            case R.id.txt_record:
                openActivity(RecordActivity.class);
                break;
            case R.id.txt_store:
                openActivity(StoreActivity.class);
                break;
            case R.id.txt_setting:
                openActivity(SettingActivity.class);
                break;
            default:
                break;
        }
    }
}
