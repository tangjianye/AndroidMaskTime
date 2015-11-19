package com.peach.masktime.ui.activity;

import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.common.mgr.VolleyManager;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.utils.LogUtils;

public class StoreActivity extends BaseTitleActivity implements IInit {
    public static final String TAG = StoreActivity.class.getSimpleName();

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
        setContentView(R.layout.activity_store);
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
        request();
    }

    @Override
    public void initTitles() {
        mTitleTips.setText(R.string.main_store);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    private void request() {
        String url = "http://mask.cloudsdee.com/?/api/shop/get_goods/category_id=7&page=1";
        LogUtils.i(TAG, "request url = " + url);

        RequestQueue rq = VolleyManager.getInstance(this).getRequestQueue();

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.i(TAG, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i(TAG, error.getMessage(), error);
            }
        });

        rq.add(stringRequest);
        stringRequest.setTag("aaa");
        rq.start();
    }
}
