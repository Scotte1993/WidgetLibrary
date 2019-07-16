package com.junpu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * loading dialog
 */
public class LoadingDialog extends Dialog {

    private TextView mMessageView;

    public LoadingDialog(Context context) {
        this(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    protected void init() {
        setContentView(R.layout.dialog_loading);
        mMessageView = (TextView) findViewById(R.id.notice_content);
        mMessageView.setText("加载中...");
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0;
        window.setAttributes(lp);
    }

    public void setMessage(String msg) {
        mMessageView.setText(msg);
    }

    public void setMessage(int resId) {
        setMessage(getContext().getResources().getString(resId));
    }

    public static class Builder {
        private Context mContext;
        private String mMessage;
        private boolean mCancelable;
        private boolean mCancelOnTouchOutside;

        public Builder(Context context) {
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder setMessage(String msg) {
            mMessage = msg;
            return this;
        }

        public Builder setCancelable(boolean flag) {
            mCancelable = flag;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancel) {
            mCancelOnTouchOutside = cancel;
            return this;
        }

        public LoadingDialog create() {
            LoadingDialog dialog = new LoadingDialog(mContext);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCancelOnTouchOutside);
            dialog.setMessage(mMessage);
            return dialog;
        }

        public LoadingDialog show() {
            LoadingDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
