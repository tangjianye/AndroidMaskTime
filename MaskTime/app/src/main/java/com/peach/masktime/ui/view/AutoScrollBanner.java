package com.peach.masktime.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.ICycle;
import com.peach.masktime.ui.adapter.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class AutoScrollBanner extends RelativeLayout implements ICycle {
    // private Button mBtnEntry;
    private LinearLayout mIndicator;
    private ScrollViewPager mViewPager;
    private List<View> mViews;

    private int mGuidePageResId[] = {R.drawable.guide_page_1,
            R.drawable.guide_page_2, R.drawable.guide_page_3};

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

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//    }

    @Override
    public void init() {

    }

    public void updateData() {
        initData();
        initIndicator(getContext(), mIndicator);
        notifyAdapter();
    }

    private void notifyAdapter() {
        mViewPager.setAdapter(new BannerPagerAdapter(getContext(), mViews));
        mViewPager.setOnPageChangeListener(new GuidePageChangeListener());
        // mBtnEntry.setOnClickListener(mClickListener);
    }

    @Override
    public void show(Object obj) {
        setVisibility(VISIBLE);
        updateData();
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

    private void initData() {
        // 初始化引导页
        ImageView imageView;
        mViews = new ArrayList<>();
        for (int id : mGuidePageResId) {
            imageView = new ImageView(getContext());
            imageView.setImageResource(id);
            mViews.add(imageView);
        }
    }

    private void initIndicator(final Context context, ViewGroup layout) {
        // 初始化引导点indicator
        LinearLayout.LayoutParams params;
        for (int i = 0; i < mGuidePageResId.length; i++) {
            View dot = new View(getContext());
            dot.setBackgroundResource(R.drawable.selector_guide_indicator);
            params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 8;
            params.rightMargin = 8;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            layout.addView(dot);
        }

        if (layout.getChildCount() != 0)
            layout.getChildAt(0).setEnabled(true);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.listitem_banner, this);
        mIndicator = (LinearLayout) findViewById(R.id.ll_dot_group);
        mViewPager = (ScrollViewPager) findViewById(R.id.view_pager);

        show(false);
    }

    private class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
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

    private void setIndicatorEnabled(int pos) {
        for (int i = 0; i < mGuidePageResId.length; i++) {
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
