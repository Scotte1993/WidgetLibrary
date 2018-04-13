package com.baofeng.statistics

import android.content.Context

/**
 * 统计管理类
 *
 * @author chengxiaobo
 * @time 2018/4/10 18:00
 */
object StatisticsManager : StatisticsInterface {

    val list = ArrayList<StatisticsInterface>()

    fun add(bean: StatisticsInterface) {

        list.add(bean)
    }

    override fun init(context: Context) {

        list.forEach {
            it.init(context)
        }

    }

    override fun faceTimeDail() {

        list.forEach {
            it.faceTimeDail()
        }
    }

    override fun faceTimeCancel() {

        list.forEach {
            it.faceTimeCancel()
        }
    }

    override fun faceTimeTimeOut20s() {

        list.forEach {
            it.faceTimeTimeOut20s()
        }
    }

    override fun faceTimeTimeOut60s() {

        list.forEach {
            it.faceTimeTimeOut60s()
        }
    }

    override fun faceTimeTimeBusy() {

        list.forEach {
            it.faceTimeTimeBusy()
        }
    }

    override fun faceTimeHangUpFrom() {

        list.forEach {
            it.faceTimeHangUpFrom()
        }
    }

    override fun faceTimeAccepted() {

        list.forEach {
            it.faceTimeAccepted()
        }
    }

    override fun faceTimeAnswerPage() {

        list.forEach {
            it.faceTimeAnswerPage()
        }
    }

    override fun faceTimeAnswerButton() {

        list.forEach {
            it.faceTimeAnswerButton()
        }
    }

    override fun faceTimeRejcet() {

        list.forEach {
            it.faceTimeRejcet()
        }
    }

    override fun faceTimehandUpTo() {

        list.forEach {
            it.faceTimehandUpTo()
        }
    }
}
