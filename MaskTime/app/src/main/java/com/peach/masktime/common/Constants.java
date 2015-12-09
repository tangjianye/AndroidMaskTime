package com.peach.masktime.common;

/**
 * Created by Administrator on 2015/11/3 0003.
 */
public class Constants {
    /**
     * The default socket timeout in milliseconds
     */
    public static final int REQUEST_TIMEOUT_MS = 20 * 1000;

    /**
     * SP关键字
     */
    public static final String HAVED_SHOW_GUIDE = "haved_show_guide";

    /**
     * bundle关键字
     */
    public static final String BUNDLE_KEY = "bundle_key";

    /**
     * bundle关键字
     */
    public static final String BUNDLE_URL = "bundle_url";

    /**
     * bundle关键字
     */
    public static final String PLAYER_MSG = "player_msg";

    /**
     * bundle关键字
     */
    public static final String PLAYER_URL = "player_url";

    /**
     * 播放控制msg
     */
    public static class PlayerMsg {
        /* 开始播放 */
        public static final int PLAY = 1;
        /* 暂停播放 */
        public static final int PAUSE = 2;
        /* 停止播放 */
        public static final int STOP = 3;
    }
}
