package com.junpu.widget.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 编辑对话框
 *
 * @author Junpu
 * @time 2018/9/13 14:33
 */
public class EditDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTextTitle;
    private EditText mEditText;
    // 取消按钮
    private TextView mNegativeButton;
    private OnClickListener mNegativeListener;
    // 确定按钮
    private TextView mPositiveButton;
    private OnSubmitListener mOnSubmitListener;

    public EditDialog(Context context) {
        super(context);
    }

    public EditDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public EditDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_edit;
    }

    @Override
    protected void initWindow(Window window, WindowManager.LayoutParams params) {
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void initView() {
        setCanceledOnTouchOutside(false);
        mTextTitle = findViewById(R.id.title);
        mEditText = findViewById(R.id.editText);
        mNegativeButton = findViewById(R.id.cancel);
        mPositiveButton = findViewById(R.id.submit);
        mNegativeButton.setOnClickListener(this);
        mPositiveButton.setOnClickListener(this);
    }

    public void setText(String content) {
        mEditText.setText(content);
        mEditText.requestFocus();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cancel) {
            DialogUtils.hideInputMethod(getContext(), view);
            if (mNegativeListener != null) {
                mNegativeListener.onClick(this, id);
            }
        } else if (id == R.id.submit) {
            String message = mEditText.getText().toString();
            if (mOnSubmitListener != null) {
                mOnSubmitListener.onSubmit(this, id, message);
            }
        }
        cancel();
    }

    /**
     * 获取EditText
     */
    public EditText getEditText() {
        return mEditText;
    }

    /**
     * 设置标题
     */
    public void setTitle(String text) {
        mTextTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTextTitle.setText(text);
    }

    /**
     * 设置Message
     */
    public void setMessage(String text) {
        mEditText.setText(text);
    }

    /**
     * 设置EditText提示语
     */
    public void setHint(String text) {
        mEditText.setHint(text);
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
    public void setPositiveButton(OnSubmitListener listener) {
        mOnSubmitListener = listener;
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
    public void setPositiveButton(String text, OnSubmitListener listener) {
        mPositiveButton.setText(text);
        setPositiveButton(listener);
    }

    /**
     * 设置单行显示
     */
    public void setSingleLine(boolean singleLine) {
        mEditText.setSingleLine(singleLine);
    }

    /**
     * 设置最大行数
     */
    public void setMaxLines(int maxLines) {
        mEditText.setMaxLines(maxLines);
    }

    /**
     * 设置最大字数
     */
    public void setMaxLength(int maxLength) {
        InputFilter inputFilter = new InputFilter.LengthFilter(maxLength);
        mEditText.setFilters(new InputFilter[]{inputFilter});
    }


    public static class Builder {
        private Context mContext;
        private String mTitle;
        private String mMessage;
        private String mHint;
        private OnClickListener mNegativeListener;
        private OnSubmitListener mOnSubmitListener;
        private String mNegativeText;
        private String mPositiveText;
        private boolean mSingleLine;
        private int mMaxLines;
        private int mMaxLength;

        public Builder(Context context) {
            mContext = context;
        }

        public EditDialog.Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public EditDialog.Builder setMessage(int resId) {
            mMessage = mContext.getResources().getString(resId);
            return this;
        }

        public EditDialog.Builder setMessage(String msg) {
            mMessage = msg;
            return this;
        }

        public EditDialog.Builder setHint(String hint) {
            mHint = hint;
            return this;
        }

        public EditDialog.Builder setNegativeButton(String text) {
            mNegativeText = text;
            return this;
        }

        public EditDialog.Builder setPositiveButton(String text) {
            mPositiveText = text;
            return this;
        }

        public EditDialog.Builder setNegativeButton(OnClickListener listener) {
            mNegativeListener = listener;
            return this;
        }

        public EditDialog.Builder setPositiveButton(OnSubmitListener listener) {
            mOnSubmitListener = listener;
            return this;
        }

        public EditDialog.Builder setNegativeButton(String text, OnClickListener listener) {
            mNegativeText = text;
            mNegativeListener = listener;
            return this;
        }

        public EditDialog.Builder setPositiveButton(String text, OnSubmitListener listener) {
            mPositiveText = text;
            mOnSubmitListener = listener;
            return this;
        }

        public EditDialog.Builder setSingleLine(boolean singleLine) {
            mSingleLine = singleLine;
            return this;
        }

        public EditDialog.Builder setMaxLines(int maxLines) {
            mMaxLines = maxLines;
            return this;
        }

        public EditDialog.Builder setMaxLength(int maxLength) {
            mMaxLength = maxLength;
            return this;
        }

        public EditDialog create() {
            EditDialog dialog = new EditDialog(mContext);
            dialog.setTitle(mTitle);
            dialog.setMessage(mMessage);
            dialog.setHint(mHint);
            if (!TextUtils.isEmpty(mNegativeText)) {
                dialog.setNegativeButton(mNegativeText);
            }
            if (mNegativeListener != null) {
                dialog.setNegativeButton(mNegativeListener);
            }
            if (!TextUtils.isEmpty(mPositiveText)) {
                dialog.setPositiveButton(mPositiveText);
            }
            if (mOnSubmitListener != null) {
                dialog.setPositiveButton(mOnSubmitListener);
            }
            dialog.setSingleLine(true);
            if (mMaxLines > 0) {
                dialog.setMaxLines(mMaxLines);
            }
            if (mMaxLength > 0) {
                dialog.setMaxLength(mMaxLength);
            }
            return dialog;
        }

        public EditDialog show() {
            EditDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

    /**
     * 确认按钮事件
     */
    public interface OnSubmitListener {
        void onSubmit(EditDialog dialog, int id, String message);
    }

}
