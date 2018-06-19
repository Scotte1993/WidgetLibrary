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

    private var mTranslationY = 0.0f
    private var mTranslationX = 0.0f
    private var downY = 0f
    private var downX = 0f

    private var animator: ObjectAnimator? = null
    private var isMove = false
    private var minMovePx = 0

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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mTranslationY = translationY
                mTranslationX = translationX
                downX = event.rawX
                downY = event.rawY
                isMove = false
            }
            MotionEvent.ACTION_MOVE -> {

                if (Math.abs(event.rawY - downX) > 5 && Math.abs(event.rawX - downX) > 5) {
                    isMove = true
                }

                translationY = mTranslationY + event.rawY - downY
                translationX = mTranslationX + event.rawX - downX

                if (translationY > 0) translationY = 0.0f
                if (translationY < -(parentHeight - height)) translationY = -(parentHeight - height)

                if (translationX > 0) translationX = 0.0f
                if (translationX < -(parentWidth - width)) translationX = -(parentWidth - width)
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
