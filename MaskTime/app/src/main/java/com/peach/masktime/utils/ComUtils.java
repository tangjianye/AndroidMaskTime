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
//    /**
//     * 后台api接口
//     **********************************************************************************************/
//    private static final String TEST_MODE = "/masktime0123456789";
//    private static final String DOMAIN = "http://mask.cloudsdee.com/?";
//    private static final String TEST_DOMAIN = "http://113.107.245.39:92";
//
//    public static final String SHOP_GET_GOODS = "/api/shop/get_goods/?";
//
//
//    /**
//     * 获取后台接口
//     **********************************************************************************************/
//    public static boolean isTestMode() {
//        String sdcard = Environment.getExternalStorageDirectory()
//                .getAbsolutePath();
//        return FileUtils.isFolderExist(sdcard + TEST_MODE);
//    }
//
//    private static String domain(boolean test) {
//        return test ? TEST_DOMAIN : DOMAIN;
//    }
//
//    public static String getUrl(String urlPath) {
//        return domain(isTestMode()) + urlPath;
//    }

    public static void setImageUrl(Context cxt, NetworkImageView view, String url) {
        view.setDefaultImageResId(R.drawable.place_holder);
        view.setErrorImageResId(R.drawable.place_holder);
        view.setImageUrl(API.getPicUrl(url), VolleyManager.getInstance(cxt).getImageLoader());
    }
}
