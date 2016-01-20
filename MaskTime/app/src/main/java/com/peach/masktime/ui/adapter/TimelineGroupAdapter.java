package com.peach.masktime.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.db.Record;
import com.peach.masktime.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class TimelineGroupAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ArrayList<Record>> mGroupData;

    public TimelineGroupAdapter(Context ctx, ArrayList<ArrayList<Record>> groupData) {
        this.mContext = ctx;
        this.mGroupData = groupData;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (ArrayList<Record> list : mGroupData) {
            for (Record item : list) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        int count = 0;
        for (ArrayList<Record> list : mGroupData) {
            for (Record item : list) {
                if (count == position) {
                    return item;
                }
                count++;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Record item = (Record) getItem(position);

        TimelineViewHolder holder;
        if (convertView == null) {
            holder = new TimelineViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_timeline, null);

            holder.lyGroup = convertView.findViewById(R.id.ly_title);
            holder.lyChild = convertView.findViewById(R.id.ly_body);
            holder.lyLine = convertView.findViewById(R.id.ly_line);
            // holder.image = (NetworkImageView) convertView.findViewById(R.id.nt_image);
            holder.titleDate = (TextView) convertView.findViewById(R.id.txt_title_date);
            holder.titleNum = (TextView) convertView.findViewById(R.id.txt_title_num);
            holder.bodyTitle = (TextView) convertView.findViewById(R.id.txt_body_title);
            holder.bodyDate = (TextView) convertView.findViewById(R.id.txt_body_content);
            convertView.setTag(holder);
        } else {
            holder = (TimelineViewHolder) convertView.getTag();
        }

        if (isGroupFirst(position)) {
            holder.lyGroup.setVisibility(View.VISIBLE);
            holder.titleDate.setText(TimeUtils.getTime(item.getDate(), TimeUtils.DATE_FORMAT_DAY));
            holder.titleNum.setText(String.format(mContext.getString(R.string.label_group_num), getGroupSize(position)));
        } else {
            holder.lyGroup.setVisibility(View.GONE);
        }

        holder.lyLine.setVisibility(isGroupLast(position) ? View.INVISIBLE : View.VISIBLE);
        holder.bodyTitle.setText(item.getTitle());
        holder.bodyDate.setText(TimeUtils.getTime(item.getDate(), TimeUtils.DATE_FORMAT_HH_MM_SS));
        // CommUtils.setImageUrl(mContext, holder.image, info.getCover());
        return convertView;
    }

    public boolean isGroupFirst(int position) {
        int count = 0;
        for (int i = 0; i < mGroupData.size(); i++) {
            ArrayList<Record> list = mGroupData.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (count == position) {
                    if (0 == j) {
                        return true;
                    }
                }
                count++;
            }
        }
        return false;
    }

    public boolean isGroupLast(int position) {
        int count = 0;
        for (int i = 0; i < mGroupData.size(); i++) {
            ArrayList<Record> list = mGroupData.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (count == position) {
                    if (list.size() - 1 == j) {
                        return true;
                    }
                }
                count++;
            }
        }
        return false;
    }

    public int getGroupSize(int position) {
        int count = 0;
        for (int i = 0; i < mGroupData.size(); i++) {
            ArrayList<Record> list = mGroupData.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (count == position) {
                    return list.size();
                }
                count++;
            }
        }
        return 0;
    }

    static class TimelineViewHolder {
        public View lyGroup;
        public View lyChild;
        public View lyLine;

        // public NetworkImageView image;
        public TextView titleDate;
        public TextView titleNum;
        public TextView bodyTitle;
        public TextView bodyDate;
    }
}
