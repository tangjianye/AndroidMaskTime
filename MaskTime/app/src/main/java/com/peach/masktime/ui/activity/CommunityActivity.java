package com.peach.masktime.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IInit;
import com.peach.masktime.ui.adapter.BaseFragmentPagerAdapter;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.ui.fragment.CommunityFragment;
import com.peach.masktime.ui.fragment.ReadFragment;
import com.peach.masktime.ui.view.CustomViewPager;

import java.util.ArrayList;

public class CommunityActivity extends BaseActivity implements IInit, View.OnClickListener {
    public static final String TAG = "CommunityActivity";

    /**
     * 标题选择
     */
    private TextView mCommunityView;
    private TextView mReadView;

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
        ImageView mTitleBack = (ImageView) findViewById(R.id.ic_header);
        ImageView mTitleMore = (ImageView) findViewById(R.id.more_header);
        //mTitleTips.setText(R.string.app_name);
        mTitleMore.setVisibility(View.GONE);

        mTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setResult(Activity.RESULT_CANCELED, null);
                finish();
            }
        });
    }

    @Override
    public void initViews() {
        // 标题
        mCommunityView = (TextView) findViewById(R.id.title_community);
        mReadView = (TextView) findViewById(R.id.title_read);

        mViewPager = (CustomViewPager) findViewById(R.id.view_pager);
    }

    @Override
    public void initEvents() {
        mCommunityView.setOnClickListener(this);
        mReadView.setOnClickListener(this);

        mPageAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager(), getFragmentList());
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOnPageChangeListener(mPageChangeListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_community:
                mViewPager.setCurrentItem(0);
                setIndicatorEnabled(0);
                break;
            case R.id.title_read:
                mViewPager.setCurrentItem(1);
                setIndicatorEnabled(1);
                break;
        }
    }

    private void setIndicatorEnabled(int position) {
        if (0 == position) {
            mCommunityView.setSelected(true);
            mReadView.setSelected(false);
        } else {
            mCommunityView.setSelected(false);
            mReadView.setSelected(true);
        }
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
            setIndicatorEnabled(arg0);
        }
    };
}
