package com.bftv.sample

import android.app.Activity
import android.content.Intent

/**
 *
 * @author Junpu
 * @time 2017/12/27 20:01
 */
fun Activity.launch(cls: Class<*>) {
    val intent = Intent(this, cls)
    startActivity(intent)
}