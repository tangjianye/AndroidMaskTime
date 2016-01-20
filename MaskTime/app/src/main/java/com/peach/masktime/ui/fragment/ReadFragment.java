package com.peach.masktime.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.peach.masktime.R;
import com.peach.masktime.module.net.VolleyManager;
import com.peach.masktime.module.net.request.GsonRequest;
import com.peach.masktime.module.net.response.AlbumItem;
import com.peach.masktime.module.net.response.MaskArraySet;
import com.peach.masktime.module.net.response.ReadItem;
import com.peach.masktime.ui.base.BaseListFragment;
import com.peach.masktime.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4 0004.
 */
public class ReadFragment extends BaseListFragment {
    private static final String TAG = ReadFragment.class.getSimpleName();
    private List<ReadItem> mListData;
    private ItemAdapter mAdapter;

    public ReadFragment() {
        super();
        mListData = new ArrayList<>();

        ReadItem item = null;
        for (int i = 0; i < 35; i++) {
            item = new ReadItem();
            mListData.add(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        refreshContentTips(false);
        mAdapter = new ItemAdapter(getActivity(), mListData);
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void pullDown() {
        refresh();
    }

    @Override
    protected void pullUp() {
        loadMore();
    }

    private static class ItemAdapter extends ArrayAdapter<ReadItem> {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context, List<ReadItem> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public ItemAdapter(Context context, int resource, List<ReadItem> objects) {
            super(context, resource, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listitem_read, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            return convertView;
        }
    }

    private static class ViewHolder {
        public ViewHolder(View parent) {
        }
    }


    private void loadMore() {
        LogUtils.i(TAG, "loadMore: mPage = " + mPage);
        request(null, Status.PullUp);
    }


    private void refresh() {
        mPage = 1;
        request(null, Status.PullDown);
    }

    private void request(final String url, final Status mode) {
        getBaseActivity().showLoadingDialog(getString(R.string.default_loading_tips));

        GsonRequest<MaskArraySet<AlbumItem>> request = new GsonRequest<MaskArraySet<AlbumItem>>(
                Request.Method.GET,
                url,
                new TypeToken<MaskArraySet<AlbumItem>>() {
                }.getType(),
                new Response.Listener<MaskArraySet<AlbumItem>>() {
                    @Override
                    public void onResponse(MaskArraySet<AlbumItem> response) {
                        // dismissLoadingDialog();
                        refreshComplete();
                        if (null != response && response.getRsm() != null && response.getRsm().size() > 0) {
                            mPage++;
                            //response(mode, response.getRsm());
                        } else {
                            getBaseActivity().showToast(R.string.default_no_more_date);
                        }
//                        refreshContentTips(mAlbumDataSet.size() > 0 ? false : true);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getBaseActivity().dismissLoadingDialog();
                        refreshComplete();
                        getBaseActivity().showToast(R.string.default_get_data_fail);
                    }
                }
        );

        VolleyManager.getInstance().addToRequestQueue(request, url);
    }
}
