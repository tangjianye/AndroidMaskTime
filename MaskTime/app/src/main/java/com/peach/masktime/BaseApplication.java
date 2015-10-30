package com.peach.masktime;

import android.app.Application;

import com.peach.masktime.common.AppManager;

public class BaseApplication extends Application {
    private static final String TAG = BaseApplication.class.getSimpleName();
    private AppManager mAppManager;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
//        DBManager.getInstance().initialize(this);
        mAppManager = AppManager.getInstance();
    }

    public void exitApp(boolean isKillProcess) {
        mAppManager.appExit(this, isKillProcess);
//        DBController.getInstance().clearAllCustomer();
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
