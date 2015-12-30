package com.peach.masktime.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.ui.beans.RecordBean;
import com.peach.masktime.ui.layer.TimelineItemLayer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class TimelineAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<RecordBean> mListData;

    public TimelineAdapter(Context ctx, ArrayList<RecordBean> list) {
        this.mContext = ctx;
        this.mListData = list;
    }

    @Override
    public int getCount() {
        return (null == mListData) ? 0 : mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return (null == mListData) ? null : mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RecordBean info = mListData.get(position);

//        TimelineViewHolder holder;
//        if (convertView == null) {
//            holder = new TimelineViewHolder();
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_timeline, null);
//            // holder.image = (NetworkImageView) convertView.findViewById(R.id.nt_image);
//            holder.titleData = (TextView) convertView.findViewById(R.id.txt_title_data);
//            holder.titleNum = (TextView) convertView.findViewById(R.id.txt_title_num);
//            holder.bodyTitle = (TextView) convertView.findViewById(R.id.txt_body_title);
//            holder.bodyContent = (TextView) convertView.findViewById(R.id.txt_body_content);
//            convertView.setTag(holder);
//        } else {
//            holder = (TimelineViewHolder) convertView.getTag();
//        }
//
//        if (info.isFirstData()) {
//
//        } else {
//
//        }
//        // CommUtils.setImageUrl(mContext, holder.image, info.getCover());

        convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_timeline, null);
        ((TimelineItemLayer) convertView).refresh(info);
        return convertView;
    }

    static class TimelineViewHolder {
        // public NetworkImageView image;
        public TextView titleData;
        public TextView titleNum;
        public TextView bodyTitle;
        public TextView bodyContent;
    }
}
