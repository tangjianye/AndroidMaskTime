package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.common.manager.VolleyManager;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.response.BannerItem;
import com.peach.masktime.module.net.response.BannerSet;
import com.peach.masktime.ui.adapter.AlbumListAdapter;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.utils.JsonUtils;
import com.peach.masktime.utils.LogUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlbumActivity extends BaseTitleActivity implements IInit, AdapterView.OnItemClickListener {
    private static final String TAG = AlbumActivity.class.getSimpleName();
    private static final int PAGE_BANNER = 1;
    private static final int CATEGORY_BANNER = 7;
    private static final int CATEGORY_CONTENT = 8;

    private ListView mListView;
    private AlbumListAdapter mListAdapter;

    private int mPage = 1;
    private ArrayList<BannerItem> mBannerDataSet;
    private ArrayList<BannerItem> mAlbumDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initTitles();
        initViews();
        initEvents();

        // request(API.SHOP_GET_GOODS, CATEGORY_BANNER, PAGE_BANNER);
        // request(API.SHOP_GET_GOODS, CATEGORY_CONTENT, mPage);

        showLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                request(API.SHOP_GET_GOODS, CATEGORY_CONTENT, mPage);
            }
        }, 5000);
    }

    @Override
    protected void setContentLayer() {
        setContentView(R.layout.activity_store);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void initDatas() {
        mBannerDataSet = new ArrayList<BannerItem>();
        mAlbumDataSet = new ArrayList<BannerItem>();
    }

    @Override
    public void initTitles() {
        mTitleTips.setText(R.string.main_album);
    }

    @Override
    public void initViews() {
        mListView = (ListView) findViewById(R.id.lv_content);
        mListAdapter = new AlbumListAdapter(this, mAlbumDataSet);
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void initEvents() {
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        LogUtils.i(TAG, "onItemClick");
    }

    private void request(String func, final int category, int page) {
        String url = API.getUrl(func) + "category_id=" + category + "&page=" + page;
        LogUtils.i(TAG, "request url = " + url);

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.i(TAG, "response = " + response);
                        dismissLoadingDialog();
                        if (null != response) {
                            Type type = new TypeToken<BannerSet>() {
                            }.getType();
                            BannerSet set = JsonUtils.parseJson(response, type);
                            LogUtils.i(TAG, "set = " + set);
                            response(category, set);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissLoadingDialog();
                LogUtils.i(TAG, error.getMessage(), error);
            }
        });

        RequestQueue rq = VolleyManager.getInstance(this).getRequestQueue();
        rq.add(stringRequest);
        stringRequest.setTag(func);
        rq.start();
    }

    private void response(final int category, final BannerSet set) {
        if (null != set) {
            if (CATEGORY_BANNER == category) {

            } else if (CATEGORY_CONTENT == category) {
                for (BannerItem item : set.getRsm()) {
                    mAlbumDataSet.add(item);
                }
                mListAdapter.notifyDataSetChanged();
            }
        }
    }
}

