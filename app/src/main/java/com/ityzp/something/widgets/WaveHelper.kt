package com.ityzp.something.widgets

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import com.gelitenight.waveview.library.WaveView
import java.util.*

/**
 * Created by wangqiang on 2019/7/2.
 */
class WaveHelper(private val mWaveView: WaveView, private val progress: Float) {

    private var mAnimatorSet: AnimatorSet? = null

    init {
        initAnimation()
    }

    fun start() {
        mWaveView.isShowWave = true
        if (mAnimatorSet != null) {
            mAnimatorSet!!.start()
        }
    }

    private fun initAnimation() {
        val animators = ArrayList<Animator>()

        // horizontal animation.
        // wave waves infinitely.
        val waveShiftAnim = ObjectAnimator.ofFloat(
            mWaveView, "waveShiftRatio", 0f, 1f
        )
        waveShiftAnim.repeatCount = ValueAnimator.INFINITE
        waveShiftAnim.duration = 1000
        waveShiftAnim.interpolator = LinearInterpolator()
        animators.add(waveShiftAnim)

        // vertical animation.
        // water level increases from 0 to center of WaveView
        val waterLevelAnim = ObjectAnimator.ofFloat(
            mWaveView, "waterLevelRatio", 0f, progress
        )
        waterLevelAnim.duration = 1000
        waterLevelAnim.interpolator = DecelerateInterpolator()
        animators.add(waterLevelAnim)

        // amplitude animation.
        // wave grows big then grows small, repeatedly
        val amplitudeAnim = ObjectAnimator.ofFloat(
            mWaveView, "amplitudeRatio", 0.0001f, 0.05f
        )
        amplitudeAnim.repeatCount = ValueAnimator.INFINITE
        amplitudeAnim.repeatMode = ValueAnimator.REVERSE
        amplitudeAnim.duration = 5000
        amplitudeAnim.interpolator = LinearInterpolator()
        animators.add(amplitudeAnim)

        mAnimatorSet = AnimatorSet()
        mAnimatorSet!!.playTogether(animators)
        start()
    }

    fun cancel() {
        if (mAnimatorSet != null) {
            //            mAnimatorSet.cancel();
            mAnimatorSet!!.end()
        }
    }
}