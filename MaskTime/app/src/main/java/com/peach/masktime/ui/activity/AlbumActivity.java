package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.response.AlbumItem;
import com.peach.masktime.module.net.response.AlbumSet;
import com.peach.masktime.ui.adapter.AlbumListAdapter;
import com.peach.masktime.ui.base.BaseTitleActivity;
import com.peach.masktime.ui.widget.xlistview.XListView;
import com.peach.masktime.ui.widget.xlistview.XListView.IXListViewListener;
import com.peach.masktime.utils.JsonUtils;
import com.peach.masktime.utils.LogUtils;
import com.peach.masktime.utils.TimeUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlbumActivity extends BaseTitleActivity implements IInit, AdapterView.OnItemClickListener, IXListViewListener {
    private static final String TAG = AlbumActivity.class.getSimpleName();
    private static final int FRIST_PAGE = 1;
    private static final boolean IS_INIT = true;

    private View mLyContentTips;
    private XListView mListView;
    private AlbumListAdapter mListAdapter;

    private int mPage = FRIST_PAGE;
    private ArrayList<AlbumItem> mAlbumDataSet;

    // 加载方式
    private enum Status {
        LordMore,
        Refresh,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDatas();
        initTitles();
        initViews();
        initEvents();

        request(IS_INIT, getUrl(FRIST_PAGE), Status.LordMore);
    }

    @Override
    protected void setContentLayer() {
        setContentView(R.layout.activity_album);
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
        mAlbumDataSet = new ArrayList<>();
    }

    @Override
    public void initTitles() {
        mTitleTips.setText(R.string.main_album);
    }

    private void refreshContentTips(boolean visible) {
        mLyContentTips.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void initViews() {
        mLyContentTips = findViewById(R.id.ly_tips_content);
        mLyContentTips.setVisibility(View.GONE);

        mListView = (XListView) findViewById(R.id.lv_content);
        mListView.setPullLoadEnable(true);
        mListAdapter = new AlbumListAdapter(this, mAlbumDataSet);
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void initEvents() {
        mListView.setOnItemClickListener(this);
        mListView.setXListViewListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlbumItem info = mAlbumDataSet.get(i);
        LogUtils.i(TAG, "onItemClick i = " + i + " ; info = " + info);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_URL, info.getUrl());
        openActivity(WebViewActivity.class, bundle);
    }

    @Override
    public void onLoadMore() {
        mPage++;
        LogUtils.i(TAG, "onLoadMore: mPage = " + mPage);
        request(!IS_INIT, getUrl(mPage), Status.LordMore);
    }

    @Override
    public void onRefresh() {
        request(!IS_INIT, getUrl(FRIST_PAGE), Status.Refresh);
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(TimeUtils.getCurrentTimeInString());
    }

    private void resetNetUI() {
        dismissLoadingDialog();
        onLoad();
    }

    private void refreshNetUI(boolean isInit) {
        if (isInit) {
            refreshContentTips(true);
        } else {
            showToast(R.string.default_no_more_date);
        }
    }

    private void refreshNetFail(boolean isInit) {
        resetNetUI();
        if (!isInit) {
            showToast(R.string.default_get_data_fail);
        }
    }

    private void request(final boolean isInit, final String url, final Status mode) {
        if (isInit) {
            showLoadingDialog();
        }

        StringRequest request = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.i(TAG, "response = " + response);
                        resetNetUI();

                        if (null != response) {
                            Type type = new TypeToken<AlbumSet>() {
                            }.getType();
                            AlbumSet set = JsonUtils.parseJson(response, type);
                            LogUtils.i(TAG, "set = " + set);
                            if (null != set && set.getRsm() != null && set.getRsm().size() > 0) {
                                response(mode, set.getRsm());
                            } else {
                                refreshNetUI(isInit);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                refreshNetFail(isInit);
                LogUtils.i(TAG, error.getMessage(), error);
            }
        });

        RequestQueue rq = VolleyManager.getInstance().getRequestQueue();
        request.setTag(url);
        request.setRetryPolicy(VolleyManager.getInstance().getRetryPolicy());
        rq.add(request);
        rq.start();
    }

    @NonNull
    private String getUrl(int page) {
        return API.getCategoryUrl(API.CATEGORY_CONTENT, page);
    }

    private void response(final Status mode, final ArrayList<AlbumItem> list) {
        if (Status.Refresh == mode) {
            mAlbumDataSet.clear();
        }

        // 创建一个假广告占位数据
        AlbumItem banner = new AlbumItem();
        mAlbumDataSet.add(banner);
        for (AlbumItem item : list) {
            mAlbumDataSet.add(item);
        }
        mListAdapter.notifyDataSetChanged();
    }
}

