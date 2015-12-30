package com.peach.masktime.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import com.peach.masktime.db.DBManager;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecordingActivity extends BaseTitleActivity implements IInit, View.OnClickListener {
    private static final String TAG = RecordingActivity.class.getSimpleName();

    private String mAccount;
    private String mPassword;

    private EditText mEtAccount;
    private EditText mEtPassword;
    private EditText mEtEmail;
    private Button mBtnSubmit;
    // private TextView mTxtQuicker;

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
        mTitleTips.setText(TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_DATE));
    }

    @Override
    public void initViews() {
        mEtAccount = (EditText) findViewById(R.id.et_account);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtEmail = (EditText) findViewById(R.id.et_email);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnSubmit.setText(R.string.action_sign_in);

        mEtEmail.setVisibility(View.GONE);
    }

    @Override
    public void initEvents() {
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                // submit();

                mAccount = mEtAccount.getText().toString().trim();
                mPassword = mEtPassword.getText().toString().trim();
                Record info = new Record(null, mAccount, mPassword, null, null, null, new Date());
                DBManager.getInstance().getRecordDao().insert(info);
                break;
            default:
                break;
        }
    }

    private void submit() {
        AnimatorSet set = getAnimatorSet(mBtnSubmit);
        String url = API.getUrl(API.LOGIN);
        mAccount = mEtAccount.getText().toString().trim();
        mPassword = mEtPassword.getText().toString().trim();

        if (StringUtils.isEmpty(mAccount)) {
            showToast(R.string.error_invalid_account);
            set.start();
        } else if (StringUtils.isEmpty(mPassword)) {
            showToast(R.string.error_invalid_password);
            set.start();
        } else {
            // request(url, "yoyo1", "870914");
            request(url, mAccount, mPassword);
        }
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
