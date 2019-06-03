package com.ityzp.something.widgets.popouwindow

import android.content.Context
import android.os.Handler
import android.support.v4.widget.PopupWindowCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.ityzp.something.R
import java.util.*

/**
 * Created by wangqiang on 2019/5/31.
 */
class IndexPopupWindow(var context: Context) : PopupWindow() {
    private var list: ArrayList<String> = ArrayList()
    private var parent: View? = null
    internal var handler = Handler()

    init {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = layoutInflater.inflate(R.layout.popup_index, null)
        this.contentView = contentView
//        this.width = 300
        width = 400
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.isOutsideTouchable = true

        //点击同样的位置popupwindow消失
        this.setTouchInterceptor { v, event ->
            if (event.rawX > parent!!.x && event.rawX < parent!!.x + parent!!.measuredWidth) {
                parent!!.isEnabled = false
                handler.postDelayed(Runnable {
                    parent!!.isEnabled = true
                }, 200)
                isFocusable = true
            } else {
                isFocusable = false
            }

            return@setTouchInterceptor false
        }

        val ll_sao = contentView.findViewById<LinearLayout>(R.id.ll_sao)
        val ll_message = contentView.findViewById<LinearLayout>(R.id.ll_message)
        val ll_share = contentView.findViewById<LinearLayout>(R.id.ll_share)
        val ll_collection = contentView.findViewById<LinearLayout>(R.id.ll_collection)
        ll_sao.setOnClickListener {
            if (::setOnSaoListener.isInitialized) {
                setOnSaoListener.invoke()
            }
        }

        ll_message.setOnClickListener {
            if (::setOnMessageListener.isInitialized) {
                setOnMessageListener.invoke()
            }
        }

        ll_share.setOnClickListener {
            if (::setOnShareListener.isInitialized) {
                setOnShareListener.invoke()
            }
        }

        ll_collection.setOnClickListener {
            if (::setOnCollectionListener.isInitialized) {
                setOnCollectionListener.invoke()
            }
        }
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     * @param listData
     */
    fun showPopupWindow(parent: View, listData: ArrayList<String>) {
        this.parent = parent
        if (!this.isShowing) {
//            showAsDropDown(parent, Math.abs((parent.width-this.width)), 0) 该方法设置offsetX无效
            PopupWindowCompat.showAsDropDown(this, parent, -20, 0, Gravity.END)
            list.clear()
            for (i in listData.indices) {
                list.add(listData[i])
            }
        } else {
            this.dismiss()
        }
    }

    lateinit var setOnSaoListener: () -> Unit
    lateinit var setOnMessageListener: () -> Unit
    lateinit var setOnShareListener: () -> Unit
    lateinit var setOnCollectionListener: () -> Unit
}