package com.junpu.widget.sample.utils

import android.content.Context
import android.content.DialogInterface
import com.junpu.widget.dialog.EditDialog
import com.junpu.widget.dialog.EditDialog.OnSubmitListener
import com.junpu.widget.dialog.ListDialog
import com.junpu.widget.dialog.LoadingDialog
import com.junpu.widget.dialog.ToastDialog

/**
 * 快速生成对话框
 * @author zhangjunpu
 * @date 2017/2/10
 */
object DialogHelper {

    /**
     * 显示提示对话框
     * @param context
     * @param message
     * @param positiveListener
     * @return
     */
    fun showToastDialog(context: Context, message: String, positiveListener: DialogInterface.OnClickListener? = null): com.junpu.widget.dialog.ToastDialog {
        return com.junpu.widget.dialog.ToastDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveListener)
                .show()
    }

    /**
     * 显示列表对话框
     * @param list 列表数据
     * @param listener 点击回调
     * @param isLand 是否为横屏
     */
    @JvmOverloads
    fun showListDialog(context: Context, list: Array<String>, listener: DialogInterface.OnClickListener? = null, isLand: Boolean = false): com.junpu.widget.dialog.ListDialog {
        return com.junpu.widget.dialog.ListDialog.Builder(context)
                .setList(list)
                .setOnItemClickListener(listener)
                .setIsLandScape(isLand)
                .show()
    }

    /**
     * 显示Loading对话框
     * @param context
     * @param msg
     * @return
     */
    fun showLoadingDialog(context: Context, msg: String): com.junpu.widget.dialog.LoadingDialog {
        return com.junpu.widget.dialog.LoadingDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .show()
    }

    /**
     * 显示编辑对话框
     * @param context
     * @param message
     * @param positiveListener
     * @return
     */
    fun showEditDialog(context: Context, title: String?, message: String?, hint: String?, positiveListener: OnSubmitListener? = null): com.junpu.widget.dialog.EditDialog {
        return com.junpu.widget.dialog.EditDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setHint(hint)
                .setPositiveButton(positiveListener)
                .setSingleLine(true)
                .setMaxLength(5)
                .show()
    }

    fun showEditDialog(context: Context, title: String?, message: String?, positiveListener: OnSubmitListener? = null): com.junpu.widget.dialog.EditDialog {
        return com.junpu.widget.dialog.EditDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveListener)
                .show()
    }

}