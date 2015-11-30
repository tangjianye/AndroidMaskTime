package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.view.ViewStub;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.ui.layer.GuideLayer;
import com.peach.masktime.ui.layer.WelcomeLayer;
import com.peach.masktime.utils.SPUtils;

public class SplashActivity extends BaseActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        switchView(getGuideSwitch());
    }

    private Boolean getGuideSwitch() {
        return (Boolean) SPUtils.get(this, Constants.HAVED_SHOW_GUIDE, false);
    }

    private void switchView(Boolean havedShowGuide) {
        if (havedShowGuide) {
            ViewStub stub = (ViewStub) findViewById(R.id.vs_welcome);
            stub.inflate();
            WelcomeLayer welcomeLayer = (WelcomeLayer) findViewById(R.id.in_welcome);
            welcomeLayer.refresh(null);
        } else {
            ViewStub stub = (ViewStub) findViewById(R.id.vs_guide);
            stub.inflate();
            GuideLayer guideLayer = (GuideLayer) findViewById(R.id.in_guide);
            guideLayer.refresh(null);

            SPUtils.put(this, Constants.HAVED_SHOW_GUIDE, true);
        }
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
