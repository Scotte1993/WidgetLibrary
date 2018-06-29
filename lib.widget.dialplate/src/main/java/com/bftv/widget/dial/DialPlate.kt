package com.bftv.widget.dial

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

/**
 *  拨号盘
 *
 * @author chengxiaobo
 * @time 2018/6/22 15:01
 */
class DialPlate : LinearLayout, View.OnClickListener {
    companion object {
        val number = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        val numberId = listOf(R.id.tv0, R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9)
    }

    var dialClick: OnDialClickListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        if (context != null) {
            LayoutInflater.from(context).inflate(R.layout.view_dial_plate, this)
            findViewById<TextView>(R.id.tv0).setOnClickListener(this)
            findViewById<TextView>(R.id.tv1).setOnClickListener(this)
            findViewById<TextView>(R.id.tv2).setOnClickListener(this)
            findViewById<TextView>(R.id.tv3).setOnClickListener(this)
            findViewById<TextView>(R.id.tv4).setOnClickListener(this)
            findViewById<TextView>(R.id.tv5).setOnClickListener(this)
            findViewById<TextView>(R.id.tv6).setOnClickListener(this)
            findViewById<TextView>(R.id.tv7).setOnClickListener(this)
            findViewById<TextView>(R.id.tv8).setOnClickListener(this)
            findViewById<TextView>(R.id.tv9).setOnClickListener(this)
            findViewById<FrameLayout>(R.id.flDelete).setOnClickListener(this)
            findViewById<FrameLayout>(R.id.flDial).setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.flDelete -> {
                val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
                if (tvPhoneNumber.text.toString().isEmpty()) {
                    return
                } else {
                    tvPhoneNumber.text = tvPhoneNumber.text.toString().substring(0, tvPhoneNumber.text.length - 1)
                }
            }
            R.id.flDial -> {
                val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
                dialClick?.click(tvPhoneNumber.text.toString())
            }
            else -> {
                v?.let {
                    val index = numberId.indexOf(v.id)
                    index?.let {
                        val tvPhoneNumber = findViewById<TextView>(R.id.tvPhoneNumber)
                        if (tvPhoneNumber.text.toString().trim().length < 11) {
                            findViewById<TextView>(R.id.tvPhoneNumber).append(number[index])
                        }
                    }
                }
            }
        }
    }

    interface OnDialClickListener {
        fun click(dialNumber: String)
    }
}