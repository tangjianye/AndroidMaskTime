package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.module.net.API;
import com.peach.masktime.module.net.request.GsonRequest;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.module.net.response.AlbumItem;
import com.peach.masktime.module.net.response.MaskArraySet;
import com.peach.masktime.ui.adapter.AlbumListAdapter;
import com.peach.masktime.ui.base.BaseListActivity;
import com.peach.masktime.utils.LogUtils;

import java.util.ArrayList;

public class AlbumActivity extends BaseListActivity implements IInit, AdapterView.OnItemClickListener {
    private static final String TAG = AlbumActivity.class.getSimpleName();
    private static final int FRIST_PAGE = 1;
    private static final boolean IS_INIT = true;

    private AlbumListAdapter mListAdapter;

    private int mPage = FRIST_PAGE;
    private ArrayList<AlbumItem> mAlbumDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDatas();
        initTitles();
        initViews();
        initEvents();

        request(IS_INIT, getUrl(FRIST_PAGE), Status.PullUp);
    }

//    @Override
//    protected void setContentLayer() {
//        setContentView(R.layout.activity_album);
//    }

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

    @Override
    public void initViews() {
        mListAdapter = new AlbumListAdapter(this, mAlbumDataSet);
        mListView.setAdapter(mListAdapter);
    }

    @Override
    public void initEvents() {
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlbumItem info = mAlbumDataSet.get(i);
        LogUtils.i(TAG, "onItemClick i = " + i + " ; info = " + info);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.SPKey.BUNDLE_URL, info.getUrl());
        openActivity(WebViewActivity.class, bundle);
    }

    private void loadMore() {
        mPage++;
        LogUtils.i(TAG, "loadMore: mPage = " + mPage);
        request(!IS_INIT, getUrl(mPage), Status.PullUp);
    }


    private void refresh() {
        request(!IS_INIT, getUrl(FRIST_PAGE), Status.PullDown);
    }

    private void request(final boolean isInit, final String url, final Status mode) {
        GsonRequest.GsonRequestBuilder<MaskArraySet<AlbumItem>> builder = new GsonRequest.GsonRequestBuilder<>();
        builder.setMethod(Request.Method.GET)
                .setUrl(url)
                .setType(new TypeToken<MaskArraySet<AlbumItem>>() {
                }.getType())
                .setDialog(isInit ? creatLoadingDialog() : null)
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        refreshComplete();
                        if (!isInit) {
                            showToast(R.string.default_get_data_fail);
                        }
                    }
                })
                .setListener(new Response.Listener<MaskArraySet<AlbumItem>>() {
                    @Override
                    public void onResponse(MaskArraySet<AlbumItem> response) {
                        refreshComplete();
                        if (null != response && response.getRsm() != null && response.getRsm().size() > 0) {
                            response(mode, response.getRsm());
                        } else {
                            if (isInit) {
                                refreshContentTips(true);
                            } else {
                                showToast(R.string.default_no_more_date);
                            }
                        }
                    }
                });

        GsonRequest<MaskArraySet<AlbumItem>> request = builder.create();
        VolleyManager.getInstance().addToRequestQueue(request, url);
    }

    @NonNull
    private String getUrl(int page) {
        return API.getCategoryUrl(API.CATEGORY_CONTENT, page);
    }

    private void response(final Status mode, final ArrayList<AlbumItem> list) {
        if (Status.PullDown == mode) {
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

    @Override
    protected void pullDown() {
        refresh();
    }

    @Override
    protected void pullUp() {
        loadMore();
    }
}

