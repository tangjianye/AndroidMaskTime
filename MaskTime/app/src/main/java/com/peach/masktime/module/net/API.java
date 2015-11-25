package com.peach.masktime.module.net;

import android.os.Environment;

import com.peach.masktime.utils.FileUtils;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class API {
    /**
     * 后台api接口
     **********************************************************************************************/
    private static final String TEST_MODE = "/masktime0123456789";
    private static final String DOMAIN = "http://mask.cloudsdee.com/?";
    private static final String TEST_DOMAIN = "http://113.107.245.39:92";
    private static final String GET_PICTURE = "http://mask.cloudsdee.com/uploads/kshop/";

    public static final String SHOP_GET_GOODS = "/api/shop/get_goods/?";

    public static final int PAGE_BANNER = 1;
    public static final int CATEGORY_BANNER = 7;
    public static final int CATEGORY_CONTENT = 8;

    /**
     * 获取后台接口
     **********************************************************************************************/
    public static boolean isTestMode() {
        String sdcard = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        return FileUtils.isFolderExist(sdcard + TEST_MODE);
    }

    private static String domain(boolean test) {
        return test ? TEST_DOMAIN : DOMAIN;
    }

    public static String getUrl(String urlPath) {
        return domain(isTestMode()) + urlPath;
    }

    public static String getPicUrl(String urlPath) {
        return GET_PICTURE + urlPath;
    }
}
