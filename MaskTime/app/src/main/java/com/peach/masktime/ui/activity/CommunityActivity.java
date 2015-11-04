package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.ui.adapter.BaseFragmentPagerAdapter;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.ui.fragment.CommunityFragment;
import com.peach.masktime.ui.fragment.ReadFragment;
import com.peach.masktime.ui.view.CustomViewPager;

import java.util.ArrayList;

public class CommunityActivity extends BaseActivity implements IInit {
    public static final String TAG = "CommunityActivity";

    private CustomViewPager mViewPager;
    private BaseFragmentPagerAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        initDatas();
        initTitles();
        initViews();
        initEvents();
    }

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

    }

    @Override
    public void initTitles() {
        mViewPager = (CustomViewPager) findViewById(R.id.view_pager);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {
        mPageAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), getFragmentList());
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOnPageChangeListener(mPageChangeListener);
    }

    private ArrayList<Fragment> getFragmentList() {
        ArrayList<Fragment> list = new ArrayList<Fragment>();
        list.add(new CommunityFragment());
        list.add(new ReadFragment());
        return list;
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            // setIndicatorEnabled(arg0);
        }
    };
}
