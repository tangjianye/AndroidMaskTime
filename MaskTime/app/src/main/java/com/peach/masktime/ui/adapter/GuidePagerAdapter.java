package com.peach.masktime.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.peach.masktime.ui.activity.MainActivity;
import com.peach.masktime.ui.activity.WelcomeActivity;
import com.peach.masktime.ui.base.BaseActivity;

import java.util.List;

/**
 * Created by Administrator on 2015/11/3 0003.
 */
public class GuidePagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<ImageView> mList;

    public GuidePagerAdapter(Context context, List<ImageView> list) {
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

    public ImageView instantiateItem(View arg0, final int arg1) {
        ((ViewPager) arg0).addView(mList.get(arg1));
        if (arg1 == mList.size() - 1) {
            mList.get(arg1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO Auto-generated method stub
                    ((BaseActivity) mContext).openActivity(MainActivity.class);
                    ((WelcomeActivity) mContext).finish();
                }
            });
        }
        return mList.get(arg1);
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }
}

