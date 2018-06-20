package com.bftv.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * 侧滑View
 *
 * @author chengxiaobo
 * @time 2018/6/15 14:58
 */
class SlideView : RelativeLayout {
    companion object {
        const val MIN_MOVE = 2.0f //dp
    }

    private var handleWidth = 0.0f
    private var handleHeight = 0.0f
    private var downTranslationX = 0.0f
    private var downX = 0f
    private var downY = 0f
    private var animator: ObjectAnimator? = null
    private var isMove = false //是否移动，没有移动,模拟点击
    private var minMovePx = 0 //最小移动距离
    private var clickListener: ClickListener? = null
    private lateinit var container: FrameLayout
    private var isStretch = false

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
        minMovePx = dp2px(context, MIN_MOVE)
        LayoutInflater.from(context).inflate(R.layout.view_slide, this, true)
        this.post {
            handleWidth = findViewById<LinearLayout>(R.id.llHandle).width.toFloat()
            handleHeight = findViewById<LinearLayout>(R.id.llHandle).height.toFloat()
            container = findViewById(R.id.container)
            findViewById<View>(R.id.btnDelete).setOnClickListener {
                clickListener?.clickDelete()
                shrink()
            }
            findViewById<View>(R.id.btnNickName).setOnClickListener {
                clickListener?.clickAddNick()
                shrink()
            }
        }
    }

    fun setListener(listener: ClickListener?) {
        clickListener = listener
    }

    fun addContainerView(view: ViewGroup) {
        container.addView(view)
    }

    /**
     * 收缩
     */
    private fun shrink() {
        isStretch = false
        startAnimation(0.0f)
    }

    /**
     * 伸开
     */
    private fun stretch() {
        isStretch = true
        startAnimation(-handleWidth)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        var intercept = false
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTranslationX = container.translationX
                downX = event.rawX
                downY = event.rawY
                isMove = false
                intercept = false
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.rawY - downY) > minMovePx && Math.abs(event.rawX - downX) > minMovePx) {
                    isMove = true
                    intercept = true
                }
            }
            else -> {
                intercept = false
            }
        }
        return intercept
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                if (Math.abs(event.rawY - downY) > minMovePx && Math.abs(event.rawX - downX) > minMovePx) {
                    isMove = true
                }
                var x = downTranslationX + event.rawX - downX
                if (x > 0) x = 0.0f
                if (x < -handleWidth) x = -handleWidth
                container.translationX = x
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                val t = if (container.translationX < -handleWidth / 2) -handleWidth else 0.0f
                startAnimation(t)
                if (!isMove) {
                    performClick()
                }
            }
        }
        return true
    }

    private fun startAnimation(t: Float) {
        animator = ObjectAnimator.ofFloat(container, "translationX", container.translationX, t)
        animator?.duration = 100L
        animator?.interpolator = AccelerateInterpolator()
        animator?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }

    interface ClickListener {
        fun clickDelete()
        fun clickAddNick()
    }
}
