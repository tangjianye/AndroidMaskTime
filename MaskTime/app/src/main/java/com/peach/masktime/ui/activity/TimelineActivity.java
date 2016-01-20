package com.peach.masktime.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.peach.masktime.R;
import com.peach.masktime.common.Constants;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.db.DBRecordHelper;
import com.peach.masktime.db.Record;
import com.peach.masktime.ui.adapter.TimelineGroupAdapter;
import com.peach.masktime.ui.base.BaseListActivity;
import com.peach.masktime.utils.LogUtils;
import com.peach.masktime.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends BaseListActivity implements IInit {
    private static final String TAG = TimelineActivity.class.getSimpleName();

    private TimelineGroupAdapter mListAdapter;
    private ArrayList<ArrayList<Record>> mGroupData;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i(TAG, "onCreate");
        initDatas();
        initTitles();
        initViews();
        initEvents();

        pullDown();
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
        mGroupData = new ArrayList<>();
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
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        mListAdapter = new TimelineGroupAdapter(this, mGroupData);
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
            refreshContentTips(list.size() > 0 ? false : true);
            refreshCompleteQuick();
        }
    }

    private void rebuildListData(List<Record> list, int pageSize) {
        groupBy(list);
        boolean noMoreData = filterGroupByPage(pageSize / Constants.PAGE_COUNT);
        mListAdapter.notifyDataSetChanged();

        // 页面计数加一
        mPage++;

        // 刷新UI
        refreshContentTips(list.size() > 0 ? false : true);
        refreshCompleteQuick();
        LogUtils.i(TAG, "rebuildListData pageSize = " + pageSize + " ;list.size() = " + list.size());
        if (noMoreData) {
            showToast(R.string.default_no_more_date);
        }
    }

    private void groupBy(final List<Record> list) {
        mGroupData.clear();
        ArrayList<Record> childData = null;
        String preDate = null;

        for (Record item : list) {
            String date = TimeUtils.getTime(item.getDate(), TimeUtils.DATE_FORMAT_DAY);
            if (null == preDate || !preDate.equals(date)) {
                childData = new ArrayList<>();
                mGroupData.add(childData);
            }
            childData.add(item);
            preDate = date;
        }
    }

    private boolean filterGroupByPage(int page) {
        if (1 == mGroupData.size() || mGroupData.size() <= page) {
            return true;
        }

        int min = Math.min(mGroupData.size(), page) - 1;
        int max = Math.max(mGroupData.size(), page) - 1;
        for (int i = max; i > min; i--) {
            mGroupData.remove(i);
        }
        return false;
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
