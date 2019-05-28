package com.ityzp.something.widgets

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.NumberPicker

/**
 * Created by wangqiang on 2019/5/28.
 */
class MyNumberPicker : NumberPicker {
    private var mContext: Context? = null
    internal var picker: NumberPicker

    private var mRight: Int = 0
    private var mLeft: Int = 0
    private var mBottom: Int = 0
    private var mTopSelectionDividerTop: Int = 0
    private var mBottomSelectionDividerBottom: Int = 0
    private var mSelectorIndices: IntArray? = null
    private var mScrollState: Int = 0
    private var mSelectorIndexToStringCache: SparseArray<String>? = null
    private var mInputText: EditText? = null
    private var mSelectorWheelPaint: Paint? = null
    private var mSelectorElementHeight: Int = 0
    private var mCurrentScrollOffset: Int = 0
    private var mHasSelectorWheel: Boolean = false
    private var mHideWheelUntilFocused: Boolean = false
    private var mSelectionDivider: Drawable? = null

    constructor(context: Context) : super(context) {
        picker = this
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        picker = this
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        picker = this
        mContext = context
    }

    override fun addView(child: View) {
        super.addView(child)
        updateView(child)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        updateView(child)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        super.addView(child, params)
        updateView(child)
    }

    private fun updateView(view: View) {
        if (view is EditText) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            view.setTextColor(Color.parseColor("#6495ED"))
        }
    }

    /**
     * 通过反射获取值
     */
    private fun getMyValue() {
        mLeft = super.getLeft()
        mRight = super.getRight()
        mBottom = super.getBottom()
        val pickerFields = NumberPicker::class.java.declaredFields
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mSelectorWheelPaint") {
                try {
                    mSelectorWheelPaint = field.get(picker) as Paint
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mSelectorElementHeight") {
                try {
                    mSelectorElementHeight = field.get(picker) as Int
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mCurrentScrollOffset") {
                try {
                    mCurrentScrollOffset = field.get(picker) as Int
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mInputText") {
                try {
                    mInputText = field.get(picker) as EditText
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mSelectorIndexToStringCache") {
                try {
                    mSelectorIndexToStringCache = field.get(picker) as SparseArray<String>
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mSelectorIndices") {
                try {
                    mSelectorIndices = field.get(picker) as IntArray
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mHasSelectorWheel") {
                try {
                    mHasSelectorWheel = field.get(picker) as Boolean
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mHideWheelUntilFocused") {
                try {
                    mHideWheelUntilFocused = field.get(picker) as Boolean
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        //        for (Field field : pickerFields) {
        //            field.setAccessible(true);
        //            if (field.getName().equals("mDecrementVirtualButtonPressed")) {
        //                try {
        //                    mDecrementVirtualButtonPressed = (boolean) field.get(picker);
        //                } catch (IllegalAccessException e) {
        //                    e.printStackTrace();
        //                }
        //                break;
        //            }
        //        }
        //        for (Field field : pickerFields) {
        //            field.setAccessible(true);
        //            if (field.getName().equals("mIncrementVirtualButtonPressed")) {
        //                try {
        //                    mIncrementVirtualButtonPressed = (boolean) field.get(picker);
        //                } catch (IllegalAccessException e) {
        //                    e.printStackTrace();
        //                }
        //                break;
        //            }
        //        }
        //        for (Field field : pickerFields) {
        //            field.setAccessible(true);
        //            if (field.getName().equals("mVirtualButtonPressedDrawable")) {
        //                try {
        //                    mVirtualButtonPressedDrawable = (Drawable) field.get(picker);
        //                } catch (IllegalAccessException e) {
        //                    e.printStackTrace();
        //                }
        //                break;
        //            }
        //        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mScrollState") {
                try {
                    mScrollState = field.get(picker) as Int
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mTopSelectionDividerTop") {
                try {
                    mTopSelectionDividerTop = field.get(picker) as Int
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mBottomSelectionDividerBottom") {
                try {
                    mBottomSelectionDividerBottom = field.get(picker) as Int
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }
        for (field in pickerFields) {
            field.isAccessible = true
            if (field.name == "mSelectionDivider") {
                try {
                    mSelectionDivider = field.get(picker) as Drawable
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }

                break
            }
        }

    }

    override fun onDraw(canvas: Canvas) {
        //        super.onDraw(canvas);
        getMyValue()
        mSelectorWheelPaint!!.color = Color.BLUE

        if (!mHasSelectorWheel) {
            super.onDraw(canvas)
            return
        }
        val showSelectorWheel = if (mHideWheelUntilFocused) hasFocus() else true
        val x = ((mRight - mLeft) / 2).toFloat()
        var y = mCurrentScrollOffset.toFloat()
        val selectorIndices = mSelectorIndices
        for (i in selectorIndices!!.indices) {
            val selectorIndex = selectorIndices[i]
            val scrollSelectorValue = mSelectorIndexToStringCache!!.get(selectorIndex)
            if (i != 1) {
                mSelectorWheelPaint!!.color = Color.BLACK
                mSelectorWheelPaint!!.textSize = sp2px(16).toFloat()
            } else {
                mSelectorWheelPaint!!.color = Color.parseColor("#6495ED")
                mSelectorWheelPaint!!.textSize = sp2px(20).toFloat()
            }

            if (showSelectorWheel && i != 1 || i == 1 && mInputText!!.visibility != View.VISIBLE) {
                val mRect = Rect()
                mSelectorWheelPaint!!.getTextBounds(scrollSelectorValue, 0, scrollSelectorValue.length, mRect)
                canvas.drawText(scrollSelectorValue, x, y, mSelectorWheelPaint!!)
            }
            y += mSelectorElementHeight.toFloat()
        }

        // draw the selection dividers
        if (showSelectorWheel && mSelectionDivider != null) {
            mSelectionDivider = ColorDrawable(Color.parseColor("#a0c4c4c4"))
            // draw the top divider
            val topOfTopDivider = mTopSelectionDividerTop
            val bottomOfTopDivider = topOfTopDivider + 2
            mSelectionDivider!!.setBounds(0, topOfTopDivider, mRight, bottomOfTopDivider)
            mSelectionDivider!!.draw(canvas)

            // draw the bottom divider
            val bottomOfBottomDivider = mBottomSelectionDividerBottom
            val topOfBottomDivider = bottomOfBottomDivider - 2
            mSelectionDivider!!.setBounds(0, topOfBottomDivider, mRight, bottomOfBottomDivider)
            mSelectionDivider!!.draw(canvas)
        }
    }

    private fun sp2px(sp: Int): Int {
        val scale = mContext!!.resources.displayMetrics.scaledDensity
        return (scale * sp + 0.5f).toInt()
    }
}
