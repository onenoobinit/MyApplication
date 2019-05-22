package com.example.baseklibrary.transitioncontroller

import android.animation.Animator

/**
 * Created by wangqiang on 2019/5/22.
 */
interface TransitionCustomListener {

    fun onTransitionStart(animator: Animator)

    fun onTransitionEnd(animator: Animator)

    fun onTransitionCancel(animator: Animator)

}