package com.peach.masktime.module.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.peach.masktime.BaseApplication;
import com.peach.masktime.common.Constants;
import com.peach.masktime.config.LruBitmapCache;
import com.peach.masktime.utils.LogUtils;
import com.squareup.okhttp.OkHttpClient;

/**
 * Volley网络通信单例管理类<br>
 * Created by tangjy on 2015/3/2.
 */
public class VolleyManager {
    private static final String TAG = VolleyManager.class.getSimpleName();

    private static Context sCtx;
    private static VolleyManager sINSTANTCE;
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    private VolleyManager() {
    }

    public static synchronized VolleyManager getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new VolleyManager();
        }
        return sINSTANTCE;
    }

    public void init(Context context) {
        if (!(context instanceof BaseApplication)) {
            throw new AssertionError();
        }

        sCtx = context;
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(sCtx.getApplicationContext(), new OkHttpStack(new OkHttpClient()));
        }
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(sCtx)));
        }
    }

    @NonNull
    private DefaultRetryPolicy getRetryPolicy() {
        return new DefaultRetryPolicy(Constants.REQUEST_TIMEOUT_MS, Constants.REQUEST_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public <T> RequestQueue addToRequestQueue(Request<T> req, String tag) {
        req.setRetryPolicy(getRetryPolicy());
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        LogUtils.i(TAG, "Adding request to queue = " + req.getUrl());
        mRequestQueue.add(req);
        return mRequestQueue;
    }

    public void cancelAll(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
