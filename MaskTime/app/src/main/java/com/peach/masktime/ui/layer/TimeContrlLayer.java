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
import com.peach.masktime.utils.LogUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class TimeContrlLayer extends RelativeLayout implements View.OnClickListener, Runnable {
    private static final String TAG = TimeContrlLayer.class.getSimpleName();

    /* 15分钟 */
    private static final int TIME_MAX = 1 * 60 * 1000;

    /* 1秒钟 */
    private static final int TIME_INTERVAL = 1000;

    private Button mTimeContrl;
    private TextView mTxtTime;

    private Handler mHandler;

    private Status mStatus;
    private int mCount = 0;

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
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void init() {
        mHandler = new Handler(Looper.getMainLooper());

        mTimeContrl = (Button) findViewById(R.id.btn_time_contrl);
        mTxtTime = (TextView) findViewById(R.id.txt_time);

        mTimeContrl.setOnClickListener(this);

        setTimeTips(TIME_MAX / TIME_INTERVAL);
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
        // 单位是1秒钟
        int left = TIME_MAX / TIME_INTERVAL - mCount;
        if (left >= 0) {
            setTimeTips(left);
            mHandler.postDelayed(this, TIME_INTERVAL);
            mCount++;
        } else {
            // Notify.getInstance().timeUp(getContext());
            stop();
        }
    }

    /**
     * 剩余多少秒
     *
     * @param left
     */
    private void setTimeTips(int left) {
        int mode = 0;
        int time1000 = left / (10 * 60);
        mode = left % (10 * 60);
        int time0100 = mode / 60;
        mode = mode % 60;
        int time0010 = mode / 10;
        int time0001 = (time0010 > 0) ? mode % 10 : mode;
        String time = Integer.toString(time1000) + Integer.toString(time0100)
                + ":" + Integer.toString(time0010) + Integer.toString(time0001);
        LogUtils.i(TAG, "mCount = " + mCount + " ;left = " + left + " ;time = " + time);
        mTxtTime.setText(time);
    }
}
