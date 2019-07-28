package com.junpu.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * 提示对话框，用于展示一段文字
 *
 * @author Junpu
 * @time 2017/2/24 13:37
 */
public class ToastDialog extends BaseDialog implements View.OnClickListener {

    private TextView mMessageText;
    private View mCutline;
    // 取消按钮
    private TextView mNegativeButton;
    private OnClickListener mNegativeListener;
    //确定按钮
    private TextView mPositiveButton;
    private OnClickListener mPositiveListener;

    public ToastDialog(Context context) {
        super(context);
    }

    public ToastDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ToastDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_toast;
    }

    @Override
    protected void initView() {
        setCanceledOnTouchOutside(false);
        mMessageText = findViewById(R.id.textMessage);
        mCutline = findViewById(R.id.line);
        mNegativeButton = findViewById(R.id.negative);
        mNegativeButton.setOnClickListener(this);
        mPositiveButton = findViewById(R.id.positive);
        mPositiveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.negative) {
            if (mNegativeListener != null) {
                mNegativeListener.onClick(this, v.getId());
            }
        } else if (id == R.id.positive) {
            if (mPositiveListener != null) {
                mPositiveListener.onClick(this, v.getId());
            }
        }
        cancel();
    }


    public void setMessage(int resId) {
        mMessageText.setText(resId);
    }

    public void setMessage(String msg) {
        mMessageText.setText(msg);
    }

    /**
     * 设置取消按钮文字
     *
     * @param text
     */
    public void setNegativeButton(String text) {
        mNegativeButton.setText(text);
    }

    /**
     * 设置确定按钮文字
     *
     * @param text
     */
    public void setPositiveButton(String text) {
        mPositiveButton.setText(text);
    }

    /**
     * 设置取消按钮
     */
    public void setNegativeButton(OnClickListener listener) {
        mNegativeListener = listener;
    }

    /**
     * 设置确定按钮
     */
    public void setPositiveButton(OnClickListener listener) {
        mCutline.setVisibility(View.VISIBLE);
        mPositiveButton.setVisibility(View.VISIBLE);
        mPositiveListener = listener;
    }

    /**
     * 设置取消按钮
     */
    public void setNegativeButton(String text, OnClickListener listener) {
        mNegativeButton.setText(text);
        setNegativeButton(listener);
    }

    /**
     * 设置确定按钮
     */
    public void setPositiveButton(String text, OnClickListener listener) {
        mPositiveButton.setText(text);
        setPositiveButton(listener);
    }

    public static class Builder {
        private Context mContext;
        private String mMessage;
        private OnClickListener mNegativeListener;
        private OnClickListener mPositiveListener;
        private String mNegativeText;
        private String mPositiveText;

        public Builder(Context context) {
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }

        public ToastDialog.Builder setMessage(int resId) {
            mMessage = getContext().getResources().getString(resId);
            return this;
        }

        public ToastDialog.Builder setMessage(String msg) {
            mMessage = msg;
            return this;
        }

        public ToastDialog.Builder setNegativeButton(String text) {
            mNegativeText = text;
            return this;
        }

        public ToastDialog.Builder setPositiveButton(String text) {
            mPositiveText = text;
            return this;
        }

        public ToastDialog.Builder setNegativeButton(OnClickListener listener) {
            mNegativeListener = listener;
            return this;
        }

        public ToastDialog.Builder setPositiveButton(OnClickListener listener) {
            mPositiveListener = listener;
            return this;
        }

        public ToastDialog.Builder setNegativeButton(String text, OnClickListener listener) {
            mNegativeText = text;
            mNegativeListener = listener;
            return this;
        }

        public ToastDialog.Builder setPositiveButton(String text, OnClickListener listener) {
            mPositiveText = text;
            mPositiveListener = listener;
            return this;
        }

        public ToastDialog create() {
            ToastDialog dialog = new ToastDialog(mContext);
            dialog.setMessage(mMessage);
            if (!TextUtils.isEmpty(mNegativeText)) {
                dialog.setNegativeButton(mNegativeText);
            }
            if (mNegativeListener != null) {
                dialog.setNegativeButton(mNegativeListener);
            }
            if (!TextUtils.isEmpty(mPositiveText)) {
                dialog.setPositiveButton(mPositiveText);
            }
            if (mPositiveListener != null) {
                dialog.setPositiveButton(mPositiveListener);
            }
            return dialog;
        }

        public ToastDialog show() {
            ToastDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
