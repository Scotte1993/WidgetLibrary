package com.baofeng.statistics

import android.content.Context
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_ACCEPTED
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_ANSWER
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_ANSWER_PAGE
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_BUSY
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_CANCEL
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_DAIL
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_HANG_UP_FROM
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_HANG_UP_TO
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_REJECT
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_TIME_OUT_20s
import com.baofeng.statistics.StatisticsInterface.Companion.UM_FACETIME_TIME_OUT_60s
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure


/**
 * 友盟统计
 *
 * @author chengxiaobo
 * @time 2018/4/10 16:10
 */
class UMStatistics : StatisticsInterface {

    private var context: Context? = null

    override fun init(context: Context) {

        this.context = context
        UMConfigure.init(context.applicationContext, UMConfigure.DEVICE_TYPE_PHONE, null)
        MobclickAgent.setScenarioType(context.applicationContext, MobclickAgent.EScenarioType.E_DUM_NORMAL)
    }

    override fun faceTimeDail() {

        MobclickAgent.onEvent(context, UM_FACETIME_DAIL)
    }

    override fun faceTimeCancel() {

        MobclickAgent.onEvent(context, UM_FACETIME_CANCEL)
    }

    override fun faceTimeTimeOut20s() {

        MobclickAgent.onEvent(context, UM_FACETIME_TIME_OUT_20s)
    }

    override fun faceTimeTimeOut60s() {

        MobclickAgent.onEvent(context, UM_FACETIME_TIME_OUT_60s)
    }

    override fun faceTimeTimeBusy() {

        MobclickAgent.onEvent(context, UM_FACETIME_BUSY)
    }

    override fun faceTimeHangUpFrom() {

        MobclickAgent.onEvent(context, UM_FACETIME_HANG_UP_FROM)
    }

    override fun faceTimeAccepted() {

        MobclickAgent.onEvent(context, UM_FACETIME_ACCEPTED)
    }

    override fun faceTimeAnswerPage() {

        MobclickAgent.onEvent(context, UM_FACETIME_ANSWER_PAGE)
    }

    override fun faceTimeAnswerButton() {

        MobclickAgent.onEvent(context, UM_FACETIME_ANSWER)
    }

    override fun faceTimeRejcet() {

        MobclickAgent.onEvent(context, UM_FACETIME_REJECT)
    }

    override fun faceTimehandUpTo() {

        MobclickAgent.onEvent(context, UM_FACETIME_HANG_UP_TO)
    }
}