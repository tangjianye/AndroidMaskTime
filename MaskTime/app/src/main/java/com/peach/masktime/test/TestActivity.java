package com.peach.masktime.test;

import android.os.Bundle;
import android.util.Log;

import com.peach.masktime.R;
import com.peach.masktime.ui.base.BaseActivity;
import com.peach.masktime.ui.view.CircleSeekBar;
import com.peach.masktime.ui.view.CircleSeekBar.OnSeekBarChangeListener;

/**
 * Created by Administrator on 2015/12/16 0016.
 */
public class TestActivity extends BaseActivity {
    private static final String TAG = TestActivity.class.getSimpleName();
    private final boolean DEBUG = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        CircleSeekBar circleSeekBar = (CircleSeekBar) findViewById(R.id.circle_seekbar);
        // circleSeekBar.setProgressMax(100);
        circleSeekBar.setProgress(10);
        circleSeekBar.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());

//        CircleSeekBar circleSeekBar2 = new CircleSeekBar(this);
//        circleSeekBar2.setProgress(10);
//        circleSeekBar2.setProgressFrontColor(Color.RED);
//        circleSeekBar2.setProgressThumb(R.drawable.thumb);
//        circleSeekBar2.setOnSeekBarChangeListener(new CircleSeekBarOnChangeListener());
//        RelativeLayout.LayoutParams circleSeekBarParams = new RelativeLayout.LayoutParams(200, 200);
//        circleSeekBarParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        mainLayout.addView(circleSeekBar2, circleSeekBarParams);
    }

    private class CircleSeekBarOnChangeListener implements OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(int progress) {
            if (DEBUG) Log.d(TAG, "onProgressChanged progress = " + progress);
        }

        @Override
        public void onStartTrackingTouch() {
            if (DEBUG) Log.d(TAG, "onStartTrackingTouch");
        }

        @Override
        public void onStopTrackingTouch() {
            if (DEBUG) Log.d(TAG, "onStopTrackingTouch");
        }

    }
}
