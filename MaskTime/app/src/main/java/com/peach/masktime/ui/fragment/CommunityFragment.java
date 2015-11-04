package com.peach.masktime.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.ui.base.BaseFragment;

/**
 * Created by Administrator on 2015/11/4 0004.
 */
public class CommunityFragment extends BaseFragment {

    public CommunityFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ((TextView) view.findViewById(R.id.txt_test)).setText("This is CommunityFragment!");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
