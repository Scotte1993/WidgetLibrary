package com.junpu.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Dialog Utils
 *
 * @author junpu
 * @date 2019-07-16
 */
class DialogUtils {

    /**
     * 获取屏幕宽度
     */
    static int getScreenWidth(Context context) {
        int width = 0;
        if (context instanceof Activity) {
            width = ((Activity) context).getWindow().getDecorView().getMeasuredWidth();
        }
        if (width <= 0)
            width = getDisplayMetrics(context).widthPixels;
        return width;
    }

    /**
     * 获取屏幕高度
     */
    static int getScreenHeight(Context context) {
        int height = 0;
        if (context instanceof Activity) {
            height = ((Activity) context).getWindow().getDecorView().getMeasuredHeight();
        }
        if (height <= 0)
            height = getDisplayMetrics(context).heightPixels;
        return height;
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 返回dp值
     */
    static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 返回dp值
     */
    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    static int parseIntSafely(String number) {
        return parseIntSafely(number, 0);
    }

    private static int parseIntSafely(String number, int defValue) {
        if (TextUtils.isEmpty(number)) return defValue;
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException exception) {
            Log.e("System.err", Log.getStackTraceString(exception));
        }
        return defValue;
    }

    static void hideInputMethod(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
