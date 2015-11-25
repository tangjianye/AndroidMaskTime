package com.peach.masktime.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.ICycle;
import com.peach.masktime.module.net.response.AlbumItem;
import com.peach.masktime.ui.adapter.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class AutoScrollBanner extends RelativeLayout implements ICycle {
    private static final String TAG = AutoScrollBanner.class.getSimpleName();
    private int mCount;

    private LinearLayout mIndicator;
    private ScrollViewPager mViewPager;
    private List<View> mViews;

    private BannerPagerAdapter mBannerAdapter;
    private ArrayList<AlbumItem> mList;

    public AutoScrollBanner(Context context) {
        super(context);
        initView(context);
    }

    public AutoScrollBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AutoScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void init() {

    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.auto_scroll_banner, this);
        mIndicator = (LinearLayout) findViewById(R.id.ll_dot_group);
        mViewPager = (ScrollViewPager) findViewById(R.id.view_pager);
        mViewPager.setOnPageChangeListener(new PageChangeListener());

        mViews = new ArrayList<>();
        mBannerAdapter = new BannerPagerAdapter(getContext(), mViews);
        mViewPager.setAdapter(mBannerAdapter);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int pageIndex) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pageIndex) {
            setIndicatorEnabled(pageIndex);
        }
    }

    @Override
    public void show(Object obj) {
        setVisibility(VISIBLE);
        ArrayList<AlbumItem> list = (ArrayList<AlbumItem>) obj;
        if (null != list && list.size() > 0) {
            updateUI(list);
        }
    }

    private void updateUI(ArrayList<AlbumItem> list) {
        if (null == mList) {
            mList = list;
        } else {
            if (mList.size() == list.size()) {
                return;
            } else {
                mList = list;
            }
        }

        mCount = mList.size();
        initBanner(mCount);
        initIndicator(mCount);
        mBannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void hide(Object obj) {
        setVisibility(GONE);
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    private void initBanner(int count) {
        // 初始化引导页
        ImageView imageView;
        mViews.clear();
        for (int i = 0; i < count; i++) {
            imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.guide_page_1);
            mViews.add(imageView);
        }
    }

    private void initIndicator(int count) {
        // 初始化引导点indicator
        LinearLayout.LayoutParams params;
        for (int i = 0; i < count; i++) {
            View dot = new View(getContext());
            dot.setBackgroundResource(R.drawable.selector_guide_indicator);
            params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 5;
            params.rightMargin = 5;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            mIndicator.addView(dot);
        }

        if (mIndicator.getChildCount() != 0)
            mIndicator.getChildAt(0).setEnabled(true);
    }

    private void setIndicatorEnabled(int pos) {
        for (int i = 0; i < mCount; i++) {
            mIndicator.getChildAt(i).setEnabled(false);
        }
        mIndicator.getChildAt(pos).setEnabled(true);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void resume() {

    }
}
