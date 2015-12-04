package com.peach.masktime.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.ui.service.PlayerService;

public class MainActivity extends BaseActivity implements IInit, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
//    private Button mPlay;
//    private Button mPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
//        mPlay = (Button)findViewById(R.id.bt_play);
//        mPause = (Button)findViewById(R.id.bt_pause);
    }

    @Override
    public void initEvents() {
        findViewById(R.id.txt_community).setOnClickListener(this);
        findViewById(R.id.txt_record).setOnClickListener(this);
        findViewById(R.id.txt_store).setOnClickListener(this);
        findViewById(R.id.txt_setting).setOnClickListener(this);

//        mPlay.setOnClickListener(this);
//        mPause.setOnClickListener(this);

        findViewById(R.id.bt_play).setOnClickListener(this);
        findViewById(R.id.bt_pause).setOnClickListener(this);
        findViewById(R.id.bt_stop).setOnClickListener(this);
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
                openActivity(AlbumActivity.class);
                break;
            case R.id.txt_setting:
                openActivity(SettingActivity.class);
                break;
            case R.id.bt_pause:
                startService(getPlayerIntent(this, Constants.PlayerMsg.PAUSE));
                break;
            case R.id.bt_play:
                startService(getPlayerIntent(this, Constants.PlayerMsg.PLAY));
                break;
            case R.id.bt_stop:
                stopService(getPlayerIntent(this, Constants.PlayerMsg.STOP));
                break;
            default:
                break;
        }
    }

    private Intent getPlayerIntent(Context ctx, int msg) {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLAYER_MSG, msg);
        intent.setClass(ctx, PlayerService.class);
        return intent;
    }
}
