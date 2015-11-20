package com.peach.masktime.utils;

import android.content.Context;

import com.android.volley.toolbox.NetworkImageView;
import com.peach.masktime.R;
import com.peach.masktime.common.manager.VolleyManager;
import com.peach.masktime.module.net.API;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
public class ComUtils {
    private static final String TAG = ComUtils.class.getSimpleName();

    public static void setImageUrl(Context cxt, NetworkImageView view, String url) {
        view.setDefaultImageResId(R.drawable.place_holder);
        view.setErrorImageResId(R.drawable.place_holder);
        view.setImageUrl(API.getPicUrl(url), VolleyManager.getInstance(cxt).getImageLoader());
    }
}
