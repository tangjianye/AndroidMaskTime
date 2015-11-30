package com.peach.masktime.ui.layer;

import android.content.Context;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.ICycle;
import com.peach.masktime.ui.adapter.GuidePagerAdapter;
import com.peach.masktime.ui.view.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;


public class GuideLayer extends RelativeLayout implements ICycle {
    // private Button mBtnEntry;
    private LinearLayout mIndicator;
    private ScrollViewPager mViewPager;
    private List<View> mBannerViewList;

    private int mGuidePageResId[] = {R.drawable.guide_page_1,
            R.drawable.guide_page_2, R.drawable.guide_page_3};

    public GuideLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    public void updateData() {
        initData();
        initIndicator(getContext(), mIndicator);
        notifyAdapter();
    }

    private void notifyAdapter() {
        mViewPager.setAdapter(new GuidePagerAdapter(getContext(), mBannerViewList));
        mViewPager.setOnPageChangeListener(new GuidePageChangeListener());
        // mBtnEntry.setOnClickListener(mClickListener);
    }

    @Override
    public void refresh(Object obj) {
        updateData();
    }

    @Override
    public void resume() {

    }

    private void initData() {
        // 初始化引导页
        ImageView imageView;
        mBannerViewList = new ArrayList<>();
        for (int id : mGuidePageResId) {
            imageView = new ImageView(getContext());
            imageView.setImageResource(id);
            mBannerViewList.add(imageView);
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

    private void initView() {
        // mBtnEntry = (Button) findViewById(R.id.btn_entry);
        mIndicator = (LinearLayout) findViewById(R.id.ll_dot_group);
        mViewPager = (ScrollViewPager) findViewById(R.id.view_pager);
    }

//    private OnClickListener mClickListener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (R.id.btn_entry == v.getId()) {
//                WallpaperUtil.gotoActivity(getContext(),
//                        WallpaperActivity.class);
//                ((WelcomeAnimationActivity) getContext()).finish();
//                ((WelcomeAnimationActivity) getContext()).sendGuide(999);
//            }
//        }
//    };

    private class GuidePageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int pageIndex) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pageIndex) {
            // setButtonEnabled(pageIndex);
            setIndicatorEnabled(pageIndex);
        }
    }

//    private void setButtonEnabled(int pos) {
//        if (mGuidePageResId.length - 1 == pos) {
//            mBtnEntry.setVisibility(View.VISIBLE);
//        } else {
//            mBtnEntry.setVisibility(View.GONE);
//        }
//    }

    private void setIndicatorEnabled(int pos) {
        for (int i = 0; i < mGuidePageResId.length; i++) {
            mIndicator.getChildAt(i).setEnabled(false);
        }
        mIndicator.getChildAt(pos).setEnabled(true);
    }
}
