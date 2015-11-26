package com.peach.masktime.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class ScrollViewPager extends ViewPager {

    private boolean mScrollable = true;

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScanScroll(boolean scrollable) {
        this.mScrollable = scrollable;
    }

    public boolean getScanScroll() {
        return mScrollable;
    }

    @Override
    public void scrollTo(int x, int y) {
        if (mScrollable) {
            super.scrollTo(x, y);
        }
    }
}