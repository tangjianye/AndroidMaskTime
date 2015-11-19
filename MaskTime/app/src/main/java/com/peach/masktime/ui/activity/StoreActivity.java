package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.common.manager.VolleyManager;
import com.peach.masktime.module.net.response.BannerSet;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.utils.JsonUtils;
import com.peach.masktime.utils.LogUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;

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
        request("json");
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
                        // LogUtils.i(TAG, "response = " + response);
                        BannerSet bannerSet = new BannerSet();
                        Type type = new TypeToken<BannerSet>() {
                        }.getType();
                        bannerSet = JsonUtils.parseJson(response, type);
                        LogUtils.i(TAG, "bannerSet = " + bannerSet.toString());
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

    private void request(String aaa) {
        String url = "http://mask.cloudsdee.com/?/api/shop/get_goods/category_id=7&page=1";
        LogUtils.i(TAG, "request url = " + url);

        RequestQueue rq = VolleyManager.getInstance(this).getRequestQueue();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        jsonObjectRequest.setTag("aaa");
        rq.start();
    }
}
