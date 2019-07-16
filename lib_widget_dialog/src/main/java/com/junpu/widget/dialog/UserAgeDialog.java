package com.junpu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.junpu.widget.dialog.utils.DialogUtils;

/**
 * 用户选择生日dialog
 */
public class UserAgeDialog extends Dialog {

    private DatePicker mDatePicker;

    private OnDateChangedListener mListener;

    public void setOnDateChangedListener(OnDateChangedListener listener) {
        mListener = listener;
    }

    public UserAgeDialog(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        int width = DialogUtils.getScreenWidth(context);
        LayoutParams lp = new LayoutParams(width, LayoutParams.WRAP_CONTENT);

        ViewGroup view = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.dialog_user_age, null);
        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        setContentView(view, lp);
        mDatePicker.setMaxDate(System.currentTimeMillis());

        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialog_anim_bottom);

        setCanceledOnTouchOutside(true);
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

        public UserAgeDialog create() {
            UserAgeDialog dialog = new UserAgeDialog(mContext);
            dialog.initDate(mBirthday);
            dialog.setOnDateChangedListener(mListener);
            dialog.setOnDismissListener(mOnDismissListener);
            return dialog;
        }

        public UserAgeDialog show() {
            UserAgeDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
