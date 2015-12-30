package com.peach.masktime.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.db.DBManager;
import com.peach.masktime.db.Record;
import com.peach.masktime.ui.adapter.TimelineAdapter;
import com.peach.masktime.ui.base.BaseListActivity;
import com.peach.masktime.ui.beans.RecordBean;
import com.peach.masktime.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends BaseListActivity implements IInit {
    private static final String TAG = TimelineActivity.class.getSimpleName();

    private TimelineAdapter mListAdapter;

    private ArrayList<RecordBean> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initTitles();
        initViews();
        initEvents();

        refresh();
    }

//    @Override
//    protected void setContentLayer() {
//        setContentView(R.layout.activity_timeline);
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
        mListData = new ArrayList<>();
    }

    @Override
    public void initTitles() {
        mTitleTips.setText(R.string.main_record);

        mTitleMore.setVisibility(View.VISIBLE);
        mTitleMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityForResult(RecordingActivity.class, Constants.COMMON_REQUEST_CODE);
            }
        });
    }

    @Override
    public void initViews() {
        mListAdapter = new TimelineAdapter(this, mListData);
        mListView.setAdapter(mListAdapter);

        refreshContentTips(true);
    }

    @Override
    public void initEvents() {

    }

    @Override
    protected void pullDown() {

    }

    @Override
    protected void pullUp() {

    }

    private void refresh() {
        List<Record> list = DBManager.getInstance().getRecordDao().loadAll();
        LogUtils.i(TAG, "refresh");

        for (Record item : list) {
            RecordBean temp = new RecordBean(null, item.getTitle(), item.getContent(),
                    item.getPath01(), item.getPath02(), item.getPath03(), item.getDate(), false);
            LogUtils.i(TAG, "temp = " + temp);
            mListData.add(temp);
        }
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult requestCode = " + requestCode + " ;resultCode = " + resultCode);
        if (Activity.RESULT_OK == resultCode) {
            if (Constants.COMMON_REQUEST_CODE == requestCode) {
                LogUtils.i(TAG, "xxx onActivityResult requestCode = " + requestCode + " ;resultCode = " + resultCode);
                refresh();
            }
        }
    }
}
