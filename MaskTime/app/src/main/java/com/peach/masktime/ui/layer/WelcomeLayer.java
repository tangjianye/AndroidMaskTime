package com.peach.masktime.ui.layer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.peach.masktime.common.interfaces.ICycle;
import com.peach.masktime.ui.activity.MainActivity;
import com.peach.masktime.ui.activity.WelcomeActivity;
import com.peach.masktime.ui.base.BaseActivity;

public class WelcomeLayer extends RelativeLayout implements ICycle {
    private static final String TAG = "WelcomeLayer";
    private final int DELAY_TIME = 1000;
    private final int ENTRY_TO_WALLPAPER_ACTIVITY = 1;
    private Animation mAnimator = null;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENTRY_TO_WALLPAPER_ACTIVITY:
                    ((BaseActivity) getContext()).openActivity(MainActivity.class);
                    break;
                default:
                    break;
            }
            ((WelcomeActivity) getContext()).finish();
            super.handleMessage(msg);
        }
    };

    public WelcomeLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void init() {
    }

    @Override
    public void show(boolean isAnimte) {
        setVisibility(VISIBLE);
//        if (isAnimte) {
//            zoomAnimate();
//        } else {
//            entryWallpaperActivity(DELAY_TIME);
//        }
        entryWallpaperActivity(DELAY_TIME);
    }

    @Override
    public void hide(boolean isAnimte) {
        setVisibility(GONE);
    }

    @Override
    public void pause() {
    }

    @Override
    public void destroy() {
        cancelAnimate();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void cancelAnimate() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

//    private void zoomAnimate() {
//        View first = this.findViewById(R.id.first);
//        first.setAlpha(1f);
//        View second = this.findViewById(R.id.second);
//        second.setAlpha(1f);
//        View third = this.findViewById(R.id.third);
//        third.setAlpha(1f);
//
//        AnimatorSet set = AnimatorUtils.createAnimatorSet();
//        set.setDuration(800);
//        Animator a1 = AnimatorUtils.ofFloat(first, "alpha", 1f, 0f);
//        Animator a2 = AnimatorUtils.ofFloat(second, "alpha", 1f, 0f);
//        Animator a3 = AnimatorUtils.ofFloat(third, "alpha", 1f, 0f);
//        set.addListener(new AnimatorListener() {
//            private boolean isCanceled = false;
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                LogUtils.d(TAG, "onAnimationEnd: " + isCanceled);
//                if (isCanceled) {
//                    return;
//                }
//                entryWallpaperActivity(0);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                LogUtils.d(TAG, "onAnimationCancel");
//                isCanceled = true;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//
//        });
//        set.setStartDelay(350);
//        set.play(a2).before(a3);
//        set.play(a2).after(a1);
//        set.start();
//    }

    private void entryWallpaperActivity(int duration) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = ENTRY_TO_WALLPAPER_ACTIVITY;
                mHandler.sendMessage(message);
            }
        }, duration);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void resume() {

    }
}
