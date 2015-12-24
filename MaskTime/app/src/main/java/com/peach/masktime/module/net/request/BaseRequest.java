package com.peach.masktime.module.net.request;

import android.app.Dialog;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.peach.masktime.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 所有联网请求的基类，返回string类型
 *
 * @param <T>
 */
public class BaseRequest<T> extends Request<T> {
    private static final String TAG = BaseRequest.class.getSimpleName();

    private int mMethod;
    private final Listener<T> mListener;
    private Type mType;
    private IParams mParamListener;
    // private static Gson sGson;
    // private Class<T> mClass;
    // private Map<String, String> mMap;

    public static class BaseRequestBuilder<T> {
        private int method = Method.GET;
        private String url;
        private Listener<T> listener;
        private ErrorListener errorListener;
        private Type type;
        private IParams paramListener;
        private Dialog dialog;
        // private Class<T> bclass;
        // private Map<String, String> map = null;

        public BaseRequestBuilder setMethod(int method) {
            this.method = method;
            return this;
        }

        public BaseRequestBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public BaseRequestBuilder setType(Type type) {
            this.type = type;
            return this;
        }

        public BaseRequestBuilder setParams(IParams listener) {
            this.paramListener = listener;
            return this;
        }

        public BaseRequestBuilder setErrorListener(final ErrorListener errorListener) {
            this.errorListener = deliverErrorListener(errorListener);
            return this;
        }

        public BaseRequestBuilder setListener(final Listener<T> listener) {
            this.listener = deliverResponse(listener);
            return this;
        }

        public BaseRequestBuilder setDialog(Dialog dialog) {
            this.dialog = dialog;
            return this;
        }

        public BaseRequest create() {
            if (null != dialog) {
                dialog.show();
            }
            return new BaseRequest<T>(method, url, type, listener, errorListener, paramListener);
        }

        /**
         * 统一的成功处理
         *
         * @param listener
         * @return
         */
        private Listener<T> deliverResponse(final Listener<T> listener) {
            return new Listener<T>() {
                @Override
                public void onResponse(T response) {
                    LogUtils.i(TAG, "onResponse = " + ((null != response) ? response.toString() : null));
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

    public BaseRequest(int method, String url, Type type, Listener<T> listener,
                       ErrorListener errorListener, IParams params) {
        super(method, url, errorListener);
        // setRetryPolicy(new DefaultRetryPolicy(
        //        Constants.REQUEST_TIMEOUT_MS, Constants.REQUEST_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // sGson = new Gson();
        mMethod = method;
        mType = type;
        mListener = listener;
        mParamListener = params;
        LogUtils.i(TAG, "url =" + url);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (null != mParamListener) {
            Map<String, String> map = mParamListener.getParams();
            LogUtils.i(TAG, "map = " + map.toString());
            return map;
        } else {
            LogUtils.i(TAG, "map = null");
            return null;
        }
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success((T) parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    public interface IParams {
        Map<String, String> getParams();
    }
}
