package com.peach.masktime.ui.layer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.peach.masktime.common.interfaces.ICycle;
import com.peach.masktime.ui.activity.MainActivity;
import com.peach.masktime.ui.activity.SplashActivity;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.utils.LogUtils;

public class WelcomeLayer extends RelativeLayout implements ICycle {
    private static final String TAG = "WelcomeLayer";
    private final int DELAY_TIME = 1000;
    private final int ENTRY_TO_WALLPAPER_ACTIVITY = 1;
    private Animation mAnimator = null;
    private Handler mHandler;

//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case ENTRY_TO_WALLPAPER_ACTIVITY:
//                    ((BaseActivity) getContext()).openActivity(MainActivity.class);
//                    break;
//                default:
//                    break;
//            }
//            ((SplashActivity) getContext()).finish();
//            super.handleMessage(msg);
//        }
//    };

    public WelcomeLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.i(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        LogUtils.i(TAG, "onDetachedFromWindow");
        super.onDetachedFromWindow();
        cancelAnimate();
        mHandler.removeCallbacksAndMessages(null);
    }

//    @Override
//    public void show(Object obj) {
//        setVisibility(VISIBLE);
////        if (isAnimte) {
////            zoomAnimate();
////        } else {
////            entryMainActivity(DELAY_TIME);
////        }
//        entryMainActivity(DELAY_TIME);
//    }


    @Override
    public void refresh(Object obj) {
        entryMainActivity(DELAY_TIME);
    }

    @Override
    public void resume() {

    }

    private void cancelAnimate() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    private void entryMainActivity(int duration) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                Message message = Message.obtain();
//                message.what = ENTRY_TO_WALLPAPER_ACTIVITY;
//                mHandler.sendMessage(message);
                ((BaseActivity) getContext()).openActivity(MainActivity.class);
                ((SplashActivity) getContext()).finish();
            }
        }, duration);
    }
}
