package com.baofeng.statistics

import android.content.Context

/**
 * 统计interface
 *
 * @author chengxiaobo
 * @time 2018/4/10 16:00
 */
interface StatisticsInterface {

    companion object {

        //发起方上报
        const val UM_FACETIME_DAIL = "UM_FACETIME_DAIL" // 发起端_拨打电话界面
        const val UM_FACETIME_CANCEL = "UM_FACETIME_CANCEL" // 发起端_取消拨打按钮
        const val UM_FACETIME_TIME_OUT_20s = "UM_FACETIME_TIME_OUT_20s" //发起端_拨打电话界面20s超时
        const val UM_FACETIME_TIME_OUT_60s = "UM_FACETIME_TIME_OUT_60s"//发起端_拨打电话界面60s超时
        const val UM_FACETIME_BUSY = "UM_FACETIME_BUSY"//发起端_用户占线
        const val UM_FACETIME_HANG_UP_FROM = "UM_FACETIME_HANG_UP_FROM" //  发起端_挂断电话按钮
        const val UM_FACETIME_ACCEPTED = "UM_FACETIME_ACCEPTED"//发起端_被接听

        //接收端上报：
        const val UM_FACETIME_ANSWER_PAGE = "UM_FACETIME_ANSWER_PAGE" // 接收端_接听电话界面
        const val UM_FACETIME_ANSWER = "UM_FACETIME_ANSWER" // 接收端_接听电话按钮
        const val UM_FACETIME_REJECT = "UM_FACETIME_REJECT" //  接收端_拒接电话按钮
        const val UM_FACETIME_HANG_UP_TO = "UM_FACETIME_HANG_UP_TO" //  接收端_挂断电话按钮
    }


    /**
     * 初始化
     */
    fun init(context: Context)

    /**
     * 发起端_拨打电话
     */
    fun faceTimeDail()

    /**
     * 发起端_取消拨打按钮
     */
    fun faceTimeCancel()

    /**
     * 发起端_拨打电话界面20s超时
     */
    fun faceTimeTimeOut20s()

    /**
     * 发起端_拨打电话界面60s超时
     */
    fun faceTimeTimeOut60s()

    /**
     * 发起端_用户占线
     */
    fun faceTimeTimeBusy()

    /**
     * 发起端_挂断电话按钮
     */
    fun faceTimeHangUpFrom()

    /**
     * 发起端_被接听
     */
    fun faceTimeAccepted()


    /**
     * 接收端_接听电话界面
     */
    fun faceTimeAnswerPage()

    /**
     * 接收端_接听电话按钮
     */
    fun faceTimeAnswerButton()

    /**
     * 接收端_拒接电话按钮
     */
    fun faceTimeRejcet()

    /**
     * 接收端_挂断电话按钮
     */
    fun faceTimehandUpTo()
}
