package com.peach.masktime.ui.layer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.peach.masktime.R;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.utils.LogUtils;

import java.util.HashMap;

/**
 * Created by Administrator on 2015/12/7 0007.
 */
public class AssectsMusicContrlLayer extends FrameLayout implements MediaPlayer.OnCompletionListener {
    private static final String TAG = AssectsMusicContrlLayer.class.getSimpleName();

    private View mPlayContrl;
    private Status mStatus;
    private String mPath;

    /**
     * 播放器
     */
    private MediaPlayer mMediaPlayer = null;

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
        /* 启动播放 */
        PLAY,
        /* 暂停|开始播放 */
        PAUSE,
        /* 停止播放 */
        STOP,
    }

    public AssectsMusicContrlLayer(Context context) {
        super(context);
        init(context);
    }

    public AssectsMusicContrlLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AssectsMusicContrlLayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopPlay();
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.local_music_contrl, this);

        mPlayContrl = findViewById(R.id.btn_play_contrl);
        // mPlayContrl.setOnClickListener(this);

        setPlayBtnUI(Status.IDLE);
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        this.mPath = path;
    }

    public void stopPlay() {
        setPlayBtnUI(Status.IDLE);
        stopMusic();
    }

    public void startPlay() {
        LogUtils.i(TAG, "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx mStatus = " + mStatus);
        if (isClickAble()) {
            if (Status.IDLE == mStatus) {
                setPlayBtnUI(Status.PLAY);
                playMusic();
            } else if (Status.PLAY == mStatus) {
                setPlayBtnUI(Status.PAUSE);
                pauseMusic();
            } else if (Status.PAUSE == mStatus) {
                setPlayBtnUI(Status.PLAY);
                pauseMusic();
            }
        } else {
            ((BaseActivity) getContext()).showToast(R.string.downloading);
        }
    }

    private boolean isClickAble() {
        return null != mPath;
    }

    private void setPlayBtnUI(Status status) {
        mStatus = status;
        // mPlayContrl.setText(CONTENT_MAP.get(status));
        // mPlayContrl.setText("");
        mPlayContrl.setBackgroundDrawable(getContext().getResources().getDrawable(SELECTOR_MAP.get(status)));
    }

//    private void setPlayStatus(int msg) {
//        PlayBean playBean = new PlayBean(msg, mPath);
//        Bundle bundle = CommUtils.getMaskBundle(playBean);
//        getContext().startService(CommUtils.getMaskIntent(getContext(), bundle, LocalPlayerService.class));
//    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mMediaPlayer.release();
        mMediaPlayer = null;
        setPlayBtnUI(Status.IDLE);
    }

    /**
     * 初始化
     */
    private void initMusic() {
        // stopMusic();

        /* 初始化 */
        mMediaPlayer = new MediaPlayer();
        /* 监听播放是否完成 */
        mMediaPlayer.setOnCompletionListener(this);
    }

    /**
     * 暂停或者播放
     */
    private void pauseMusic() {
        try {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放
     */
    private void playMusic() {
        if (null == mMediaPlayer) {
            initMusic();
        }

        try {
            /* 重置多媒体 */
            mMediaPlayer.reset();
            /* 读取mp3文件 */
            // mMediaPlayer.setDataSource(mPath);
            AssetFileDescriptor fd = getContext().getAssets().openFd(mPath);
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            LogUtils.i(TAG, "playMusic getStartOffset() = " + fd.getStartOffset() + " ;getLength()" + fd.getLength());
            /* 准备播放 */
            mMediaPlayer.prepare();
            /* 开始播放 */
            mMediaPlayer.start();
            /* 是否单曲循环 */
            mMediaPlayer.setLooping(false);
            // 设置进度条最大值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止
     */
    private void stopMusic() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
