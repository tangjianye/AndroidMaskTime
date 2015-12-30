package com.peach.masktime.ui.layer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peach.masktime.R;
import com.peach.masktime.ui.beans.RecordBean;
import com.peach.masktime.utils.TimeUtils;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2015/11/20 0020.
 */
public class TimelineItemLayer extends LinearLayout {
    private View mLyTitle;
    private View mLyBody;

    private TextView mTitleData;
    private TextView mTitleNum;
    private TextView mBodyTitle;
    private TextView mBodyContent;

    public TimelineItemLayer(Context context) {
        super(context);
        // LayoutInflater.from(context).inflate(R.layout.layout_content_tips, this);
    }

    public TimelineItemLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLyTitle = findViewById(R.id.ly_title);
        mLyBody = findViewById(R.id.ly_body);

        mTitleData = (TextView) findViewById(R.id.txt_title_data);
        mTitleNum = (TextView) findViewById(R.id.txt_title_num);
        mBodyTitle = (TextView) findViewById(R.id.txt_body_title);
        mBodyContent = (TextView) findViewById(R.id.txt_body_content);
    }

    public void refresh(Object obj) {
        if (null == obj) {
            return;
        }
        RecordBean info = (RecordBean) obj;

        SimpleDateFormat formatter = TimeUtils.DATE_FORMAT_DATE;
        String dateString = formatter.format(info.getDate());

//        if (info.isFirstData()) {
//            mLyTitle.setVisibility(VISIBLE);
//            TimeUtils.getCurrentTimeInString();
//            mTitleData.setText(dateString);
//        } else {
//            mLyTitle.setVisibility(GONE);
//        }

        mTitleData.setText(dateString);
        mTitleNum.setText("2条");

        mBodyTitle.setText(info.getTitle());
        mBodyContent.setText(info.getContent());
    }
}
