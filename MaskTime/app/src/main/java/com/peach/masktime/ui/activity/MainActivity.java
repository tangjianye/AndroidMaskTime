package com.peach.masktime.ui.activity;

import android.os.Bundle;

import com.peach.masktime.R;
import com.peach.masktime.ui.base.BaseTabActivity;

public class MainActivity extends BaseTabActivity {
    public static final String TAB_HOME = "Home";
    public static final String TAB_CATEGORY = "Category";
    public static final String TAB_LOCAL = "Local";
    public static final String TAB_MORE = "More";

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
