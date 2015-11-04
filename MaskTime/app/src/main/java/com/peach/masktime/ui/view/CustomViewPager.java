package com.peach.masktime.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class CustomViewPager extends ViewPager {

    private boolean mScrollable = true;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean scrollable) {
        this.mScrollable = scrollable;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mScrollable) {
            super.scrollTo(x, y);
        }
    }
}