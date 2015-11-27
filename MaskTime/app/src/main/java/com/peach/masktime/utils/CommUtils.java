package com.peach.masktime.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.volley.toolbox.NetworkImageView;
import com.peach.masktime.R;
import com.peach.masktime.common.manager.VolleyManager;
import com.peach.masktime.module.net.API;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
public class CommUtils {
    private static final String TAG = CommUtils.class.getSimpleName();

    public static void setImageUrl(Context context, NetworkImageView view, String url) {
        view.setDefaultImageResId(R.drawable.place_holder);
        view.setErrorImageResId(R.drawable.place_holder);
        view.setImageUrl(API.getPicUrl(url), VolleyManager.getInstance(context).getImageLoader());
    }

    /**
     * 获取应用渠道ID
     *
     * @param context 上下文
     * @return
     */
    public static String getChannel(Context context) {
        ApplicationInfo appInfo = null;
        String channel = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        channel = appInfo.metaData.getString("CHANNEL");
        Log.i(TAG, " channel == " + channel);
        return channel;
    }
}
