package com.peach.masktime.ui.layer;

import android.content.Context;
import android.os.Handler;
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
    private Animation mAnimator = null;
    private Handler mHandler = new Handler();

    public WelcomeLayer(Context context) {
        super(context);
    }

    public WelcomeLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.i(TAG, "onFinishInflate");
        // mHandler = new Handler(Looper.getMainLooper());
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

    }

    @Override
    public void refresh(Object obj) {
        entryMainActivity(DELAY_TIME);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        cancelAnimate();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void init() {

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
                ((BaseActivity) getContext()).openActivity(MainActivity.class);
                ((SplashActivity) getContext()).finish();
            }
        }, duration);
    }
}
