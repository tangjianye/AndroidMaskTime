package com.peach.masktime.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.peach.masktime.R;
import com.peach.masktime.utils.TimeUtils;

/**
 * Created by Administrator on 2015/11/4 0004.
 */
public abstract class BaseListFragment extends BaseFragment {
    private static final String TAG = BaseListFragment.class.getSimpleName();
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
    /**
     * 加载页面数 从1开始
     */
    protected int mPage = 1;

    /**
     * 加载方式
     */
    public enum Status {
        // 下拉刷新
        PullDown,
        // 上拉加载更多
        PullUp,
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        mListView.onRefreshComplete();
    }

    /**
     * 快速刷新回调
     */
    protected void refreshCompleteQuick() {
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

    private void initViews(View view) {
        mLyContentTips = view.findViewById(R.id.ly_tips_content);
        mLyContentTips.setVisibility(View.GONE);

        mListView = (PullToRefreshListView) view.findViewById(R.id.lv_content);
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

    protected abstract void pullDown();

    protected abstract void pullUp();
}
