package com.ityzp.something.widgets.popouwindow

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.res.Resources
import android.graphics.Rect
import android.os.Handler
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/7/12.
 */
class MoreWindow(private val mContext: Activity) : PopupWindow(), View.OnClickListener {
    private var layout: RelativeLayout? = null
    private var close: ImageView? = null
    private var bgView: View? = null
//    private var blurringView: BlurringView? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var statusBarHeight: Int = 0
    private val mHandler = Handler()

    /**
     * 初始化
     *
     * @param view 要显示的模糊背景View,一般选择跟布局layout
     */
    fun init(view: View) {
        val frame = Rect()
        mContext.window.decorView.getWindowVisibleDisplayFrame(frame)
        statusBarHeight = frame.top
        val metrics = DisplayMetrics()
        mContext.windowManager.defaultDisplay
            .getMetrics(metrics)
        mWidth = metrics.widthPixels
        mHeight = metrics.heightPixels

        width = mWidth
        height = mHeight

        layout = LayoutInflater.from(mContext).inflate(R.layout.more_window, null) as RelativeLayout

        contentView = layout

        close = layout!!.findViewById<View>(R.id.iv_close) as ImageView
        close!!.setOnClickListener {
            if (isShowing) {
                closeAnimation()
            }
        }

//        blurringView = layout!!.findViewById<View>(R.id.blurring_view) as BlurringView
//        blurringView!!.blurredView(view)//模糊背景

        bgView = layout!!.findViewById(R.id.rel)
        isOutsideTouchable = true
        isFocusable = true
        isClippingEnabled = false//使popupwindow全屏显示
    }

    fun getNavigationBarHeight(activity: Activity): Int {
        val resources = activity.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        //获取NavigationBar的高度
        return resources.getDimensionPixelSize(resourceId)
    }

    /**
     * 显示window动画
     *
     * @param anchor
     */
    fun showMoreWindow(anchor: View) {

        showAtLocation(anchor, Gravity.TOP or Gravity.START, 0, 0)
        mHandler.post {
            try {
                //圆形扩展的动画
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    val x = mWidth / 2
                    val y = (mHeight - fromDpToPx(25f)).toInt()
                    val animator = ViewAnimationUtils.createCircularReveal(
                        bgView, x,
                        y, 0f, bgView!!.height.toFloat()
                    )
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            //                                bgView.setVisibility(View.VISIBLE);
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //							layout.setVisibility(View.VISIBLE);
                        }
                    })
                    animator.duration = 300
                    animator.start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        showAnimation(layout)

    }

    private fun showAnimation(layout: ViewGroup?) {
        try {
            val linearLayout = layout!!.findViewById<LinearLayout>(R.id.lin)
            mHandler.post {
                //＋ 旋转动画
                close!!.animate().rotation(90f).duration = 400
            }
            //菜单项弹出动画
            for (i in 0 until linearLayout.childCount) {
                val child = linearLayout.getChildAt(i)
                child.setOnClickListener(this)
                child.visibility = View.INVISIBLE
                mHandler.postDelayed({
                    child.visibility = View.VISIBLE
                    val fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600f, 0f) as ValueAnimator
                    fadeAnim.setDuration(200)
                    val kickAnimator = KickBackAnimator()
                    kickAnimator.setDuration(150f)
                    fadeAnim.setEvaluator(kickAnimator)
                    fadeAnim.start()
                }, (i * 50 + 100).toLong())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 关闭window动画
     */
    private fun closeAnimation() {
        mHandler.post { close!!.animate().rotation(-90f).duration = 400 }

        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                val x = mWidth / 2
                val y = (mHeight - fromDpToPx(25f)).toInt()
                val animator = ViewAnimationUtils.createCircularReveal(
                    bgView, x,
                    y, bgView!!.height.toFloat(), 0f
                )
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        //							layout.setVisibility(View.GONE);
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        //                        bgView.setVisibility(View.GONE);
                        dismiss()
                    }
                })
                animator.duration = 300
                animator.start()
            }
        } catch (e: Exception) {
        }

    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    override fun onClick(v: View) {

        if (isShowing) {
            closeAnimation()
        }

        when (v.id) {
            R.id.tv_sbs -> {
            }
            R.id.tv_search -> {
            }
            R.id.tv_course -> {
            }
            R.id.tv_task -> {
            }
        }

    }

    internal fun fromDpToPx(dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }
}
