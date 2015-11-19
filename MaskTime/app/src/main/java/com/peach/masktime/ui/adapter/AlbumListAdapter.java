package com.peach.masktime.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.peach.masktime.R;
import com.peach.masktime.module.net.response.BannerItem;
import com.peach.masktime.utils.ComUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class AlbumListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BannerItem> mList;

    public AlbumListAdapter(Context ctx, ArrayList<BannerItem> list) {
        this.mContext = ctx;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return (null == mList) ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return (null == mList) ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final BannerItem info = mList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_album_content, null);
            holder.image = (NetworkImageView) convertView.findViewById(R.id.nt_image);
            holder.title = (TextView) convertView.findViewById(R.id.txt_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(info.getTitle());
        ComUtils.setImageUrl(mContext, holder.image, info.getUrl());
        return convertView;
    }

    static class ViewHolder {
        public NetworkImageView image;
        public TextView title;
    }
}
