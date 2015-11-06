package com.peach.masktime.common.mgr;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.peach.masktime.config.LruBitmapCache;


/**
 * Volley网络通信单例管理类<br>
 * Created by tangjy on 2015/3/2.
 */
public class VolleyManager {

    private static final String TAG = VolleyManager.class.getSimpleName();

    private static Context sCtx;
    private static VolleyManager sINSTANTCE;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyManager(Context context) {
        sCtx = context;
    }

    public static synchronized VolleyManager getInstance(Context context) {
        if (sINSTANTCE == null) {
            sINSTANTCE = new VolleyManager(context);
        }
        return sINSTANTCE;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(sCtx.getApplicationContext());

            mImageLoader = new ImageLoader(mRequestQueue, new LruBitmapCache(LruBitmapCache.getCacheSize(sCtx)));
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    public void cancelAll(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
