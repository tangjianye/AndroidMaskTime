package com.peach.masktime.module.thirdparty;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class Douban {
    private static final String TAG = Douban.class.getSimpleName();
    /**
     * 获取频道ID
     */
    private static final String DOUBAN_CHANNELS = "http://douban.fm/j/explore/hot_channels";
    /**
     * 获取播放列表
     */
    private static final String DOUBAN_PLAY_LIST = "http://douban.fm/j/mine/playlist";

    private boolean isDestroy = false;

    private static Douban sINSTANTCE;

    private Douban() {

    }

    public static synchronized Douban getInstance() {
        if (sINSTANTCE == null) {
            sINSTANTCE = new Douban();
        }
        return sINSTANTCE;
    }

    private String getListUrl(int channel) {
        String url = DOUBAN_PLAY_LIST + "?type=n&sid=&pt=0.0&from=mainsite&r=c7bc353d05&channel=" + channel;
        LogUtils.i(TAG, "getPlaylistUrl url = " + url);
        return url;
    }

    public void request(final Context context, final Douban.Listener listener) {
        requestChannel(context, DOUBAN_CHANNELS, listener);
    }

    private void requestChannel(final Context context, String url, final Listener listener) {
        LogUtils.i(TAG, "request url = " + url);

        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            LogUtils.i(TAG, "response = " + response.toString(50));
                            JSONObject data = response.getJSONObject("data");
                            JSONArray channels = data.getJSONArray("channels");
                            if (channels.length() > 0) {
                                int pos = new Random().nextInt(channels.length());
                                JSONObject channel = (JSONObject) channels.get(pos);
                                int id = channel.getInt("id");

                                // 获取音乐列表
                                isRequestPlayList(context, id, listener);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.error("requestChannel: JSONException");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i(TAG, error.getMessage(), error);
                listener.error(error.getMessage());
            }
        });

        VolleyManager.getInstance().addToRequestQueue(request, url);
    }

    private void isRequestPlayList(final Context context, int id, final Listener listener) {
        if (!isDestroy()) {
            requestList(context, getListUrl(id), listener);
        }
    }

    private void requestList(Context context, String url, final Listener listener) {
        LogUtils.i(TAG, "request url = " + url);

        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            LogUtils.i(TAG, "response = " + response.toString(50));
                            String url = null;
                            JSONArray song = response.getJSONArray("song");
                            if (song.length() > 0) {
                                JSONObject channel = (JSONObject) song.get(0);
                                url = channel.getString("url");
                                listener.response(url);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.error("JSONException");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i(TAG, error.getMessage(), error);
                listener.error(error.getMessage());
            }
        });

        VolleyManager.getInstance().addToRequestQueue(request, url);
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setIsDestroy(boolean isDestroy) {
        this.isDestroy = isDestroy;
    }

    public interface Listener {
        void response(String url);

        void error(String error);
    }
}
