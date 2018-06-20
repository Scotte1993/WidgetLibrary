package com.btfv.widget

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.FrameLayout

/**
 * 浮动ViewGroup 可以拖拽，左右停靠
 *
 * @author chengxiaobo
 * @time 2018/6/15 14:58
 */
class FloatViewGroup : FrameLayout {

    companion object {
        const val MIN_MOVE = 2.0f //dp
    }

    private var parentWidth = 0.0f
    private var parentHeight = 0.0f

//    private val marginTop = 0.0f
//    private val marginBottom = 0.0f
//    private val marginLeft = 0.0f
//    private val marginRight = 0.0f

    private var downTranslationX = 0.0f
    private var downTranslationY = 0.0f
    private var downX = 0f
    private var downY = 0f

    private var animator: ObjectAnimator? = null
    private var isMove = false //是否移动，没有移动,模拟点击
    private var minMovePx = 0 //最小移动距离

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
        this.post {
            parentWidth = (parent as ViewGroup).width.toFloat()
            parentHeight = (parent as ViewGroup).height.toFloat()
        }

        minMovePx = dp2px(context, MIN_MOVE)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        var intercept = false
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTranslationX = translationX
                downTranslationY = translationY
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
                translationX = downTranslationX + event.rawX - downX
                if (translationX > 0) translationX = 0.0f
                if (translationX < -(parentWidth - width)) translationX = -(parentWidth - width)

                translationY = downTranslationY + event.rawY - downY
                if (translationY > 0) translationY = 0.0f
                if (translationY < -(parentHeight - height)) translationY = -(parentHeight - height)
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                val t = if ((parentWidth - width) / 2 + translationX > 0) 0.0f else -(parentWidth - width)
                startAnimation(t)
                if (!isMove) performClick()

            }
        }
        return true
    }

    private fun startAnimation(t: Float) {
        animator = ObjectAnimator.ofFloat(this, "translationX", translationX, t)
        animator?.duration = 100L
        animator?.interpolator = AccelerateInterpolator()
        animator?.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
