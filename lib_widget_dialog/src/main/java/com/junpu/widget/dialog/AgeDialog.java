package com.junpu.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

/**
 * 用户选择生日dialog
 */
public class AgeDialog extends BaseDialog {

    private DatePicker mDatePicker;

    private OnDateChangedListener mListener;

    public AgeDialog(Context context) {
        super(context);
    }

    public AgeDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public AgeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        mListener = listener;
    }

    @Override
    protected int layout() {
        return R.layout.dialog_age;
    }

    @Override
    protected void initWindow(Window window, WindowManager.LayoutParams params) {
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_anim_bottom);
    }

    @Override
    protected void initView() {
        setCanceledOnTouchOutside(true);
        mDatePicker = findViewById(R.id.datePicker);
        mDatePicker.setMaxDate(System.currentTimeMillis());
    }

    /**
     * 初始化年月日
     *
     * @param birthday 格式：1900-01-01
     */
    public void initDate(String birthday) {
        // 用户的生日
        int year = 1990;
        int month = 1;
        int day = 1;
        if (!TextUtils.isEmpty(birthday)) {
            String[] birth = birthday.split("-");
            year = DialogUtils.parseIntSafely(birth[0]);
            month = DialogUtils.parseIntSafely(birth[1]);
            day = DialogUtils.parseIntSafely(birth[2]);
        }
        initDate(year, month, day);
    }

    /**
     * 初始化年月日
     *
     * @param year
     * @param month
     * @param day
     */
    public void initDate(int year, int month, int day) {
        // 初始化数据
        mDatePicker.init(year, month - 1, day, new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                if (mListener != null) {
                    mListener.onDateChanged(datePicker, year, month, dayOfMonth);
                }
            }
        });
    }

    /**
     * 获取当前显示时间
     */
    public int[] getDate() {
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        return new int[]{year, month, day};
    }

    public int getYear() {
        return mDatePicker.getYear();
    }

    public int getMonth() {
        return mDatePicker.getMonth();
    }

    public int getDay() {
        return mDatePicker.getDayOfMonth();
    }

    public static class Builder {
        private Context mContext;
        private String mBirthday;
        private OnDateChangedListener mListener;
        private OnDismissListener mOnDismissListener;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder initDate(String birthday) {
            mBirthday = birthday;
            return this;
        }

        public Builder setOnDateChangedListener(OnDateChangedListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            mOnDismissListener = listener;
            return this;
        }

        public AgeDialog create() {
            AgeDialog dialog = new AgeDialog(mContext);
            dialog.initDate(mBirthday);
            dialog.setOnDateChangedListener(mListener);
            dialog.setOnDismissListener(mOnDismissListener);
            return dialog;
        }

        public AgeDialog show() {
            AgeDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
