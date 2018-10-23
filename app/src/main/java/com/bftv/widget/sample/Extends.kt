package com.bftv.widget.sample

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment

/**
 *
 * @author Junpu
 * @time 2017/12/27 20:01
 */
fun Context.launch(cls: Class<*>) {
    val intent = Intent(this, cls)
    startActivity(intent)
}

fun Fragment.launch(cls: Class<*>) {
    context.launch(cls)
}