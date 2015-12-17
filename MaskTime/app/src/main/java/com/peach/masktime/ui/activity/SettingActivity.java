package com.peach.masktime.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.ui.base.BaseTitleActivity;

import java.util.HashMap;

public class SettingActivity extends BaseTitleActivity implements IInit, View.OnClickListener {
    private static final String TAG = SettingActivity.class.getSimpleName();

    private Dialog mDialog;
    private TextView mTvSoundType;

    // private String[] mSoundTypes;

    public static HashMap<Integer, Integer> CONTENT_MAP = new HashMap<Integer, Integer>();

    static {
        CONTENT_MAP.put(R.id.ly_01, R.string.setting_music_bird);
        CONTENT_MAP.put(R.id.ly_02, R.string.setting_music_dream);
        CONTENT_MAP.put(R.id.ly_03, R.string.setting_music_xqx);
        CONTENT_MAP.put(R.id.ly_04, R.string.setting_music_pkq);
    }

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
        // mSoundTypes = getResources().getStringArray(R.array.sound_type);
    }

    @Override
    public void initTitles() {
        mTitleTips.setText(R.string.main_setting);
    }

    @Override
    public void initViews() {
        mTvSoundType = (TextView) findViewById(R.id.txt_sound_type);
        mTvSoundType.setText(CONTENT_MAP.get(R.id.ly_01));
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
                // builderDialog(getString(R.string.notice_sound), mSoundTypes);
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

//    protected void builderDialog(final String title, final String[] items) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_list, null);
//        TextView dialogTitle = (TextView) view.findViewById(R.id.txt_dialog_title);
//        dialogTitle.setText(title);
////        ImageView cancel = (ImageView) view.findViewById(R.id.cancel);
////        cancel.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                mDialog.dismiss();
////            }
////        });
//        builder.setCustomTitle(view);
//
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                switch (which) {
//                    case 0:
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                    default:
//                        break;
//                }
//                mTvSoundType.setText(items[which]);
//            }
//        });
//
//        mDialog = builder.create();
//        mDialog.show();
//    }

    protected void builderDialog(int resId) {
        DialogClickListener dialogListener = new DialogClickListener();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_setting, null);
        TextView title = (TextView) view.findViewById(R.id.txt_dialog_title);
        title.setText(resId);

        ((TextView) view.findViewById(R.id.item_txt_01)).setText(CONTENT_MAP.get(R.id.ly_01));
        ((TextView) view.findViewById(R.id.item_txt_02)).setText(CONTENT_MAP.get(R.id.ly_02));
        ((TextView) view.findViewById(R.id.item_txt_03)).setText(CONTENT_MAP.get(R.id.ly_03));
        ((TextView) view.findViewById(R.id.item_txt_04)).setText(CONTENT_MAP.get(R.id.ly_04));

        view.findViewById(R.id.ly_01).setOnClickListener(dialogListener);
        view.findViewById(R.id.ly_02).setOnClickListener(dialogListener);
        view.findViewById(R.id.ly_03).setOnClickListener(dialogListener);
        view.findViewById(R.id.ly_04).setOnClickListener(dialogListener);

        builder.setCustomTitle(view);
        mDialog = builder.create();
        mDialog.show();
    }

    private class DialogClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mDialog.dismiss();
            mTvSoundType.setText(CONTENT_MAP.get(view.getId()));

            switch (view.getId()) {
                case R.id.ly_01:

                    break;
                case R.id.ly_02:

                    break;
                case R.id.ly_03:

                    break;
                case R.id.ly_04:

                    break;
                default:
                    break;
            }
        }
    }
}
