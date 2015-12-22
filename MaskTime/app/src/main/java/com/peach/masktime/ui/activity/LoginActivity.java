package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.ui.base.BaseTitleActivity;

public class LoginActivity extends BaseTitleActivity implements IInit, View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_login);
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
        mTitleTips.setText(R.string.title_activity_login);
    }

    @Override
    public void initViews() {
    }

    @Override
    public void initEvents() {
        findViewById(R.id.btn_sign_in).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                break;
            default:
                break;
        }
    }
}
