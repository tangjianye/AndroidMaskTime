package com.peach.masktime.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.config.GlobalSetting;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.ui.layer.RawMusicContrlLayer;
import com.peach.masktime.utils.CommUtils;
import com.peach.masktime.utils.SPUtils;

public class SettingActivity extends BaseTitleActivity implements IInit, View.OnClickListener {
    private static final String TAG = SettingActivity.class.getSimpleName();
    private Dialog mDialog;
    private TextView mTxtLogIn;
    private TextView mTxtSoundType;

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
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }

        // 关闭音乐播放服务
        // stopService(CommUtils.getMaskIntent(this, null, LocalPlayerService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String name = (String) SPUtils.get(this, Constants.SPKey.ACCOUNT_NAME, getString(R.string.setting_login));
        mTxtLogIn.setText(name);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initDatas() {
        // mSoundTypes = getResources().getStringArray(R.array.sound_type);
    }

    @Override
    public void initTitles() {
        mTitleTips.setText(R.string.main_setting);
    }

    @Override
    public void initViews() {
        mTxtLogIn = (TextView) findViewById(R.id.txt_login);
        mTxtSoundType = (TextView) findViewById(R.id.txt_sound_type);

        String key = CommUtils.getMusicItemKey(this);
        mTxtSoundType.setText(GlobalSetting.CONTENT_MAP.get(key));
    }

    @Override
    public void initEvents() {
        findViewById(R.id.ly_login).setOnClickListener(this);
        findViewById(R.id.ly_plan).setOnClickListener(this);
        findViewById(R.id.ly_sound).setOnClickListener(this);
        findViewById(R.id.ly_favorable).setOnClickListener(this);
        findViewById(R.id.ly_feedback).setOnClickListener(this);
        findViewById(R.id.ly_share).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_login:
                openActivity(LoginActivity.class);
                break;
            case R.id.ly_plan:
                // openActivity(CommunityActivity.class);
                break;
            case R.id.ly_sound:
                builderDialog(R.string.setting_sound);
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


    /**
     * 音乐播放按钮
     */
    private RawMusicContrlLayer mBtnItem01;
    private RawMusicContrlLayer mBtnItem02;
    private RawMusicContrlLayer mBtnItem03;
    private RawMusicContrlLayer mBtnItem04;

    protected void builderDialog(int resId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_setting, null);
        TextView title = (TextView) view.findViewById(R.id.txt_dialog_title);
        title.setText(resId);

        ((TextView) view.findViewById(R.id.item_txt_01)).setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_01));
        ((TextView) view.findViewById(R.id.item_txt_02)).setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_02));
        ((TextView) view.findViewById(R.id.item_txt_03)).setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_03));
        ((TextView) view.findViewById(R.id.item_txt_04)).setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_04));

        view.findViewById(R.id.ly_01).setOnClickListener(mDialogListener);
        view.findViewById(R.id.ly_02).setOnClickListener(mDialogListener);
        view.findViewById(R.id.ly_03).setOnClickListener(mDialogListener);
        view.findViewById(R.id.ly_04).setOnClickListener(mDialogListener);

        mBtnItem01 = (RawMusicContrlLayer) view.findViewById(R.id.item_contrl_01);
        mBtnItem02 = (RawMusicContrlLayer) view.findViewById(R.id.item_contrl_02);
        mBtnItem03 = (RawMusicContrlLayer) view.findViewById(R.id.item_contrl_03);
        mBtnItem04 = (RawMusicContrlLayer) view.findViewById(R.id.item_contrl_04);

        mBtnItem01.setPath(GlobalSetting.MUSIC_MAP.get(Constants.Music.ITEM_01));
        mBtnItem02.setPath(GlobalSetting.MUSIC_MAP.get(Constants.Music.ITEM_02));
        mBtnItem03.setPath(GlobalSetting.MUSIC_MAP.get(Constants.Music.ITEM_03));
        mBtnItem04.setPath(GlobalSetting.MUSIC_MAP.get(Constants.Music.ITEM_04));

        mBtnItem01.setOnClickListener(mPickerListener);
        mBtnItem02.setOnClickListener(mPickerListener);
        mBtnItem03.setOnClickListener(mPickerListener);
        mBtnItem04.setOnClickListener(mPickerListener);

        builder.setCustomTitle(view);
        mDialog = builder.create();
        mDialog.show();
    }

    private View.OnClickListener mDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDialog.dismiss();

            switch (view.getId()) {
                case R.id.ly_01:
                    mTxtSoundType.setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_01));
                    SPUtils.put(SettingActivity.this, Constants.SPKey.MUSIC_ITEM_KEY, Constants.Music.ITEM_01);
                    break;
                case R.id.ly_02:
                    mTxtSoundType.setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_02));
                    SPUtils.put(SettingActivity.this, Constants.SPKey.MUSIC_ITEM_KEY, Constants.Music.ITEM_02);
                    break;
                case R.id.ly_03:
                    mTxtSoundType.setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_03));
                    SPUtils.put(SettingActivity.this, Constants.SPKey.MUSIC_ITEM_KEY, Constants.Music.ITEM_03);
                    break;
                case R.id.ly_04:
                    mTxtSoundType.setText(GlobalSetting.CONTENT_MAP.get(Constants.Music.ITEM_04));
                    SPUtils.put(SettingActivity.this, Constants.SPKey.MUSIC_ITEM_KEY, Constants.Music.ITEM_04);
                    break;
                default:
                    break;
            }
        }
    };

    private View.OnClickListener mPickerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.item_contrl_01:
                    mBtnItem02.stopPlay();
                    mBtnItem03.stopPlay();
                    mBtnItem04.stopPlay();

                    mBtnItem01.startPlay();
                    break;
                case R.id.item_contrl_02:
                    mBtnItem01.stopPlay();
                    mBtnItem03.stopPlay();
                    mBtnItem04.stopPlay();

                    mBtnItem02.startPlay();
                    break;
                case R.id.item_contrl_03:
                    mBtnItem01.stopPlay();
                    mBtnItem02.stopPlay();
                    mBtnItem04.stopPlay();

                    mBtnItem03.startPlay();
                    break;
                case R.id.item_contrl_04:
                    mBtnItem01.stopPlay();
                    mBtnItem02.stopPlay();
                    mBtnItem03.stopPlay();

                    mBtnItem04.startPlay();
                    break;
                default:
                    break;
            }
        }
    };
}
