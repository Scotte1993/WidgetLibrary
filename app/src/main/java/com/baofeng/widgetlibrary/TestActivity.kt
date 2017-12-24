package com.baofeng.widgetlibrary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.abooc.util.Debug

/**
 *
 * @author Junpu
 * @time 2017/12/7 14:35
 */
class TestActivity: AppCompatActivity() {

    companion object {
        fun launch(context: Context) {
            val intent = Intent(context, TestActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Debug.out("TestActivity.onCreate: ")
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        Debug.out("TestActivity.onStart: ")
        super.onStart()
    }

    override fun onRestart() {
        Debug.out("TestActivity.onRestart: ")
        super.onRestart()
    }

    override fun onResume() {
        Debug.out("TestActivity.onResume: ")
        super.onResume()
    }

    override fun onPause() {
        Debug.out("TestActivity.onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Debug.out("TestActivity.onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        Debug.out("TestActivity.onDestroy: ")
        super.onDestroy()
    }
}