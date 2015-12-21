package com.peach.masktime.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.peach.masktime.R;

/**
 * 加载对话框
 *
 * @author Peng Yi
 */
public class SettingDialog extends Dialog {

    public SettingDialog(Context context) {
        super(context);
    }

    public SettingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public SettingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public SettingDialog(Context context, CharSequence text) {
        this(context, R.style.FullScreenDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init(text);
    }

    private void init(CharSequence text) {
        setContentView(R.layout.layout_dialog_setting);
    }
}
