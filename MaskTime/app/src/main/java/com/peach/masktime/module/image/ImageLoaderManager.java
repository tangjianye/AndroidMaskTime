package com.peach.masktime.module.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.peach.masktime.BaseApplication;
import com.peach.masktime.utils.LogUtils;

/**
 * Volley网络通信单例管理类<br>
 * Created by tangjy on 2015/3/2.
 */
public class ImageLoaderManager {
    private static final String TAG = ImageLoaderManager.class.getSimpleName();
    private static Context sCtx;
    private static ImageLoaderManager sINSTANTCE;

    private ImageLoaderManager() {
    }

    public static synchronized ImageLoaderManager getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new ImageLoaderManager();
        }
        return sINSTANTCE;
    }

    public void init(Context context) {
        if (!(context instanceof BaseApplication)) {
            throw new AssertionError();
        }

        sCtx = context;
        ImageLoaderConfig.initImageLoader(context, null);
    }

    public void loadImage(ImageView imageView, String uri) {
        ImageLoaderConfig.getImageLoader().displayImage(uri, imageView, ImageLoaderConfig.getDefaultOptions(), loadingListener);
    }

    public void loadImage(ImageView imageView, String uri, ImageLoadingListener listener) {
        ImageLoaderConfig.getImageLoader().displayImage(uri, imageView, ImageLoaderConfig.getDefaultOptions(), listener);
    }

    private ImageLoadingListener loadingListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String imageUri, View view) {
            LogUtils.i(TAG, "imageUri = " + imageUri);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            LogUtils.i(TAG, "imageUri = " + imageUri);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            LogUtils.i(TAG, "imageUri = " + imageUri);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            LogUtils.i(TAG, "imageUri = " + imageUri);
        }
    };
}
