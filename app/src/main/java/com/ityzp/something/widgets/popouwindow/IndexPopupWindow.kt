package com.ityzp.something.widgets.popouwindow

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.ityzp.something.R
import java.util.*

/**
 * Created by wangqiang on 2019/5/31.
 */
class IndexPopupWindow(var context: Context) : PopupWindow() {
    private var list: ArrayList<String> = ArrayList()
    private var parent: View? = null

    init {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val contentView = layoutInflater.inflate(R.layout.popup_index, null)
        this.contentView = contentView
        this.width = 300
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.isOutsideTouchable = true
        val dw = ColorDrawable(Color.parseColor("#80000000"))
        this.setBackgroundDrawable(dw)

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
            // 以下拉方式显示popupwindow
            //            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
            this.showAsDropDown(parent, 0, 20)
            list.clear()
            for (i in listData.indices) {
                list.add(listData[i])
            }
        } else {
            this.dismiss()
        }

    }
}