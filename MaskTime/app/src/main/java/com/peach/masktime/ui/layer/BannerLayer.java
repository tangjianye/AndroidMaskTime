package com.peach.masktime.ui.layer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.ICycle;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.GsonRequest;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.module.net.response.AlbumSet;
import com.peach.masktime.ui.view.AutoScrollBanner;
import com.peach.masktime.utils.LogUtils;

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

    @Override
    public void destroy() {

    }

    @Override
    public void init() {

    }

    private void request(final String url) {
        GsonRequest.GsonRequestBuilder<AlbumSet> builder = new GsonRequest.GsonRequestBuilder<>();
        builder.setMethod(Request.Method.GET)
                .setUrl(url)
                .setBclass(AlbumSet.class)
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
                .setListener(new Response.Listener<AlbumSet>() {
                    @Override
                    public void onResponse(AlbumSet response) {
                        if (null != response && response.getRsm() != null && response.getRsm().size() > 0) {
                            mAutoBanner.refresh(response.getRsm());
                        }
                    }
                });

        GsonRequest<AlbumSet> request = builder.create();
        VolleyManager.getInstance().addToRequestQueue(request, url);
    }

    @NonNull
    private String getUrl() {
        return API.getCategoryUrl(API.CATEGORY_BANNER, API.PAGE_BANNER);
    }
}
