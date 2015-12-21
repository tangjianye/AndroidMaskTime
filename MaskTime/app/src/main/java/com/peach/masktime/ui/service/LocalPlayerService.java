package com.peach.masktime.ui.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.peach.masktime.common.Constants;
import com.peach.masktime.ui.beans.PlayBean;
import com.peach.masktime.utils.CommUtils;
import com.peach.masktime.utils.LogUtils;

/**
 * Created by Administrator on 2015/12/4 0004.
 */
public class LocalPlayerService extends Service implements MediaPlayer.OnCompletionListener {
    private static final String TAG = LocalPlayerService.class.getSimpleName();
    /**
     * 播放状态
     */
    // public static int sStatus;
    /**
     * 本地播放音乐列表
     * Test: "http://mr7.doubanio.com//1d624289165da693288499428d624165//0//fm//song//p1772376_128k.mp4"
     */
    private String mPath = null;
    /**
     * 用户操作
     */
    private static int mMsg;
    /**
     * 是否单曲循环
     */
    private static final boolean IS_LOOP = false;
    /**
     * 播放器
     */
    private static MediaPlayer mMediaPlayer = null;

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
        getData(intent);

        if (mMsg == Constants.PlayerMsg.PLAY) {
            playMusic();
        } else if (mMsg == Constants.PlayerMsg.PAUSE) {
            pauseMusic();
        } /*else if (mMsg == Constants.PlayerMsg.STOP) {
            stopMusic();
        }*/

        return super.onStartCommand(intent, flags, startId);
    }

    private void getData(Intent intent) {
        PlayBean info = (PlayBean) CommUtils.getMaskSerializable(intent);
        if (null != info) {
            mMsg = info.getMsg();
            mPath = info.getPath();
        }
        LogUtils.i(TAG, "onStartCommand info = " + info);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.i(TAG, "onUnbind");
        stopMusic();
        return super.onUnbind(intent);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // mediaPlayer.release();
        LogUtils.i(TAG, "onCompletion");
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
        try {
            /* 重置多媒体 */
            mMediaPlayer.reset();
            /* 读取mp3文件 */
            // mMediaPlayer.setDataSource(mPath);
            AssetFileDescriptor fd = getAssets().openFd(mPath);
            mMediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            LogUtils.i(TAG, "playMusic getStartOffset() = " + fd.getStartOffset() + " ;getLength()" + fd.getLength());
            /* 准备播放 */
            mMediaPlayer.prepare();
            /* 开始播放 */
            mMediaPlayer.start();
            /* 是否单曲循环 */
            mMediaPlayer.setLooping(IS_LOOP);
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
