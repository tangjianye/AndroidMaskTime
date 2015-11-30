package com.peach.masktime.ui.layer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.ICycle;
import com.peach.masktime.common.manager.VolleyManager;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.response.AlbumSet;
import com.peach.masktime.ui.view.AutoScrollBanner;
import com.peach.masktime.utils.JsonUtils;
import com.peach.masktime.utils.LogUtils;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class BannerLayer extends LinearLayout implements ICycle {
    private static final String TAG = BannerLayer.class.getSimpleName();
    private AutoScrollBanner mAutoBanner;

    public BannerLayer(Context context) {
        super(context);
    }

    public BannerLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.i(TAG, "onAttachedToWindow");
        request(getUrl());
    }

    @Override
    protected void onDetachedFromWindow() {
        LogUtils.i(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mAutoBanner = (AutoScrollBanner) findViewById(R.id.bv_auto);
    }

    @Override
    public void refresh(Object obj) {

    }

    @Override
    public void resume() {

    }

    private void request(final String url) {
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.i(TAG, "response = " + response);

                        if (null != response) {
                            Type type = new TypeToken<AlbumSet>() {
                            }.getType();
                            AlbumSet set = JsonUtils.parseJson(response, type);
                            LogUtils.i(TAG, "set = " + set);
                            if (null != set && set.getRsm() != null && set.getRsm().size() > 0) {
                                mAutoBanner.refresh(set.getRsm());
                            } else {
                                LogUtils.i(TAG, "广告数据为空");
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i(TAG, error.getMessage(), error);
            }
        });

        RequestQueue rq = VolleyManager.getInstance(getContext()).getRequestQueue();
        rq.add(stringRequest);
        stringRequest.setTag(url);
        rq.start();
    }

    @NonNull
    private String getUrl() {
        return API.getCategoryUrl(API.CATEGORY_BANNER, API.PAGE_BANNER);
    }
}
