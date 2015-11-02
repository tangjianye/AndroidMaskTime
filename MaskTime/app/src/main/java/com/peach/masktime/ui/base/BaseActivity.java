package com.peach.masktime.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.peach.masktime.R;
import com.peach.masktime.common.interfaces.IStatus;

/**
 * Created by tangjy on 2015/10/24.
 */
public class BaseActivity extends Activity implements IStatus {
    private static final int FLAG_NO = -100;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initTitles();
        initViews();
        initEvents();
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

    @Override
    public void initDatas() {

    }

    @Override
    public void initTitles() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToast(int resId) {
        showToast(getResources().getString(resId));
    }

    public void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null, FLAG_NO);
    }

    public void openActivity(Class<?> pclass, int flag) {
        openActivity(pclass, null, flag);
    }

    public void openActivity(Class<?> pclass, Bundle pBundle) {
        openActivity(pclass, pBundle, FLAG_NO);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle, int flag) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        if (flag != FLAG_NO) {
            intent.addFlags(flag);
        }
        startActivity(intent);
        // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle, int[] animRes) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
        if (animRes == null) {
            // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else {
            overridePendingTransition(animRes[0], animRes[1]);
        }
    }

    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    protected void openActivity(String pAction, Bundle pBundle) {
        openActivity(pAction, pBundle, null);
    }

    protected void openActivity(String pAction, Bundle pBundle, int[] animRes) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
        if (animRes == null) {
            // overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else {
            // overridePendingTransition(animRes[0], animRes[1]);
        }
    }

    public void openActivityForResult(Class<?> pClass, int requestCode) {
        openActivityForResult(pClass, requestCode, null);
    }

    /**
     * @param pClass
     * @param requestCode
     * @param pBundle
     * @see {@link android.app.Activity#startActivityForResult}
     */
    public void openActivityForResult(Class<?> pClass, int requestCode,
                                      Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivityForResult(intent, requestCode);
        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
