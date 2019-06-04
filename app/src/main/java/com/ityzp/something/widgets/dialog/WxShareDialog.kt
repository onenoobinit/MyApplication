package com.ityzp.something.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.baseklibrary.base.BaseActivity
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/6/4.
 */
class WxShareDialog(context: Context) : Dialog(context, R.style.Sweet_Alert_Dialog) {
    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_share, null)
        setCanceledOnTouchOutside(true)
        setContentView(inflate)
        val window = window
        window.setGravity(Gravity.BOTTOM)
        window.setWindowAnimations(R.style.dialogStyle)
        val windowManager = (context as BaseActivity).windowManager
        val defaultDisplay = windowManager.defaultDisplay
        val lp = window.attributes
        lp.width = defaultDisplay.width
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window.attributes = lp

        val ll_wx_in = inflate.findViewById<LinearLayout>(R.id.ll_wx_in)
        val ll_wx_friend = inflate.findViewById<LinearLayout>(R.id.ll_wx_friend)
        val tv_wx_cancle = inflate.findViewById<TextView>(R.id.tv_wx_cancle)

        ll_wx_in.setOnClickListener {
            if (::setOnWxListener.isInitialized) {
                setOnWxListener.invoke()
            }
        }

        ll_wx_friend.setOnClickListener {
            if (::setOnFriendListener.isInitialized) {
                setOnFriendListener.invoke()
            }
        }

        tv_wx_cancle.setOnClickListener {
            dismiss()
        }
    }

    lateinit var setOnWxListener: () -> Unit
    lateinit var setOnFriendListener: () -> Unit
}