package com.peach.masktime.module.thirdparty;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.utils.LogUtils;

/**
 * Created by Administrator on 2015/12/8 0008.
 */
public class Douban {
    private static final String TAG = Douban.class.getSimpleName();
    private static final String DOUBAN_CHANNELS = "http://douban.fm/j/explore/hot_channels";
    private static final String DOUBAN_PLAY_LIST = "http://douban.fm/j/mine/playlist";

    private static Context sCtx;
    private static Douban sINSTANTCE;

    private Douban(Context context) {
        sCtx = context;
    }

    public static synchronized Douban getInstance(Context context) {
        if (sINSTANTCE == null) {
            sINSTANTCE = new Douban(context);
        }
        return sINSTANTCE;
    }

    private String getListUrl(int channel, String doubanPlayList) {
        String url = doubanPlayList + "?type=n&sid=&pt=0.0&from=mainsite&r=c7bc353d05&channel=" + channel;
        LogUtils.i(TAG, "getPlaylistUrl url = " + url);
        return url;
    }

    private void request(String url) {
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.i(TAG, "response = " + response);

                        if (null != response) {
//                            Type type = new TypeToken<AlbumSet>() {
//                            }.getType();
//                            AlbumSet set = JsonUtils.parseJson(response, type);
//                            LogUtils.i(TAG, "set = " + set);
//                            if (null != set && set.getRsm() != null && set.getRsm().size() > 0) {
//                                response(mode, set.getRsm());
//                            } else {
//                                refreshNetUI(isInit);
//                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i(TAG, error.getMessage(), error);
            }
        });

        RequestQueue rq = VolleyManager.getInstance(sCtx).getRequestQueue();
        rq.add(stringRequest);
        stringRequest.setTag(url);
        rq.start();
    }
}
