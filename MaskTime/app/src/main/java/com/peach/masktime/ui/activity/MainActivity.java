package com.peach.masktime.ui.activity;

import android.os.Bundle;

import com.peach.masktime.R;
import com.peach.masktime.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
