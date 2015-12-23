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
import com.peach.masktime.module.net.GsonRequest;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.module.net.response.AccountItem;
import com.peach.masktime.module.net.response.MaskSet;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.utils.LogUtils;

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

        // mEtEmail.setVisibility(View.GONE);
    }

    @Override
    public void initEvents() {
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                mAccount = mEtAccount.getText().toString();
                mPassword = mEtPassword.getText().toString();

                String url = API.getUrl(API.LOGIN);
                // StringRequest request = new StringRequest(Request.Method.POST, url, new R)
                LogUtils.i(TAG, "url -> " + url);

                GsonRequest.GsonRequestBuilder<MaskSet<AccountItem>> builder = new GsonRequest.GsonRequestBuilder<>();
                builder.setMethod(Request.Method.POST)
                        .setUrl(url)
                        .setType(new TypeToken<MaskSet<AccountItem>>() {
                        }.getType())
                        .setDialog(creatLoadingDialog())
                        .setErrorListener(new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                showToast(R.string.default_get_data_fail);
                            }
                        })
                        .setListener(new Response.Listener<MaskSet<AccountItem>>() {
                            @Override
                            public void onResponse(MaskSet<AccountItem> response) {
                                if (null != response && response.getRsm() != null && response.getRsm().size() > 0) {

                                } else {
                                    showToast(response.getErr());
                                }
                            }
                        })
                        .setMap(getParams());

                GsonRequest<MaskSet<AccountItem>> request = builder.create();
                VolleyManager.getInstance().addToRequestQueue(request, url);

//                StringRequest aaa = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                LogUtils.i(TAG, "response -> " + response);
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        LogUtils.e(TAG, error.getMessage(), error);
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        //在这里设置需要post的参数
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("user_name", "yoyo1");
//                        map.put("password", "870914");
//                        return map;
//                    }
//                };

//                VolleyManager.getInstance().addToRequestQueue(aaa, TAG);
                break;
            default:
                break;
        }
    }

    Map<String, String> getParams() {
        //在这里设置需要post的参数
        Map<String, String> map = new HashMap<String, String>();
        map.put("user_name", "yoyo1");
        map.put("password", "870914");
        return map;
    }
}
