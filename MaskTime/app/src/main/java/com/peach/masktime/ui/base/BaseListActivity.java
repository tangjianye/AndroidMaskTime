package com.peach.masktime.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.peach.masktime.R;
import com.peach.masktime.utils.TimeUtils;


/**
 * 初始化列表界面 ，它用于显示列表、标题等页面。
 *
 * @author tangjy
 */
public abstract class BaseListActivity extends BaseTitleActivity {
    private static final String TAG = BaseListActivity.class.getSimpleName();
    /**
     * 延迟100毫秒刷新列表
     */
    private static final long DELAY = 100;
    /**
     * 没有数据的显示
     */
    private View mLyContentTips;
    /**
     * 列表
     */
    protected PullToRefreshListView mListView;

    // 加载方式
    public enum Status {
        // 下拉刷新
        PullDown,
        // 上拉加载更多
        PullUp,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setContentLayer() {
        setContentView(R.layout.activity_base_list);
    }

    /**
     * 是否显示没有数据
     *
     * @param visible true显示 false不显示
     */
    protected void refreshContentTips(boolean visible) {
        mLyContentTips.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 重新设置xlistview的状态
     */
    protected void refreshComplete() {
        // LogUtils.i(TAG, "refreshComplete");
        mListView.onRefreshComplete();
    }

    /**
     * 快速刷新回调
     */
    protected void refreshCompleteQuick() {
        // LogUtils.i(TAG, "refreshComplete2");
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.onRefreshComplete();
            }
        }, DELAY);
    }

    /**
     * 设置数据适配器
     *
     * @param adapter
     */
    protected void setListAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    private void initViews() {
        mLyContentTips = findViewById(R.id.ly_tips_content);
        mLyContentTips.setVisibility(View.GONE);

        mListView = (PullToRefreshListView) findViewById(R.id.lv_content);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);

        // Set a listener to be invoked when the list should be refreshed.
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                mListView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_MM));

                pullDown();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                // String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                //         DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                mListView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_MM));

                pullUp();
            }
        });

        // Add an end-of-list listener
        mListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {

            }
        });
    }

    private void initEvents() {

    }

    protected abstract void pullDown();

    protected abstract void pullUp();

    //protected abstract void setListAdapter(ListAdapter adapter);
}
