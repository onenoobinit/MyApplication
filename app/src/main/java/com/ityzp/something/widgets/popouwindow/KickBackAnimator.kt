package com.ityzp.something.widgets.popouwindow

import android.animation.TypeEvaluator

/**
 * Created by wangqiang on 2019/7/12.
 */
class KickBackAnimator : TypeEvaluator<Float> {
    private val s = 1.70158f
    internal var mDuration = 0f

    fun setDuration(duration: Float) {
        mDuration = duration
    }

    override fun evaluate(fraction: Float, startValue: Float?, endValue: Float?): Float? {
        val t = mDuration * fraction
        val b = startValue!!.toFloat()
        val c = endValue!!.toFloat() - startValue.toFloat()
        val d = mDuration
        return calculate(t, b, c, d)!!
    }

    fun calculate(t: Float, b: Float, c: Float, d: Float): Float? {
        var t = t
        return c * ((t / d - 1) * t * ((s + 1) * t + s) + 1) + b
    }
}
