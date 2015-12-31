package com.peach.masktime.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.db.DBRecordHelper;
import com.peach.masktime.db.Record;
import com.peach.masktime.ui.adapter.TimelineAdapter;
import com.peach.masktime.ui.base.BaseListActivity;
import com.peach.masktime.ui.beans.RecordBean;
import com.peach.masktime.utils.LogUtils;
import com.peach.masktime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends BaseListActivity implements IInit {
    private static final String TAG = TimelineActivity.class.getSimpleName();

    private TimelineAdapter mListAdapter;

    private ArrayList<RecordBean> mListData;

    private int mPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
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
    }

    @Override
    public void initEvents() {

    }

    @Override
    protected void pullDown() {
        mPage = 1;
        refresh();
    }

    @Override
    protected void pullUp() {
        refresh();
    }

    private void refresh() {
        LogUtils.i(TAG, "refresh");
        List<Record> list = DBRecordHelper.getInstance().loadAllByDate();

        if (null != list && list.size() > 0) {
            rebuildListData(list, mPage * Constants.PAGE_COUNT);
        } else {
            refreshContentTips(mListData.size() > 0 ? false : true);
            refreshCompleteQuick();
        }
    }

    private void rebuildListData(List<Record> list, int pageSize) {
        int count = 0;
        String day = null;
        int rebuildSize = (list.size() > pageSize) ? pageSize : list.size();

        mListData.clear();
        for (int i = 0; i < rebuildSize; i++) {
            boolean isDay = false;
            Record item = list.get(i);

            String tmpDay = TimeUtils.getTime(item.getDate(), TimeUtils.DATE_FORMAT_DAY);
            if (null == day || !day.equals(tmpDay)) {
                isDay = true;
                count = 0;
            }
            count++;
            day = tmpDay;

            RecordBean temp = new RecordBean(null, item.getTitle(), item.getContent(),
                    item.getPath01(), item.getPath02(), item.getPath03(), item.getDate(), count, isDay);
            // LogUtils.i(TAG, "temp = " + temp);
            mListData.add(temp);
            mListAdapter.notifyDataSetChanged();

            // 页面计数加一
            mPage++;
        }

        refreshContentTips(mListData.size() > 0 ? false : true);
        refreshCompleteQuick();
        if (list.size() <= pageSize) {
            showToast(R.string.default_no_more_date);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i(TAG, "onActivityResult requestCode = " + requestCode + " ;resultCode = " + resultCode);
        if (Activity.RESULT_OK == resultCode) {
            if (Constants.COMMON_REQUEST_CODE == requestCode) {
                refresh();
            }
        }
    }
}
