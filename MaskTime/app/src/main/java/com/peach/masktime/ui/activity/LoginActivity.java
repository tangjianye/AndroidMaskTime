package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.module.net.request.GsonRequest;
import com.peach.masktime.module.net.response.AccountItem;
import com.peach.masktime.module.net.response.MaskObjectSet;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseTitleActivity implements IInit, View.OnClickListener {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private String mAccount;
    private String mPassword;

    private EditText mEtAccount;
    private EditText mEtPassword;
    private EditText mEtEmail;

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
        setContentView(R.layout.activity_login);
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
        mTitleTips.setText(R.string.title_activity_login);
    }

    @Override
    public void initViews() {
        mEtAccount = (EditText) findViewById(R.id.et_account);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtEmail = (EditText) findViewById(R.id.et_email);
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
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        mAccount = mEtAccount.getText().toString();
        mPassword = mEtPassword.getText().toString();
        String url = API.getUrl(API.LOGIN);

        if (StringUtils.isEmpty(mAccount)) {
            showToast(R.string.error_invalid_account);
        } else if (StringUtils.isEmpty(mPassword)) {
            showToast(R.string.error_invalid_password);
        } else {
            //request(url, "yoyo1", "870914");
            request(url, mAccount, mPassword);
        }
    }

    private void request(final String url, final String account, final String password) {
        GsonRequest.GsonRequestBuilder<MaskObjectSet<AccountItem>> builder = new GsonRequest.GsonRequestBuilder<>();
        builder.setMethod(Request.Method.POST)
                .setUrl(url)
                .setType(new TypeToken<MaskObjectSet<AccountItem>>() {
                }.getType())
                .setDialog(creatLoadingDialog())
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast(R.string.default_get_data_fail);
                    }
                })
                .setListener(new Response.Listener<MaskObjectSet<AccountItem>>() {
                    @Override
                    public void onResponse(MaskObjectSet<AccountItem> response) {
                        if (null != response && response.getRsm() != null) {
                            finish();
                        } else {
                            showToast((null != response) ? response.getErr() : getString(R.string.default_get_data_fail));
                        }
                    }
                })
                .setParams(new GsonRequest.IParams() {
                    @Override
                    public Map<String, String> getParams() {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("user_name", account);
                        map.put("password", password);
                        return map;
                    }
                });

        GsonRequest<MaskObjectSet<AccountItem>> request = builder.create();
        VolleyManager.getInstance().addToRequestQueue(request, url);
    }
}
