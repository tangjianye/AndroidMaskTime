package com.peach.masktime.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.db.DBRecordHelper;
import com.peach.masktime.db.Record;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.module.net.request.GsonRequest;
import com.peach.masktime.module.net.response.AccountItem;
import com.peach.masktime.module.net.response.MaskObjectSet;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.utils.AnimatorUtils;
import com.peach.masktime.utils.LogUtils;
import com.peach.masktime.utils.SPUtils;
import com.peach.masktime.utils.StringUtils;
import com.peach.masktime.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

public class RecordingActivity extends BaseTitleActivity implements IInit, View.OnClickListener {
    private static final String TAG = RecordingActivity.class.getSimpleName();

    private String mTitle;
    private String mContent;

    private EditText mEtTitle;
    private EditText mEtContent;
    private Button mBtnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        initDatas();
        initTitles();
        initViews();
        initEvents();
    }

    @Override
    protected void setContentLayer() {
        setContentView(R.layout.activity_recording);
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
        mTitleTips.setText(TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_DAY));
    }

    @Override
    public void initViews() {
        mEtTitle = (EditText) findViewById(R.id.et_title);
        mEtContent = (EditText) findViewById(R.id.et_content);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void initEvents() {
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        AnimatorSet set = getAnimatorSet(mBtnSubmit);
        String url = API.getUrl(API.LOGIN);
        mTitle = mEtTitle.getText().toString().trim();
        mContent = mEtContent.getText().toString().trim();

        if (StringUtils.isEmpty(mTitle)) {
            showToast(R.string.error_invalid_title);
            set.start();
        } else if (StringUtils.isEmpty(mContent)) {
            showToast(R.string.error_invalid_content);
            set.start();
        } else {
            saveLocal();
            // request(url, mTitle, mContent);
        }
    }

    private void saveLocal() {
        Record info = new Record(null, mTitle, mContent, null, null, null, System.currentTimeMillis());
        // DBManager.getInstance().getRecordDao().insert(info);
        DBRecordHelper.getInstance().save(info);
        setResult(Activity.RESULT_OK);
    }

    @NonNull
    private AnimatorSet getAnimatorSet(View view) {
        AnimatorSet set = AnimatorUtils.createAnimatorSet();
        ObjectAnimator translationX = AnimatorUtils.ofFloat(view, "translationX", 0.0f, 20.0f, 0.0f);
        ObjectAnimator translationY = AnimatorUtils.ofFloat(view, "translationY", 0, 10);
        set.setDuration(getResources().getInteger(R.integer.anim_shake_duration));
        set.setInterpolator(new CycleInterpolator(getResources().getInteger(R.integer.anim_shake_cycle_num)));
        set.play(translationX);
        // set.play(translationX).with(translationY);
        return set;
    }

    private void request(final String url, final String account, final String password) {
        showLoadingDialog(getString(R.string.default_loading_tips));

        GsonRequest<MaskObjectSet<AccountItem>> request = new GsonRequest<MaskObjectSet<AccountItem>>(
                Request.Method.POST,
                url,
                new TypeToken<MaskObjectSet<AccountItem>>() {
                }.getType(),
                new Response.Listener<MaskObjectSet<AccountItem>>() {
                    @Override
                    public void onResponse(MaskObjectSet<AccountItem> response) {
                        dismissLoadingDialog();
                        if (null != response && response.getRsm() != null) {
                            SPUtils.put(RecordingActivity.this, Constants.SPKey.ACCOUNT_UID, response.getRsm().getUid());
                            SPUtils.put(RecordingActivity.this, Constants.SPKey.ACCOUNT_NAME, account);
                            SPUtils.put(RecordingActivity.this, Constants.SPKey.ACCOUNT_PASSWORD, password);
                            SPUtils.put(RecordingActivity.this, Constants.SPKey.ACCOUNT_AVATAR_FILE, response.getRsm().getAvatar_file());
                            finish();
                        } else {
                            showToast((null != response) ? response.getErr() : getString(R.string.default_get_data_fail));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissLoadingDialog();
                        showToast(R.string.default_get_data_fail);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("user_name", account);
                map.put("password", password);
                LogUtils.i(TAG, "map = " + map.toString());
                return map;
            }
        };

        VolleyManager.getInstance().addToRequestQueue(request, url);
    }
}
