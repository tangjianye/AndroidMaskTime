package com.peach.masktime.module.net.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Administrator on 2015/11/19 0019.
 */
public class BaseRequest extends StringRequest {

    public BaseRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public BaseRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}
