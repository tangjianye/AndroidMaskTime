package com.peach.masktime.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mList;
    private ArrayList<String> mTitles;

    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mList.get(arg0);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (null == mTitles) ? null : mTitles.get(position);
    }
}
