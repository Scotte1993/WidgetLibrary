package com.junpu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * Base Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        this(context, R.style.Theme_Dialog_Base);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setContentView(layout());
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            initWindow(window, params);
            window.setAttributes(params);
        }
        initView();
    }

    /**
     * 初始化layout
     */
    protected abstract int layout();

    /**
     * 初始化window
     */
    protected void initWindow(Window window, WindowManager.LayoutParams params) {
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 设置Dialog宽度
     */
    public void setWidth(int width) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            window.setAttributes(lp);
        }
    }

    /**
     * 设置Dialog高度
     */
    public void setHeight(int height) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.height = height;
            window.setAttributes(lp);
        }
    }

}
