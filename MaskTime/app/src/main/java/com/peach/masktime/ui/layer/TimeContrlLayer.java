package com.peach.masktime.ui.layer;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.ui.notification.Notify;
import com.peach.masktime.ui.view.CircleSeekBar;
import com.peach.masktime.utils.LogUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class TimeContrlLayer extends RelativeLayout implements View.OnClickListener, Runnable {
    private static final String TAG = TimeContrlLayer.class.getSimpleName();

    /* 15分钟 */
    private static final int TIME_MAX = 15 * 60 * 1000;

    /* 1秒钟 */
    private static final int TIME_INTERVAL = 1000;

    /* 进度条起点 */
    private static final int PROGRESS_START = 50;

    // private CircleProgressBar mRoundPgbar;
    private CircleSeekBar mCircleSeekBar;
    private Button mTimeContrl;
    private TextView mTxtTime;

    private Handler mHandler;

    private Status mStatus;

    /* 当前时间单位毫秒 */
    private int mCurrTime;

    public static HashMap<Status, Integer> CONTENT_MAP = new HashMap<Status, Integer>();
    public static HashMap<Status, Integer> SELECTOR_MAP = new HashMap<Status, Integer>();

    static {
        CONTENT_MAP.put(Status.IDLE, R.string.pause);
        CONTENT_MAP.put(Status.PLAY, R.string.play);
        CONTENT_MAP.put(Status.PAUSE, R.string.pause);
        CONTENT_MAP.put(Status.STOP, R.string.pause);
    }

    static {
        SELECTOR_MAP.put(Status.IDLE, R.drawable.selector_time_pause);
        SELECTOR_MAP.put(Status.PLAY, R.drawable.selector_time_play);
        SELECTOR_MAP.put(Status.PAUSE, R.drawable.selector_time_pause);
        SELECTOR_MAP.put(Status.STOP, R.drawable.selector_time_pause);
    }

    /**
     * 显示状态
     */
    public enum Status {
        /* 初始化 */
        IDLE,
        /* 开始 */
        PLAY,
        /* 暂停 */
        PAUSE,
        /* 停止 */
        STOP,
    }

    public TimeContrlLayer(Context context) {
        super(context);
    }

    public TimeContrlLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeContrlLayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.i(TAG, "onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
        LogUtils.i(TAG, "onDetachedFromWindow");
    }

    private void init() {
        mCurrTime = (TIME_MAX * PROGRESS_START) / 100;
        mHandler = new Handler(Looper.getMainLooper());

        // mRoundPgbar = (CircleProgressBar) findViewById(R.id.round_progressbar);
        mCircleSeekBar = (CircleSeekBar) findViewById(R.id.circle_seekbar);
        mTimeContrl = (Button) findViewById(R.id.btn_time_contrl);
        mTxtTime = (TextView) findViewById(R.id.txt_time);

        mTimeContrl.setOnClickListener(this);
        mCircleSeekBar.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());

        mCircleSeekBar.setProgress(PROGRESS_START);
        setTimeTips(mCurrTime);
        setPlayStatus(Status.IDLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_time_contrl:
                if (Status.PLAY == mStatus) {
                    setPlayStatus(Status.PAUSE);
                } else if (Status.PAUSE == mStatus || Status.STOP == mStatus || Status.IDLE == mStatus) {
                    setPlayStatus(Status.PLAY);
                }
                break;
            default:
                break;
        }
    }

    public void setPlayStatus(TimeContrlLayer.Status status) {
        mStatus = status;
        // mTimeContrl.setText(CONTENT_MAP.get(status));
        mTimeContrl.setText("");
        mTimeContrl.setBackgroundDrawable(getContext().getResources().getDrawable(SELECTOR_MAP.get(status)));

        if (Status.PLAY == status) {
            start();
        } else if (Status.PAUSE == status || Status.STOP == status || Status.IDLE == status) {
            stop();
        }
    }

    private void start() {
        mHandler.removeCallbacks(this);
        mHandler.postDelayed(this, TIME_INTERVAL);
    }

    private void stop() {
        mHandler.removeCallbacks(this);
    }

    @Override
    public void run() {
        mCurrTime = mCurrTime - TIME_INTERVAL;
        if (mCurrTime >= 0) {
            setTimeTips(mCurrTime);
            setProgress(TIME_MAX, mCurrTime);
            mHandler.postDelayed(this, TIME_INTERVAL);
        } else {
            Notify.getInstance().timeUp(getContext());
            stop();
        }
    }

    private void setProgress(int max, int left) {
        int progress = 0;
        if (0 == left) {
            progress = 0;
        } else {
            progress = (int) (((float) left / (float) max) * 100) + 1;
        }

        if (progress < 0) {
            progress = 0;
        } else if (progress > 100) {
            progress = 100;
        }
        mCircleSeekBar.setProgress(progress);
    }

    /**
     * 当前时间单位毫秒
     *
     * @param ms
     */
    private void setTimeTips(int ms) {
        int left = ms / TIME_INTERVAL;
        int mode = 0;
        int time1000 = left / (10 * 60);
        mode = left % (10 * 60);
        int time0100 = mode / 60;
        mode = mode % 60;
        int time0010 = mode / 10;
        int time0001 = (time0010 > 0) ? mode % 10 : mode;
        String time = Integer.toString(time1000) + Integer.toString(time0100)
                + ":" + Integer.toString(time0010) + Integer.toString(time0001);
        // LogUtils.i(TAG, "left = " + left + " ;time = " + time);
        mTxtTime.setText(time);
    }

    private class CircleSeekBarOnChangeListener implements CircleSeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(int progress) {
            LogUtils.i(TAG, "onProgressChanged progress = " + progress);
            int max = mCircleSeekBar.getProgressMax();
            mCurrTime = (TIME_MAX * progress) / max;
            setTimeTips(mCurrTime);
        }

        @Override
        public void onStartTrackingTouch() {
            LogUtils.i(TAG, "onStartTrackingTouch");
            stop();
            setPlayStatus(Status.STOP);
        }

        @Override
        public void onStopTrackingTouch() {
            LogUtils.i(TAG, "onStopTrackingTouch");
            start();
            setPlayStatus(Status.PLAY);
        }
    }
}
