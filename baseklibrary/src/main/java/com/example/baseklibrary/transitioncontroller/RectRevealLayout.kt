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
class RectRevealLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val path: Path
    private var revealRadius: Float = 0.toFloat()
    private var isRunning: Boolean = false
    private var childView: View? = null
    private var startHeight: Float = 0.toFloat()
    private var endHeight: Float = 0.toFloat()
    private var nDirection: Int = 0


    val animator: Animator
        get() {
            val reveal = ObjectAnimator.ofFloat(this, "revealRadius", startHeight, endHeight)

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

    fun setDirection(nDirection: Int) {
        this.nDirection = nDirection
    }


    fun setStartHeight(startHeight: Float) {
        this.startHeight = startHeight
    }

    fun setEndHeight(endHeight: Float) {
        this.endHeight = endHeight
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
            //            path.addCircle(centerX, centerY, revealRadius, Path.Direction.CW);

            when (nDirection) {
                ViewAnimationCompatUtils.RECT_TOP -> path.addRect(
                    0f,
                    0f,
                    width.toFloat(),
                    revealRadius,
                    Path.Direction.CW
                )
                ViewAnimationCompatUtils.RECT_LEFT -> path.addRect(
                    0f,
                    0f,
                    revealRadius,
                    height.toFloat(),
                    Path.Direction.CW
                )
                ViewAnimationCompatUtils.RECT_BOTTOM -> path.addRect(
                    0f,
                    revealRadius,
                    width.toFloat(),
                    height.toFloat(),
                    Path.Direction.CW
                )
                ViewAnimationCompatUtils.RECT_RIGHT -> path.addRect(
                    revealRadius,
                    0f,
                    width.toFloat(),
                    height.toFloat(),
                    Path.Direction.CW
                )
            }

            canvas.clipPath(path)

            val isInvalided = super.drawChild(canvas, child, drawingTime)

            canvas.restoreToCount(state)

            return isInvalided
        }

        return super.drawChild(canvas, child, drawingTime)
    }


    class Builder(private val view: View) {
        //        private float centerX;
        //        private float centerY;
        private var startHeight: Float = 0.toFloat()
        private var endHeight: Float = 0.toFloat()
        private var nDirection = ViewAnimationCompatUtils.RECT_TOP


        fun Direction(nDirection: Int): Builder {
            this.nDirection = nDirection
            return this
        }

        fun startHeight(startHeight: Float): Builder {
            this.startHeight = startHeight
            return this
        }

        fun endHeight(endHeight: Float): Builder {
            this.endHeight = endHeight
            return this
        }

        private fun setParameter(layout: RectRevealLayout) {
            layout.setDirection(nDirection)
            layout.setStartHeight(startHeight)
            layout.setEndHeight(endHeight)
            layout.setChildView(view)

        }


        fun create(): Animator {

            if (view.parent != null && view.parent is RectRevealLayout) {

                val layout = view.parent as RectRevealLayout

                setParameter(layout)

                return layout.animator
            }

            val layout = RectRevealLayout(view.context)


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
