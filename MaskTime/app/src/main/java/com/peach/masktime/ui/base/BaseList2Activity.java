package com.peach.masktime.ui.base;

import android.os.Bundle;

import com.peach.masktime.R;
import com.peach.masktime.ui.fragment.ReadFragment;

/**
 * 初始化列表界面 ，它用于显示列表、标题等页面。
 *
 * @author tangjy
 */
public class BaseList2Activity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReadFragment fragment = new ReadFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ly_content, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setContentLayer() {
        setContentView(R.layout.activity_base_list2);
    }
}
