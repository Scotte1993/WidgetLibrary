package com.junpu.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.baofeng.widget.R;


/**
 * 列表对话框
 */
public class NumberPickerDialog extends Dialog {
    
    private NumberPicker mNumberpicker;

    private OnValueChangeListener mOnValueChangeListener;

    public NumberPickerDialog(Context context) {
        this(context, 0);
    }

    public NumberPickerDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_numberpicker);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setLandScape(false);
        initUI();
    }

    private void initUI() {
        mNumberpicker = (NumberPicker) findViewById(R.id.numberpicker);
        // 关闭可编辑模式
        mNumberpicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mNumberpicker.setOnValueChangedListener(new OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (mOnValueChangeListener != null) {
                    mOnValueChangeListener.onValueChange(picker, oldVal, newVal);
                }
            }
        });
    }

    public void setLandScape(boolean isLand) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.getDecorView().setPadding(0, 0, 0, 0);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    /**
     * 设置最大值
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        mNumberpicker.setMaxValue(maxValue);
    }

    /**
     * 设置最小值
     * @param minValue
     */
    public void setMinValue(int minValue) {
        mNumberpicker.setMinValue(minValue);
    }

    /**
     * 设置当前值
     * @param value
     */
    public void setValue(int value) {
        mNumberpicker.setValue(value);
    }

    /**
     * 获取当前值
     * @return
     */
    public int getValue() {
        return mNumberpicker.getValue();
    }

    /**
     * 设置数值改变事件
     * @param onValueChangedListener
     */
    public void setOnValueChangedListener(OnValueChangeListener onValueChangedListener) {
        mOnValueChangeListener = onValueChangedListener;
    }

    public static class Builder {
        private Context mContext;
        private int mMaxValue;
        private int mMinValue;
        private int mValue;
        private OnValueChangeListener mListener;
        private OnDismissListener mOnDismissListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder setMaxValue(int maxValue) {
            mMaxValue = maxValue;
            return this;
        }

        public Builder setMinValue(int minValue) {
            mMinValue = minValue;
            return this;
        }

        public Builder setValue(int value) {
            mValue = value;
            return this;
        }

        public Builder setOnValueChangedListener(OnValueChangeListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            mOnDismissListener = listener;
            return this;
        }

        public NumberPickerDialog create() {
            NumberPickerDialog dialog = new NumberPickerDialog(mContext);
            dialog.setMaxValue(mMaxValue);
            dialog.setMinValue(mMinValue);
            dialog.setValue(mValue);
            dialog.setOnValueChangedListener(mListener);
            dialog.setOnDismissListener(mOnDismissListener);
            return dialog;
        }

        public NumberPickerDialog show() {
            NumberPickerDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}