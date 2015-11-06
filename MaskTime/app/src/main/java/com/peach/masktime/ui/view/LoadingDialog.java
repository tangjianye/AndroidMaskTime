package com.peach.masktime.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peach.masktime.R;

/**
 * Created by Administrator on 2015/11/6 0006.
 */
public class LoadingDialog extends LinearLayout {
    private TextView mTips;
    private Animation mOperatingAnim;

    public LoadingDialog(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.cview_loading, this);
        init();
    }

    public LoadingDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.cview_loading, this);
        init();
    }

    public LoadingDialog(Context context, TextView mTips) {
        super(context);
        this.mTips = mTips;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        if (null != mOperatingAnim) {
            mOperatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            mOperatingAnim.setInterpolator(new LinearInterpolator());
        }

        mTips = (TextView) findViewById(R.id.txt_tips);
    }

    @Override
    public void setVisibility(int visibility) {
        if (VISIBLE == visibility) {
            findViewById(R.id.iv_progress).startAnimation(mOperatingAnim);
        } else {
            if (mOperatingAnim.hasStarted()) {
                mOperatingAnim.cancel();
                findViewById(R.id.iv_progress).clearAnimation();
            }
        }
        super.setVisibility(visibility);
    }

    public void setAnimListener(AnimationListener listener) {
        mOperatingAnim.setAnimationListener(listener);
    }
}
