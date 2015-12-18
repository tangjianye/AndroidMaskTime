package com.peach.masktime.common;

/**
 * Created by Administrator on 2015/11/3 0003.
 */
public class Constants {
    /**
     * The default socket timeout in milliseconds
     */
    public static final int REQUEST_TIMEOUT_MS = 30 * 1000;

    /**
     * The default number of retries
     */
    public static final int REQUEST_MAX_RETRIES = 1;

    /**
     * SP关键字
     */
    public static final String HAVED_SHOW_GUIDE = "haved_show_guide";

    /**
     * intent关键字
     */
    public static final String INTENT_KEY = "intent_key";

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
     * bundle关键字
     */
    public static final String PLAYER_RES_ID = "res_id";

    /**
     * 播放控制msg
     */
    public static class PlayerMsg {
        /* 初始化状态 */
        public static final int IDLE = 0;
        /* 开始播放 */
        public static final int PLAY = 1;
        /* 暂停播放 */
        public static final int PAUSE = 2;
        /* 下一首 */
        public static final int NEXT = 4;
        /* 上一首 */
        public static final int PREV = 5;
        /* 停止播放 */
        public static final int STOP = 6;
    }
}
