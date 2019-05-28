package com.ityzp.something.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wangqiang on 2019/5/28.
 */
class PickTimeView : LinearLayout, NumberPicker.OnValueChangeListener {
    private val TAG = javaClass.simpleName
    private var mContext: Context? = null
    /**
     * 视图控件
     */
    //    private NumberPicker mNpLeft, mNpMiddle, mNpRight;
    private var mNpLeft: MyNumberPicker? = null
    private var mNpMiddle: MyNumberPicker? = null
    private var mNpRight: MyNumberPicker? = null
    private var mMinuteTip: TextView? = null
    /**
     * 选择框之间距离（dp）
     */
    private val mOffsetMargin = 20

    /**
     * 一组数据长度
     */
    private val DATA_SIZE = 5
    /**
     * 当前type值
     */
    private var mCurrentType = TYPE_PICK_TIME
    /**
     * 当前时间戳
     */
    private var mTimeMillis: Long = 0
    /**
     * 选中回调监听
     */
    private var mOnSelectedChangeListener: onSelectedChangeListener? = null

    private var test: SimpleDateFormat? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
        generateView()
        initPicker()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        generateView()
        initPicker()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        this.mContext = context
        generateView()
        initPicker()
    }

    /**
     * 绘制视图
     */
    private fun generateView() {
        this.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.orientation = LinearLayout.VERTICAL
        this.gravity = Gravity.CENTER
        val container = LinearLayout(mContext)
        container.orientation = LinearLayout.HORIZONTAL
        container.layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        container.gravity = Gravity.CENTER
        //        mNpLeft = new NumberPicker(mContext);
        //        mNpMiddle = new NumberPicker(mContext);
        //        mNpRight = new NumberPicker(mContext);
        mNpLeft = MyNumberPicker(mContext!!)
        mNpMiddle = MyNumberPicker(mContext!!)
        mNpRight = MyNumberPicker(mContext!!)
        mMinuteTip = TextView(mContext!!)
        mMinuteTip!!.text = ":"
        mNpLeft!!.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS)
        mNpMiddle!!.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS)
        mNpRight!!.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS)

        //        mNpLeft.setTextColor(Color.BLUE);
        //        mNpMiddle.setTextSize(28);
        //        mNpRight.setDividerColor(Color.GREEN);
        /**
         * 设置宽高和边距
         */
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 0, 0, 0)
        mNpLeft!!.setLayoutParams(params)
        params.setMargins(dip2px(mOffsetMargin), 0, 0, 0)
        mNpMiddle!!.setLayoutParams(params)
        mNpRight!!.setLayoutParams(params)
        mMinuteTip!!.layoutParams = params

        mNpLeft!!.setOnValueChangedListener(this)
        mNpMiddle!!.setOnValueChangedListener(this)
        mNpRight!!.setOnValueChangedListener(this)

        container.addView(mNpLeft)
        container.addView(mNpMiddle)
        container.addView(mMinuteTip)
        container.addView(mNpRight)
        this.addView(container)
        initTime()
    }

    /**
     * 初始化控件
     */
    private fun initPicker() {
        test = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        if (mCurrentType == TYPE_PICK_DATE) {
            mMinuteTip!!.visibility = View.GONE
        } else {
            mMinuteTip!!.visibility = View.VISIBLE
        }

        mNpLeft!!.setMinValue(0)
        mNpLeft!!.setMaxValue(DATA_SIZE - 1)
        updateLeftValue(mTimeMillis)

        mNpMiddle!!.setMinValue(0)
        mNpMiddle!!.setMaxValue(DATA_SIZE - 1)
        updateMiddleValue(mTimeMillis)

        mNpRight!!.setMinValue(0)
        mNpRight!!.setMaxValue(DATA_SIZE - 1)
        updateRightValue(mTimeMillis)
    }

    /**
     * 更新左侧控件
     * 日期：选择年控件
     * 时间：选择月份和日期控件
     *
     * @param timeMillis
     */
    private fun updateLeftValue(timeMillis: Long) {
        val sdf: SimpleDateFormat
        val str = arrayOfNulls<String>(DATA_SIZE)
        if (mCurrentType == TYPE_PICK_DATE) {
            sdf = SimpleDateFormat("yyyy")
            for (i in 0 until DATA_SIZE) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = timeMillis
                cal.add(Calendar.YEAR, i - DATA_SIZE / 2)
                str[i] = sdf.format(cal.timeInMillis)
            }
        } else {
            sdf = SimpleDateFormat("MM-dd EEE")
            for (i in 0 until DATA_SIZE) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = timeMillis
                cal.add(Calendar.DAY_OF_MONTH, i - DATA_SIZE / 2)
                str[i] = sdf.format(cal.timeInMillis)
            }
        }
        mNpLeft!!.setDisplayedValues(str)
        mNpLeft!!.setValue(DATA_SIZE / 2)
        mNpLeft!!.postInvalidate()
    }

    /**
     * 更新中间控件
     * 日期：选择月份控件
     * 时间：选择小时控件
     *
     * @param timeMillis
     */
    private fun updateMiddleValue(timeMillis: Long) {
        val sdf: SimpleDateFormat
        val str = arrayOfNulls<String>(DATA_SIZE)
        if (mCurrentType == TYPE_PICK_DATE) {
            sdf = SimpleDateFormat("MM")
            for (i in 0 until DATA_SIZE) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = timeMillis
                cal.add(Calendar.MONTH, i - DATA_SIZE / 2)
                str[i] = sdf.format(cal.timeInMillis)
            }
        } else {
            sdf = SimpleDateFormat("HH")
            for (i in 0 until DATA_SIZE) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = timeMillis
                cal.add(Calendar.HOUR_OF_DAY, i - DATA_SIZE / 2)
                str[i] = sdf.format(cal.timeInMillis)
            }
        }
        mNpMiddle!!.setDisplayedValues(str)
        mNpMiddle!!.setValue(DATA_SIZE / 2)
        mNpMiddle!!.postInvalidate()
    }

    /**
     * 更新右侧控件
     * 日期：选择日期控件
     * 时间：选择分钟控件
     *
     * @param timeMillis
     */
    private fun updateRightValue(timeMillis: Long) {
        val sdf: SimpleDateFormat
        val str = arrayOfNulls<String>(DATA_SIZE)
        if (mCurrentType == TYPE_PICK_DATE) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timeMillis
            val days = getMaxDayOfMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1)
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
            for (i in 0 until DATA_SIZE) {
                var temp = currentDay - (DATA_SIZE / 2 - i)
                if (temp > days) {
                    temp -= days
                }
                if (temp < 1) {
                    temp += days
                }
                str[i] = if (temp > 9) temp.toString() + "" else "0$temp"
            }
        } else {
            sdf = SimpleDateFormat("mm")
            for (i in 0 until DATA_SIZE) {
                val cal = Calendar.getInstance()
                cal.timeInMillis = timeMillis
                cal.add(Calendar.MINUTE, i - DATA_SIZE / 2)
                str[i] = sdf.format(cal.timeInMillis)
            }
        }
        mNpRight!!.setDisplayedValues(str)
        mNpRight!!.setValue(DATA_SIZE / 2)
        mNpRight!!.postInvalidate()
    }

    override fun onValueChange(picker: NumberPicker, oldVal: Int, newVal: Int) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = mTimeMillis
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val offset = newVal - oldVal
        if (picker === mNpLeft) {
            if (mCurrentType == TYPE_PICK_DATE) {
                calendar.add(Calendar.YEAR, offset)
            } else {
                calendar.add(Calendar.DAY_OF_MONTH, offset)
            }
            updateLeftValue(calendar.timeInMillis)
            mTimeMillis = calendar.timeInMillis
        } else if (picker === mNpMiddle) {
            if (mCurrentType == TYPE_PICK_DATE) {
                calendar.add(Calendar.MONTH, offset)
                if (calendar.get(Calendar.YEAR) != year) {
                    calendar.set(Calendar.YEAR, year)
                }
            } else {
                calendar.add(Calendar.HOUR_OF_DAY, offset)
                if (calendar.get(Calendar.DAY_OF_MONTH) != day) {
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                }
                if (calendar.get(Calendar.MONTH) != month) {
                    calendar.set(Calendar.MONTH, month)
                }
                if (calendar.get(Calendar.YEAR) != year) {
                    calendar.set(Calendar.YEAR, year)
                }
            }
            updateMiddleValue(calendar.timeInMillis)
            updateRightValue(calendar.timeInMillis)
            mTimeMillis = calendar.timeInMillis
        } else if (picker === mNpRight) {
            if (mCurrentType == TYPE_PICK_DATE) {
                val days = getMaxDayOfMonth(year, month + 1)
                if (day == 1 && offset < 0) {
                    calendar.set(Calendar.DAY_OF_MONTH, days)
                } else if (day == days && offset > 0) {
                    calendar.set(Calendar.DAY_OF_MONTH, 1)
                } else {
                    calendar.add(Calendar.DAY_OF_MONTH, offset)
                }

                if (calendar.get(Calendar.MONTH) != month) {
                    calendar.set(Calendar.MONTH, month)
                }
                if (calendar.get(Calendar.YEAR) != year) {
                    calendar.set(Calendar.YEAR, year)
                }
                Log.e(TAG, "time：：：" + test!!.format(calendar.timeInMillis))
            } else {
                calendar.add(Calendar.MINUTE, offset)
                if (calendar.get(Calendar.HOUR_OF_DAY) != hour) {
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                }
                if (calendar.get(Calendar.DAY_OF_MONTH) != day) {
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                }
                if (calendar.get(Calendar.MONTH) != month) {
                    calendar.set(Calendar.MONTH, month)
                }
                if (calendar.get(Calendar.YEAR) != year) {
                    calendar.set(Calendar.YEAR, year)
                }
            }
            updateRightValue(calendar.timeInMillis)
            mTimeMillis = calendar.timeInMillis
        }
        /**
         * 向外部发送当前选中时间
         */
        if (mOnSelectedChangeListener != null) {
            mOnSelectedChangeListener!!.onSelected(this, mTimeMillis)
        }
        Log.e(TAG, "selected time:" + test!!.format(mTimeMillis))
    }

    /**
     * 未设置时间默认取当前时间
     */
    private fun initTime() {
        val calendar = Calendar.getInstance()
        mTimeMillis = calendar.timeInMillis
    }

    /**
     * 设置初始化时间
     *
     * @param timeMillis
     */
    fun setTimeMillis(timeMillis: Long) {
        if (timeMillis != 0L) {
            this.mTimeMillis = timeMillis
            initPicker()
            this.postInvalidate()
        } else {
            initTime()
        }
    }

    /**
     * 设置视图类型 年月日控件or时间控件
     *
     * @param type
     */
    fun setViewType(type: Int) {
        if (type == TYPE_PICK_DATE || type == TYPE_PICK_TIME) {
            this.mCurrentType = type
        } else {
            this.mCurrentType = TYPE_PICK_TIME
        }
        initPicker()
    }

    /**
     * 设置选中监听回调
     *
     * @param listener
     */
    fun setOnSelectedChangeListener(listener: onSelectedChangeListener) {
        this.mOnSelectedChangeListener = listener
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    private fun dip2px(dp: Int): Int {
        val scale = mContext!!.resources.displayMetrics.density
        return (scale * dp + 0.5f).toInt()
    }

    /**
     * 获取某年某月的天数
     *
     * @param year
     * @param month
     * @return
     */
    private fun getMaxDayOfMonth(year: Int, month: Int): Int {
        var result = 0
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            result = 31
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            result = 30
        } else {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                result = 29
            } else {
                result = 28
            }
        }
        return result
    }

    /**
     * 选中回调接口
     */
    interface onSelectedChangeListener {
        fun onSelected(view: PickTimeView, timeMillis: Long)
    }

    companion object {

        /**
         * type：选择日期控件
         */
        val TYPE_PICK_DATE = 1
        /**
         * type：选择时间控件
         */
        val TYPE_PICK_TIME = 2
    }
}
