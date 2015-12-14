/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.peach.masktime.module.net;

import android.app.Dialog;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.peach.masktime.utils.LogUtils;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends Request<T> {
    private static final String TAG = GsonRequest.class.getSimpleName();

    private final Listener<T> mListener;
    private static Gson sGson;
    private Class<T> mClass;

    public static class GsonRequestBuilder<T> {
        private int method;
        private String url;
        private Listener<T> listener;
        private ErrorListener errorListener;
        private Class<T> bclass;
        private Dialog dialog;

        public GsonRequestBuilder setMethod(int method) {
            this.method = method;
            return this;
        }

        public GsonRequestBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public GsonRequestBuilder setBclass(Class<T> bclass) {
            this.bclass = bclass;
            return this;
        }

        public GsonRequestBuilder setErrorListener(final Response.ErrorListener errorListener) {
            this.errorListener = deliverErrorListener(errorListener);
            return this;
        }

        public GsonRequestBuilder setListener(final Response.Listener<T> listener) {
            this.listener = deliverResponse(listener);
            return this;
        }

        public GsonRequestBuilder setDialog(Dialog dialog) {
            this.dialog = dialog;
            return this;
        }

        public GsonRequest build() {
            if (null != dialog) {
                dialog.show();
            }
            return new GsonRequest<T>(method, url, bclass, listener, errorListener);
        }

        /**
         * 统一的成功处理
         *
         * @param listener
         * @return
         */
        private Listener<T> deliverResponse(final Listener<T> listener) {
            return new Response.Listener<T>() {
                @Override
                public void onResponse(T response) {
                    LogUtils.i(TAG, "onResponse = " + response.toString());
                    dismissDialog();
                    listener.onResponse(response);
                }
            };
        }

        /**
         * 统一的失败处理
         *
         * @param errorListener
         * @return
         */
        private ErrorListener deliverErrorListener(final ErrorListener errorListener) {
            return new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    LogUtils.i(TAG, error.getMessage(), error);
                    dismissDialog();
                    errorListener.onErrorResponse(error);
                }
            };
        }

        private void dismissDialog() {
            if (null != dialog && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener,
                       ErrorListener errorListener) {
        super(method, url, errorListener);
//        setRetryPolicy(new DefaultRetryPolicy(
//                Constants.REQUEST_TIMEOUT_MS, Constants.REQUEST_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        sGson = new Gson();
        mClass = clazz;
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Listener<T> listener,
                       ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            LogUtils.i(TAG, "parseNetworkResponse jsonString = " + jsonString);
            // Type type = new TypeToken<T>() {}.getType();
            return Response.success(sGson.fromJson(jsonString, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
