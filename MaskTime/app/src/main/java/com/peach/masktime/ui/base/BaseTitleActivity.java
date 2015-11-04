package com.peach.masktime.ui.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.peach.masktime.R;

public abstract class BaseTitleActivity extends BaseActivity {
    private static final String TAG = "BaseTitleActivity";
    protected ImageView mTitleBack;
    protected TextView mTitleTips;
    protected ImageView mTitleMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_community);
        setContentLayer();
        this.initDatas();
        this.initTitles();
        this.initViews();
        this.initEvents();
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


    private void initDatas() {

    }

    private void initTitles() {
        mTitleBack = (ImageView) findViewById(R.id.ic_header);
        mTitleTips = (TextView) findViewById(R.id.title_header);
        mTitleMore = (ImageView) findViewById(R.id.more_header);

        mTitleTips.setText(R.string.app_name);
        mTitleMore.setVisibility(View.GONE);

        mTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setResult(Activity.RESULT_CANCELED, null);
                finish();
            }
        });
    }

    private void initViews() {

    }

    private void initEvents() {

    }

    protected abstract void setContentLayer();

    // protected abstract void setTitleContext(String mTitleTips);
}
