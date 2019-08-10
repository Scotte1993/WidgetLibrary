package com.junpu.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * CustomView Dialog
 *
 * @author junpu
 * @date 2019-07-16
 */
public class CustomViewDialog extends BaseDialog {

    public static final int STATUS_LOADING = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_ERROR = 2;
    public static final String DEFAULT_ERROR = "数据错误";

    private TextView mTextTitle;
    private TextView mTextSubTitle;
    private TextView mTextMessage;
    private FrameLayout mCustomLayout;
    private ProgressBar mProgressBar;
    private TextView mTextErrorMessage;

    public CustomViewDialog(Context context) {
        super(context, R.style.Theme_Dialog_Recycler);
    }

    public CustomViewDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomViewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_custom_view;
    }

    @Override
    protected void initWindow(Window window, WindowManager.LayoutParams params) {
        window.setGravity(Gravity.BOTTOM);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void initView() {
        mTextTitle = findViewById(R.id.textTitle);
        mTextSubTitle = findViewById(R.id.textSubTitle);
        mTextMessage = findViewById(R.id.textMessage);
        mCustomLayout = findViewById(R.id.layoutContent);
        mProgressBar = findViewById(R.id.progress);
        mTextErrorMessage = findViewById(R.id.errorMessage);
        loading();
    }

    public void setCustomView(View view) {
        mCustomLayout.addView(view);
    }

    public void setCustomView(View view, FrameLayout.LayoutParams params) {
        mCustomLayout.addView(view, params);
    }

    public void removeCustomView() {
        mCustomLayout.removeAllViews();
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
        mTextTitle.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
    }

    public void setSubTitle(String subTitle) {
        mTextSubTitle.setText(subTitle);
        mTextSubTitle.setVisibility(TextUtils.isEmpty(subTitle) ? View.GONE : View.VISIBLE);
    }

    public void setMessage(String message) {
        mTextMessage.setText(message);
        mTextMessage.setVisibility(TextUtils.isEmpty(message) ? View.GONE : View.VISIBLE);
    }

    /**
     * loading状态
     */
    public void loading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mCustomLayout.setVisibility(View.INVISIBLE);
        mTextErrorMessage.setVisibility(View.GONE);
    }

    /**
     * 成功状态
     */
    public void success() {
        mProgressBar.setVisibility(View.GONE);
        mCustomLayout.setVisibility(View.VISIBLE);
        mTextErrorMessage.setVisibility(View.GONE);
    }

    /**
     * 错误状态
     */
    public void error(String errorMsg) {
        mProgressBar.setVisibility(View.GONE);
        mCustomLayout.setVisibility(View.INVISIBLE);
        mTextErrorMessage.setVisibility(View.VISIBLE);
        mTextErrorMessage.setText(errorMsg);
    }

    /**
     * 设置状态 STATUS_LOADING, STATUS_SUCCESS, STATUS_ERROR
     */
    public void setStatus(int status) {
        setStatus(status, DEFAULT_ERROR);
    }

    public void setStatus(int status, String errorMsg) {
        switch (status) {
            case STATUS_LOADING:
                loading();
                break;
            case STATUS_SUCCESS:
                success();
                break;
            case STATUS_ERROR:
                error(errorMsg);
                break;
        }
    }

    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mSubTitle;
        private String mMessage;
        private int mWidth = -3;
        private int mHeight = -3;
        private View mCustomView;
        private FrameLayout.LayoutParams mLayoutParams;
        private boolean isRemoveCustomView = false;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            this.mSubTitle = subTitle;
            return this;
        }

        public Builder setMessage(int resId) {
            this.mMessage = mContext.getResources().getString(resId);
            return this;
        }

        public Builder setMessage(String msg) {
            this.mMessage = msg;
            return this;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder setCustomView(View view) {
            this.mCustomView = view;
            return this;
        }

        public Builder setCustomView(View view, FrameLayout.LayoutParams params) {
            this.mCustomView = view;
            this.mLayoutParams = params;
            return this;
        }

        public Builder removeCustomView() {
            this.isRemoveCustomView = true;
            return this;
        }

        public CustomViewDialog create() {
            CustomViewDialog dialog = new CustomViewDialog(mContext);
            dialog.setTitle(mTitle);
            dialog.setSubTitle(mSubTitle);
            dialog.setMessage(mMessage);
            if (mWidth != -3) dialog.setWidth(mWidth);
            if (mHeight != -3) dialog.setHeight(mHeight);
            if (isRemoveCustomView) dialog.removeCustomView();
            if (mCustomView != null) {
                if (mLayoutParams != null) {
                    dialog.setCustomView(mCustomView, mLayoutParams);
                } else {
                    dialog.setCustomView(mCustomView);
                }
            }
            return dialog;
        }

        public CustomViewDialog show() {
            CustomViewDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
