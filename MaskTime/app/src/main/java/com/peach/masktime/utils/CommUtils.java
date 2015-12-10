package com.peach.masktime.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.volley.toolbox.NetworkImageView;
import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.ui.service.PlayerService;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
public class CommUtils {
    private static final String TAG = CommUtils.class.getSimpleName();

    public static void setImageUrl(Context context, NetworkImageView view, String url) {
        view.setDefaultImageResId(R.drawable.place_holder);
        view.setErrorImageResId(R.drawable.place_holder);
        view.setImageUrl(API.getPicUrl(url), VolleyManager.getInstance().getImageLoader());
    }

    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    /**
     * 获取应用渠道ID
     *
     * @param context 上下文
     * @return
     */
    public static String getChannel(Context context) {
        return getMetaValue(context, "CHANNEL");
    }

    /**
     * @param ctx
     * @param msg
     * @return
     */
    public static Intent getPlayerIntent(Context ctx, int msg, String url) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PLAYER_URL, url);
        bundle.putInt(Constants.PLAYER_MSG, msg);
        intent.putExtra(Constants.BUNDLE_KEY, bundle);
        intent.setClass(ctx, PlayerService.class);
        return intent;
    }
}
