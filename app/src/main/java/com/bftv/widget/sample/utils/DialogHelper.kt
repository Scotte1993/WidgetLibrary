package com.bftv.widget.sample.utils

import android.content.Context
import android.content.DialogInterface
import com.junpu.widget.EditDialog
import com.junpu.widget.EditDialog.OnSubmitListener
import com.junpu.widget.ListDialog
import com.junpu.widget.LoadingDialog
import com.junpu.widget.ToastDialog

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
    fun showToastDialog(context: Context, message: String, positiveListener: DialogInterface.OnClickListener? = null): ToastDialog {
        return ToastDialog.Builder(context)
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
    fun showListDialog(context: Context, list: Array<String>, listener: DialogInterface.OnClickListener? = null, isLand: Boolean = false): ListDialog {
        return ListDialog.Builder(context)
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
    fun showLoadingDialog(context: Context, msg: String): LoadingDialog {
        return LoadingDialog.Builder(context)
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
    fun showEditDialog(context: Context, title: String?, message: String?, hint: String?, positiveListener: OnSubmitListener? = null): EditDialog {
        return EditDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setHint(hint)
                .setPositiveButton(positiveListener)
                .setSingleLine(true)
                .setMaxLength(5)
                .show()
    }

    fun showEditDialog(context: Context, title: String?, message: String?, positiveListener: OnSubmitListener? = null): EditDialog {
        return EditDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveListener)
                .show()
    }

}