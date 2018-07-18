package com.bftv.widget.dial

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 *  拨号盘
 *
 * @author chengxiaobo
 * @time 2018/6/22 15:01
 */
class DialPlate : LinearLayout, View.OnClickListener, View.OnFocusChangeListener {
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

            for (i in numberId) {
                findViewById<TextView>(i).onFocusChangeListener = this
                findViewById<TextView>(i).setOnClickListener(this)
            }
            findViewById<FrameLayout>(R.id.flDelete).setOnClickListener(this)
            findViewById<FrameLayout>(R.id.flDelete).onFocusChangeListener = this

            findViewById<FrameLayout>(R.id.flDial).setOnClickListener(this)
            findViewById<FrameLayout>(R.id.flDial).onFocusChangeListener = this
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

    fun getDailNumTextView(): TextView {
        return (findViewById(R.id.tvPhoneNumber))
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {

            ViewCompat.animate(v).scaleX(1.05f).scaleY(1.05f).translationZ(1.0f).start()
        } else {
            ViewCompat.animate(v).scaleX(1f).scaleY(1f).translationZ(0.0f).start()
        }
        if (v?.id == R.id.flDelete) {
            findViewById<ImageView>(R.id.ivDelete).isSelected = hasFocus
        }

        if (v?.id == R.id.flDial) {
            findViewById<ImageView>(R.id.ivDial).isSelected = hasFocus
        }
    }

    interface OnDialClickListener {
        fun click(dialNumber: String)
    }
}