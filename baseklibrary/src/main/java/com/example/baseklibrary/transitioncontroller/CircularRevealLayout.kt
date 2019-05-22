package com.example.baseklibrary.transitioncontroller

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout

/**
 * Created by wangqiang on 2019/5/22.
 */
class CircularRevealLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val path: Path
    private var centerX: Float = 0.toFloat()
    private var centerY: Float = 0.toFloat()
    private var revealRadius: Float = 0.toFloat()
    private var isRunning: Boolean = false
    private var childView: View? = null
    private var startRadius: Float = 0.toFloat()
    private var endRadius: Float = 0.toFloat()


    val animator: Animator
        get() {
            val reveal = ObjectAnimator.ofFloat(this, "revealRadius", startRadius, endRadius)

            reveal.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    animationStart(animator)
                }

                override fun onAnimationEnd(animator: Animator) {
                    animationEnd(animator)
                }

                override fun onAnimationCancel(animator: Animator) {
                    animationCancel(animator)
                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })

            return reveal
        }

    init {

        path = Path()


    }


    fun setChildView(childView: View) {
        this.childView = childView
    }

    fun setCenterX(centerX: Float) {
        this.centerX = centerX
    }

    fun setCenterY(centerY: Float) {
        this.centerY = centerY
    }

    fun setStartRadius(startRadius: Float) {
        this.startRadius = startRadius
    }

    fun setEndRadius(endRadius: Float) {
        this.endRadius = endRadius
    }

    fun setRevealRadius(revealRadius: Float) {
        this.revealRadius = revealRadius

        invalidate()

    }

    private fun animationStart(animator: Animator) {
        isRunning = true

    }

    private fun animationEnd(animator: Animator) {
        isRunning = false

    }

    private fun animationCancel(animator: Animator) {
        isRunning = false

    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        if (isRunning && childView === child) {
            val state = canvas.save()

            path.reset()
            path.addCircle(centerX, centerY, revealRadius, Path.Direction.CW)

            canvas.clipPath(path)

            val isInvalided = super.drawChild(canvas, child, drawingTime)

            canvas.restoreToCount(state)

            return isInvalided
        }

        return super.drawChild(canvas, child, drawingTime)
    }


    class Builder(private val view: View) {
        private var centerX: Float = 0.toFloat()
        private var centerY: Float = 0.toFloat()
        private var startRadius: Float = 0.toFloat()
        private var endRadius: Float = 0.toFloat()


        fun centerX(centerX: Int): Builder {
            this.centerX = centerX.toFloat()
            return this
        }

        fun centerY(centerY: Int): Builder {
            this.centerY = centerY.toFloat()
            return this
        }

        fun startRadius(startRadius: Float): Builder {
            this.startRadius = startRadius
            return this
        }

        fun endRadius(endRadius: Float): Builder {
            this.endRadius = endRadius
            return this
        }

        private fun setParameter(layout: CircularRevealLayout) {
            layout.setCenterX(centerX)
            layout.setCenterY(centerY)
            layout.setStartRadius(startRadius)
            layout.setEndRadius(endRadius)
            layout.setChildView(view)

        }


        fun create(): Animator {

            if (view.parent != null && view.parent is CircularRevealLayout) {

                val layout = view.parent as CircularRevealLayout

                setParameter(layout)

                return layout.animator
            }

            val layout = CircularRevealLayout(view.context)


            if (Build.VERSION.SDK_INT >= 11) {
                layout.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }

            setParameter(layout)


            val params = view.layoutParams
            val parent = view.parent as ViewGroup
            var index = 0


            if (parent != null) {
                index = parent.indexOfChild(view)
                parent.removeView(view)
            }

            layout.addView(view, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))

            parent?.addView(layout, index, params)


            return layout.animator

        }

        companion object {


            fun on(view: View): Builder {
                return Builder(view)
            }
        }


    }
}
