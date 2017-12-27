package com.bftv.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bftv.sample.utils.DialogHelper
import kotlinx.android.synthetic.main.activity_list_dialog.*

/**
 *
 * @author Junpu
 * @time 2017/12/27 20:02
 */
class ListDialogActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_dialog)
        initView()
    }

    private fun initView() {
        btnSingle.setOnClickListener {
            DialogHelper.showListDialog(this, arrayOf("1"))
        }
        btnDouble.setOnClickListener {
            DialogHelper.showListDialog(this, arrayOf("1", "2"))
        }
    }

}