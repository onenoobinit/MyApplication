package com.ityzp.something.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import com.example.baseklibrary.utils.DensityUtil
import com.ityzp.something.R
import java.util.*

/**
 * 循环消息
 * Created by wangqiang on 2019/6/5.
 */
class MarqueeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewFlipper(context, attrs) {

    private var mContext: Context? = null
    var notices: List<String>? = null
    private var isSetAnimDuration = false
    private var interval = 4000
    private var animDuration = 800
    private var textSize = 14
    private var textColor = -0x1

    private var singleLine = false
    private var gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL

    val position: Int
        get() = currentView.tag as Int

    init {
        init(context, attrs, 0)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        this.mContext = context
        if (notices == null) {
            notices = ArrayList<String>()
        }

        val typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0)
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval)
        isSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration)
        singleLine = typedArray.getBoolean(R.styleable.MarqueeViewStyle_mvSingleLine, false)
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration)
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize.toFloat()).toInt()
            textSize = DensityUtil.px2sp(mContext!!, textSize).toInt()
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor)
        val gravityType = typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity, TEXT_GRAVITY_LEFT)
        when (gravityType) {
            TEXT_GRAVITY_CENTER -> gravity = Gravity.CENTER
            TEXT_GRAVITY_RIGHT -> gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        }
        typedArray.recycle()

        setFlipInterval(interval)
        val animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in)
        if (isSetAnimDuration) {
            animIn.duration = animDuration.toLong()
        }
        inAnimation = animIn
        val animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out)
        if (isSetAnimDuration) {
            animOut.duration = animDuration.toLong()
        }
        outAnimation = animOut
    }

    /* // 根据公告字符串启动轮播
    public void startWithText(final String notice) {
        if (TextUtils.isEmpty(notice)) return;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                startWithFixedWidth(notice, getWidth());
            }
        });
    }*/

    // 根据公告字符串列表启动轮播
    fun startWithList(notices: List<String>, position: Int) {
        this.notices = notices
        start(position)
    }

    /* // 根据宽度和公告字符串启动轮播
     private void startWithFixedWidth(String notice, int width) {
         int noticeLength = notice.length();
         int dpW = (int) DensityUtil.px2dp(mContext, width);
         int limit = dpW / textSize;
         if (dpW == 0) {
             throw new RuntimeException("Please set MarqueeView width !");
         }

         if (noticeLength <= limit) {
             notices.add(notice);
         } else {
             int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
             for (int i = 0; i < size; i++) {
                 int startIndex = i * limit;
                 int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
                 notices.add(notice.substring(startIndex, endIndex));
             }
         }
         start();
     }*/

    // 启动轮播
    fun start(position: Int): Boolean {
        removeAllViews()
        if (notices == null || notices!!.size == 0) {
            return false
        }

        for (i in notices!!.indices) {

            addView(createView(i))
        }

        if (notices!!.size > 1) {
            displayedChild = position
            startFlipping()
        }
        return true
    }

    // 创建ViewFlipper下的TextView
    private fun createView(position: Int): View {
        val inflate = LayoutInflater.from(mContext).inflate(R.layout.home_marquee, null)
        val tv_content = inflate.findViewById<TextView>(R.id.tv_content)
        val tv_red = inflate.findViewById<TextView>(R.id.tv_red)
        val tv_new = inflate.findViewById<TextView>(R.id.tv_new)
        val rl_mq = inflate.findViewById<LinearLayout>(R.id.rl_mq)
        tv_content.setText(notices!![position])
        tv_new.setVisibility(View.VISIBLE)

        rl_mq.setOnClickListener {
            if (::setOnItemListener.isInitialized) {
                setOnItemListener.invoke(position)
            }
        }

        return inflate
    }

    lateinit var setOnItemListener: (position: Int) -> Unit

    companion object {
        private val TEXT_GRAVITY_LEFT = 0
        private val TEXT_GRAVITY_CENTER = 1
        private val TEXT_GRAVITY_RIGHT = 2
    }

}