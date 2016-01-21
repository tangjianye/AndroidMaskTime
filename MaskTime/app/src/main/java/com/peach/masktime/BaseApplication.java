package com.peach.masktime;

import android.app.Application;

import com.peach.masktime.common.AppManager;
import com.peach.masktime.db.DBManager;
import com.peach.masktime.module.image.ImageLoaderManager;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.ui.notification.Notify;

public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        // CrashException.getInstance().init(this);
        DBManager.getInstance().init(this);
        ImageLoaderManager.getInstance().init(this);
        VolleyManager.getInstance().init(this);
        Notify.getInstance().init(this);

        // CommUtils.getChannel(this);
        // CommUtils.getAppInfo(this);
    }

    public void exitApp(boolean isKillProcess) {
        AppManager.getInstance().appExit(this, isKillProcess);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
