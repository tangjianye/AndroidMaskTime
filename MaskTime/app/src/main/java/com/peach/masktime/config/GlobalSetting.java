package com.peach.masktime.config;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;

import java.util.HashMap;

/**
 * Created by tangjy on 2015/10/24.
 */
public class GlobalSetting {
    public static final String DATABASE_NAME = "masktime";
    // private static final String CHANNEL = "CHANNEL";

    /**
     * 当前app的运行模式
     */
    public final boolean DEBUG = true;

    /**
     * 当前app的是否写Log
     */
    public boolean mIsLogined = false;

//    /**
//     * 音乐播放数据 assects
//     */
//    public static HashMap<String, String> PATH_MAP = new HashMap<String, String>();
//
//    static {
//        PATH_MAP.put(Constants.Music.ITEM_01, "bird.mp3");
//        PATH_MAP.put(Constants.Music.ITEM_02, "hmyx.mp3");
//        PATH_MAP.put(Constants.Music.ITEM_03, "pikachu.mp3");
//        PATH_MAP.put(Constants.Music.ITEM_04, "xqx.mp3");
//    }

    /**
     * 音乐播放数据 raw
     */
    public static HashMap<String, Integer> MUSIC_MAP = new HashMap<String, Integer>();

    static {
        MUSIC_MAP.put(Constants.Music.ITEM_01, R.raw.bird);
        MUSIC_MAP.put(Constants.Music.ITEM_02, R.raw.hmyx);
        MUSIC_MAP.put(Constants.Music.ITEM_03, R.raw.pikachu);
        MUSIC_MAP.put(Constants.Music.ITEM_04, R.raw.xqx);
    }

    /**
     * 音乐名字数据
     */
    public static HashMap<String, Integer> CONTENT_MAP = new HashMap<String, Integer>();

    static {
        CONTENT_MAP.put(Constants.Music.ITEM_01, R.string.setting_music_bird);
        CONTENT_MAP.put(Constants.Music.ITEM_02, R.string.setting_music_dream);
        CONTENT_MAP.put(Constants.Music.ITEM_03, R.string.setting_music_xqx);
        CONTENT_MAP.put(Constants.Music.ITEM_04, R.string.setting_music_pkq);
    }
}
