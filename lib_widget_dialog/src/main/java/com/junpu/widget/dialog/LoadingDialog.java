package com.junpu.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Loading dialog.
 *
 * @author junpu
 * @date 2019-07-28
 */
public class LoadingDialog extends BaseDialog {

    private TextView mMessageView;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initWindow(Window window, WindowManager.LayoutParams params) {
//        params.dimAmount = 0;
    }

    @Override
    protected void initView() {
        mMessageView = findViewById(R.id.textMessage);
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
            if (!TextUtils.isEmpty(mMessage)) dialog.setMessage(mMessage);
            return dialog;
        }

        public LoadingDialog show() {
            LoadingDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
