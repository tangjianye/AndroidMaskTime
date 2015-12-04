package com.peach.masktime.ui.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.utils.LogUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/4 0004.
 */
public class PlayerService extends Service implements MediaPlayer.OnCompletionListener {
    private static final String TAG = PlayerService.class.getSimpleName();

    /* 是否单曲循环 */
    private static final boolean sIsLoop = true;
    /* 播放器 */
    public static MediaPlayer mMediaPlayer = null;
    /* 用户操作 */
    private int mPlayerMsg;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.i(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i(TAG, "onCreate");
        initMusic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.i(TAG, "onDestroy");
        stopMusic();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayerMsg = intent.getIntExtra(Constants.PLAYER_MSG, Constants.PlayerMsg.PLAY);
        LogUtils.i(TAG, "onStartCommand mPlayerMsg = " + mPlayerMsg);

        if (mPlayerMsg == Constants.PlayerMsg.PLAY) {
            playMusic();
        } else if (mPlayerMsg == Constants.PlayerMsg.PAUSE) {
            pauseMusic();
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.i(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mMediaPlayer.release();
    }

    /**
     * 初始化
     */
    private void initMusic() {
        stopMusic();

        /* 初始化 */
        // mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sky);
        /* 监听播放是否完成 */
        mMediaPlayer.setOnCompletionListener(this);
    }

    /**
     * 暂停
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
    public void playMusic() {
        try {
            /* 重置多媒体 */
            mMediaPlayer.reset();
            /* 读取mp3文件 */
            // mMediaPlayer.setDataSource(this, R.raw.sky);
            /* 准备播放 */
            mMediaPlayer.prepare();
            /* 开始播放 */
            mMediaPlayer.start();
            /* 是否单曲循环 */
            mMediaPlayer.setLooping(sIsLoop);
            // 设置进度条最大值
//            TestMediaPlayer.audioSeekBar.setMax(PlayerService.mMediaPlayer
//                    .getDuration());
//            new Thread(this).start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
