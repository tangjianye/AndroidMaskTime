package com.peach.masktime.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2015/11/3 0003.
 */
public class BannerPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<View> mList;

    public BannerPagerAdapter(Context context, List<View> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    public View instantiateItem(View arg0, final int arg1) {
        ((ViewPager) arg0).addView(mList.get(arg1));
        return mList.get(arg1);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
}

