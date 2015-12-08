package com.peach.masktime.ui.layer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.utils.CommUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class MusicContrlLayer extends FrameLayout implements View.OnClickListener {
    private Button mPlayContrl;

    private Status mStatus;

    public static HashMap<Status, Integer> CONTENT_MAP = new HashMap<Status, Integer>();
    public static HashMap<Status, Integer> SELECTOR_MAP = new HashMap<Status, Integer>();

    static {
        CONTENT_MAP.put(Status.PLAY, R.string.play);
        CONTENT_MAP.put(Status.PAUSE, R.string.pause);
        CONTENT_MAP.put(Status.STOP, R.string.play);
    }

    static {
        SELECTOR_MAP.put(Status.PLAY, R.drawable.selector_music_play);
        SELECTOR_MAP.put(Status.PAUSE, R.drawable.selector_music_pause);
        SELECTOR_MAP.put(Status.STOP, R.drawable.selector_music_play);
    }

    /**
     * 显示状态
     */
    public enum Status {
        /* 开始播放 */
        PLAY,
        /* 暂停播放 */
        PAUSE,
        /* 停止播放 */
        STOP,
    }

    public MusicContrlLayer(Context context) {
        super(context);
    }

    public MusicContrlLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MusicContrlLayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mPlayContrl = (Button) findViewById(R.id.btn_play_contrl);
        mPlayContrl.setOnClickListener(this);
        setPlayStatus(Status.PLAY);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play_contrl:
                if (Status.PLAY == mStatus) {
                    getContext().startService(CommUtils.getPlayerIntent(getContext(), Constants.PlayerMsg.PLAY));
                    setPlayStatus(Status.PAUSE);
                } else if (Status.PAUSE == mStatus) {
                    getContext().startService(CommUtils.getPlayerIntent(getContext(), Constants.PlayerMsg.PAUSE));
                    setPlayStatus(Status.PLAY);
                }
                break;
            default:
                break;
        }
    }

    public void setPlayStatus(MusicContrlLayer.Status status) {
        mStatus = status;
        // mPlayContrl.setText(CONTENT_MAP.get(status));
        mPlayContrl.setText("");
        mPlayContrl.setBackgroundDrawable(getContext().getResources().getDrawable(SELECTOR_MAP.get(status)));
    }
}
