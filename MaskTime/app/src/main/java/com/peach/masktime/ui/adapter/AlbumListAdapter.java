package com.peach.masktime.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.peach.masktime.R;
import com.peach.masktime.module.net.response.AlbumItem;
import com.peach.masktime.utils.ComUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class AlbumListAdapter extends BaseAdapter {
    private Context mContext;
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ALBUM = 1;
    private static final int TYPE_COUNT = 2;

    private ArrayList<AlbumItem> mList;

    public AlbumListAdapter(Context ctx, ArrayList<AlbumItem> list) {
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
    public int getItemViewType(int position) {
        if ((null == mList)) {
            return super.getItemViewType(position);
        } else {
            if (0 == position) {
                return TYPE_BANNER;
            } else {
                return TYPE_ALBUM;
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        if ((null == mList)) {
            return super.getViewTypeCount();
        } else {
            return TYPE_COUNT;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AlbumItem info = mList.get(position);
        ViewHolder holder;
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_BANNER:
                // ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_album, null);
                    holder.image = (NetworkImageView) convertView.findViewById(R.id.nt_image);
                    holder.title = (TextView) convertView.findViewById(R.id.txt_title);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.title.setText("我是第一项");
                ComUtils.setImageUrl(mContext, holder.image, info.getCover());
                break;
            case TYPE_ALBUM:
                // ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_album, null);
                    holder.image = (NetworkImageView) convertView.findViewById(R.id.nt_image);
                    holder.title = (TextView) convertView.findViewById(R.id.txt_title);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.title.setText(info.getTitle());
                ComUtils.setImageUrl(mContext, holder.image, info.getCover());
                break;
            default:
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        public NetworkImageView image;
        public TextView title;
    }
}
