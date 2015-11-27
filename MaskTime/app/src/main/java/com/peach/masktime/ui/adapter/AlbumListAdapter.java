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
import com.peach.masktime.ui.layer.BannerLayer;
import com.peach.masktime.ui.view.AutoScrollBanner;
import com.peach.masktime.utils.CommUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/12 0012.
 */
public class AlbumListAdapter extends BaseAdapter {
    private Context mContext;
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_ALBUM = 1;
    private static final int TYPE_COUNT = 2;

    private ArrayList<AlbumItem> mAlbumList;

    public AlbumListAdapter(Context ctx, ArrayList<AlbumItem> list) {
        this.mContext = ctx;
        this.mAlbumList = list;
    }

    @Override
    public int getCount() {
        return (null == mAlbumList) ? 0 : mAlbumList.size();
    }

    @Override
    public Object getItem(int position) {
        return (null == mAlbumList) ? null : mAlbumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return TYPE_BANNER;
        } else {
            return TYPE_ALBUM;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AlbumItem info = mAlbumList.get(position);
        int type = getItemViewType(position);

        switch (type) {
            case TYPE_BANNER: {
//                BannerViewHolder holder;
//                if (convertView == null) {
//                    holder = new BannerViewHolder();
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_banner, null);
//                    holder.banner = (AutoScrollBanner) convertView.findViewById(R.id.bv_auto);
//                    convertView.setTag(holder);
//                } else {
//                    holder = (BannerViewHolder) convertView.getTag();
//                }
//                holder.banner.show(getAlbumItems(info));
                convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_banner, null);
                ((BannerLayer) convertView).show(null);
                break;
            }

            case TYPE_ALBUM: {
                AlbumViewHolder holder;
                if (convertView == null) {
                    holder = new AlbumViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_album, null);
                    holder.image = (NetworkImageView) convertView.findViewById(R.id.nt_image);
                    holder.title = (TextView) convertView.findViewById(R.id.txt_title);
                    convertView.setTag(holder);
                } else {
                    holder = (AlbumViewHolder) convertView.getTag();
                }

                holder.title.setText(info.getTitle());
                CommUtils.setImageUrl(mContext, holder.image, info.getCover());
                break;
            }

            default:
                break;
        }
        return convertView;
    }

    static class BannerViewHolder {
        public AutoScrollBanner banner;
    }

    static class AlbumViewHolder {
        public NetworkImageView image;
        public TextView title;
    }
}
