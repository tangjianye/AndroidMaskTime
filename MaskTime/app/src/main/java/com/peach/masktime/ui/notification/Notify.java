package com.peach.masktime.ui.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.peach.masktime.BaseApplication;
import com.peach.masktime.R;
import com.peach.masktime.ui.activity.MainActivity;
import com.peach.masktime.ui.activity.SplashActivity;
import com.peach.masktime.utils.CommUtils;
import com.peach.masktime.utils.LogUtils;

/**
 * Created by Administrator on 2015/12/9 0009.
 */
public class Notify {
    private static final String TAG = Notify.class.getSimpleName();
    private static final int TIME_UP_ID = 1;
    private static Notify sINSTANTCE;
    private static NotificationManager sManager;

    private Notify() {
    }

    public static synchronized Notify getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new Notify();
        }
        return sINSTANTCE;
    }

    public void init(Context context) {
        if (!(context instanceof BaseApplication)) {
            throw new AssertionError();
        }
        if (null == sManager) {
            sManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
    }

    public void cancel(int id) {
        sManager.cancel(id);
    }

    public void cancelAll() {
        sManager.cancelAll();
    }

    /**
     * 定时时间到的通知
     *
     * @param context
     */
    public void timeUp(Context context) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, getClickIntent(context), 0);
        // 下面需兼容Android 2.x版本是的处理方式
        // Notification notify1 = new Notification(R.drawable.message,
        // "TickerText:" + "您有新短消息，请注意查收！", System.currentTimeMillis());
        Notification notify = new Notification();
        notify.icon = R.mipmap.icon_launcher;
        notify.tickerText = context.getResources().getString(R.string.app_name);
        notify.when = System.currentTimeMillis();
        notify.setLatestEventInfo(context, "Notification Title", "This is the notification message", pendingIntent);
        notify.number = 1;
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        // notify.defaults = Notification.DEFAULT_SOUND;
        notify.sound = Uri.parse("android.resource://" +
                CommUtils.getAppPackageName(context) + "/" + CommUtils.getMusicId(context));
        sManager.notify(TIME_UP_ID, notify);
    }

    @NonNull
    private Intent getClickIntent(Context context, String activityName) {
        LogUtils.i(TAG, "activityName = " + activityName);
        Intent intent = null;
        if (null != activityName && activityName.contains(MainActivity.class.getSimpleName())) {
            intent = new Intent(context, MainActivity.class);
        } else {
            intent = new Intent(context, SplashActivity.class);
        }
        return intent;
    }

    @NonNull
    private Intent getClickIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return intent;
    }
}
