package com.btfv.widget

import android.content.Context
import android.util.TypedValue

/**
 * 返回dp值
 */
fun dp2px(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
}

/**
 * 返回dp值
 */
fun dp2px(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
}


