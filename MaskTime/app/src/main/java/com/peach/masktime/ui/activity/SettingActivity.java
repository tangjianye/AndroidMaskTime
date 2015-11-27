package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.ui.base.BaseTitleActivity;

public class SettingActivity extends BaseTitleActivity implements IInit, View.OnClickListener {
    private static final String TAG = "CommunityActivity";
    private TextView mTvSoundType;

    private String[] mSoundTypes;


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
        mSoundTypes = getResources().getStringArray(R.array.sound_type);
    }

    @Override
    public void initTitles() {
        mTitleTips.setText(R.string.main_setting);
    }

    @Override
    public void initViews() {
        mTvSoundType = (TextView) findViewById(R.id.txt_sound_type);

        mTvSoundType.setText(mSoundTypes[0]);
    }

    @Override
    public void initEvents() {
        findViewById(R.id.ly_plan).setOnClickListener(this);
        findViewById(R.id.ly_sound).setOnClickListener(this);
        findViewById(R.id.ly_favorable).setOnClickListener(this);
        findViewById(R.id.ly_feedback).setOnClickListener(this);
        findViewById(R.id.ly_share).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_plan:
                // openActivity(CommunityActivity.class);
                break;
            case R.id.ly_sound:

                break;
            case R.id.ly_favorable:

                break;
            case R.id.ly_feedback:

                break;
            case R.id.ly_share:

                break;
            default:
                break;
        }
    }
}
