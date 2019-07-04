package com.bftv.widget.sample.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.junpu.widget.EditDialog
import com.junpu.widget.EditDialog.OnSubmitListener
import com.bftv.widget.sample.R
import com.bftv.widget.sample.utils.DialogHelper
import kotlinx.android.synthetic.main.activity_dialog.*

/**
 *
 * @author Junpu
 * @time 2017/12/27 20:02
 */
class DialogActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        initView()
    }

    private fun initView() {
        btnSingle.setOnClickListener {
            DialogHelper.showListDialog(this, arrayOf("1"))
        }
        btnDouble.setOnClickListener {
            DialogHelper.showListDialog(this, arrayOf("1", "2"))
        }
        btnEdit.setOnClickListener {
            DialogHelper.showEditDialog(this, "备注", "", "填写备注", object : OnSubmitListener {
                override fun onSubmit(dialog: EditDialog?, id: Int, message: String?) {
                    println("message = ${message}")
                }
            })
        }
    }

}