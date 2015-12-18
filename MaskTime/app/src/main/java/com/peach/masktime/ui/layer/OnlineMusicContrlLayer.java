package com.peach.masktime.ui.layer;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.module.thirdparty.Douban;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.ui.beans.PlayBean;
import com.peach.masktime.ui.service.OnlinePlayerService;
import com.peach.masktime.utils.CommUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class OnlineMusicContrlLayer extends FrameLayout implements View.OnClickListener {
    private Button mPlayContrl;
    private Status mStatus;
    private String mPath;

    public static HashMap<Status, Integer> CONTENT_MAP = new HashMap<Status, Integer>();
    public static HashMap<Status, Integer> SELECTOR_MAP = new HashMap<Status, Integer>();

    static {
        CONTENT_MAP.put(Status.IDLE, R.string.pause);
        CONTENT_MAP.put(Status.PLAY, R.string.play);
        CONTENT_MAP.put(Status.PAUSE, R.string.pause);
        CONTENT_MAP.put(Status.STOP, R.string.pause);
    }

    static {
        SELECTOR_MAP.put(Status.IDLE, R.drawable.selector_music_pause);
        SELECTOR_MAP.put(Status.PLAY, R.drawable.selector_music_play);
        SELECTOR_MAP.put(Status.PAUSE, R.drawable.selector_music_pause);
        SELECTOR_MAP.put(Status.STOP, R.drawable.selector_music_pause);
    }

    /**
     * 显示状态
     */
    public enum Status {
        /* 初始化 */
        IDLE,
        /* 开始播放 */
        PLAY,
        /* 暂停播放 */
        PAUSE,
        /* 停止播放 */
        STOP,
    }

    public OnlineMusicContrlLayer(Context context) {
        super(context);
    }

    public OnlineMusicContrlLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnlineMusicContrlLayer(Context context, AttributeSet attrs, int defStyleAttr) {
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
        Douban.getInstance().setIsDestroy(true);
    }

    private void init() {
        mPlayContrl = (Button) findViewById(R.id.btn_play_contrl);
        mPlayContrl.setOnClickListener(this);
        setPlayStatus(Status.IDLE);

        request();
    }

    private void request() {
        Douban.getInstance().setIsDestroy(false);
        Douban.getInstance().request(getContext(), new Douban.Listener() {
            @Override
            public void response(String url) {
                mPath = url;
            }

            @Override
            public void error(String error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play_contrl:
                if (isClickAble()) {
                    if (Status.PLAY == mStatus) {
                        setPlayStatus(Status.PAUSE);
                    } else if (Status.PAUSE == mStatus || Status.STOP == mStatus || Status.IDLE == mStatus) {
                        setPlayStatus(Status.PLAY);
                    }
                } else {
                    ((BaseActivity) getContext()).showToast(R.string.downloading);
                }
                break;
            default:
                break;
        }
    }

    private boolean isClickAble() {
        return null != mPath;
    }

    public void setPlayStatus(OnlineMusicContrlLayer.Status status) {
        mStatus = status;
        // mPlayContrl.setText(CONTENT_MAP.get(status));
        mPlayContrl.setText("");
        mPlayContrl.setBackgroundDrawable(getContext().getResources().getDrawable(SELECTOR_MAP.get(status)));

        PlayBean playBean = null;
        if (Status.PLAY == status) {
            playBean = new PlayBean(Constants.PlayerMsg.PLAY, mPath);
        } else if (Status.PAUSE == status) {
            playBean = new PlayBean(Constants.PlayerMsg.PAUSE, mPath);
        }
        Bundle bundle = CommUtils.getMaskBundle(playBean);
        getContext().startService(CommUtils.getMaskIntent(
                getContext().getApplicationContext(), bundle, OnlinePlayerService.class));
    }
}
