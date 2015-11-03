package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.ui.layer.GuideLayer;
import com.peach.masktime.ui.layer.WelcomeLayer;
import com.peach.masktime.utils.SPUtils;

public class WelcomeActivity extends BaseActivity {
    public static final String TAG = "CommunityActivity";
    private GuideLayer mGuideLayer;
    private WelcomeLayer mWelcomeLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mGuideLayer = (GuideLayer) findViewById(R.id.in_guide);
        mWelcomeLayer = (WelcomeLayer) findViewById(R.id.in_welcome);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWelcomeLayer.setVisibility(View.GONE);
        mGuideLayer.setVisibility(View.GONE);

        Boolean havedShowGuide = (Boolean) SPUtils.get(this, Constants.HAVED_SHOW_GUIDE, false);
        if (havedShowGuide) {
            mWelcomeLayer.show(false);
        } else {
            mGuideLayer.show(false);
            SPUtils.put(this, Constants.HAVED_SHOW_GUIDE, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
