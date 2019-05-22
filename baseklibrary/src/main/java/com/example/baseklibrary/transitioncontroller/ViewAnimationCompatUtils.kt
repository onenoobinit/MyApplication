package com.example.baseklibrary.transitioncontroller

import android.animation.Animator
import android.view.View

/**
 * Created by wangqiang on 2019/5/22.
 */
object ViewAnimationCompatUtils {

    val RECT_LEFT = 0
    val RECT_TOP = 1
    val RECT_RIGHT = 2
    val RECT_BOTTOM = 3

    fun createCircularReveal(
        view: View,
        centerX: Int, centerY: Int, startRadius: Float, endRadius: Float
    ): Animator {

        return CircularRevealLayout.Builder.on(view)
            .centerX(centerX)
            .centerY(centerY)
            .startRadius(startRadius)
            .endRadius(endRadius)
            .create()

    }


    @JvmOverloads
    fun createRectReveal(
        view: View,
        startHeight: Float, endHeight: Float, nDirection: Int = ViewAnimationCompatUtils.RECT_TOP
    ): Animator {

        return RectRevealLayout.Builder.on(view)
            .Direction(nDirection)
            .startHeight(startHeight)
            .endHeight(endHeight)
            .create()
    }
}
