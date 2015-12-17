package com.peach.masktime.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.volley.toolbox.NetworkImageView;
import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.ui.service.OnlinePlayerService;

import java.util.List;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
public class CommUtils {
    private static final String TAG = CommUtils.class.getSimpleName();
    private static final String CHANNEL = "CHANNEL";

    /**
     * common
     **********************************************************************************************/
    /**
     * 获取meta信息
     *
     * @param context
     * @param metaKey
     * @return
     */
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
            e.printStackTrace();
        }
        return apiKey;
    }

    /**
     * 获取当前activity
     *
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = manager.getRunningTasks(1);

        if (taskInfo != null) {
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            return componentInfo.getClassName();
        }
        return null;
    }

    /**
     * 获取apk信息
     *
     * @param context
     * @return
     */
    public static void getAppInfo(Context context) {
        String versionName = "0.0";
        int versionCode = 0;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName; // 版本名
            versionCode = info.versionCode; // 版本号
            System.out.println(versionCode + " " + versionName);
            LogUtils.i(TAG, "packageName = " + context.getPackageName()
                    + " ;versionName = " + versionName + " ;versionCode = " + versionCode);
        } catch (Exception e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
    }

    /**
     * private
     **********************************************************************************************/
    /**
     * 获取应用渠道ID
     *
     * @param context 上下文
     * @return
     */
    public static String getChannel(Context context) {
        String channel = getMetaValue(context, CHANNEL);
        LogUtils.i(TAG, "channel = " + channel);
        return channel;
    }

    /**
     * 设置图片显示
     *
     * @param context
     * @param view
     * @param url
     */
    public static void setImageUrl(Context context, NetworkImageView view, String url) {
        view.setDefaultImageResId(R.drawable.place_holder);
        view.setErrorImageResId(R.drawable.place_holder);
        view.setImageUrl(API.getPicUrl(url), VolleyManager.getInstance().getImageLoader());
    }

    /**
     * 设置播放信息
     *
     * @param context
     * @param msg
     * @return
     */
    public static Intent getPlayerIntent(Context context, int msg, String url) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.PLAYER_URL, url);
        bundle.putInt(Constants.PLAYER_MSG, msg);
        intent.putExtra(Constants.BUNDLE_KEY, bundle);
        intent.setClass(context, OnlinePlayerService.class);
        return intent;
    }
}
