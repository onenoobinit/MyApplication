package com.ityzp.something.widgets.slide

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/7/3.
 */
class SortAdapter(context: Context, list: List<String>, listener: RvListener) :
    RvAdapter<String>(context, list, listener) {

    private var checkedPosition: Int = 0

    fun setCheckedPosition(checkedPosition: Int) {
        this.checkedPosition = checkedPosition
        notifyDataSetChanged()
    }

    protected override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_sort_list
    }

    protected override fun getHolder(view: View, viewType: Int): RvHolder<String> {
        return SortHolder(view, viewType, listener)
    }

    private inner class SortHolder internal constructor(private val mView: View, type: Int, listener: RvListener) :
        RvHolder<String>(mView, type, listener) {

        private val tvName: TextView

        init {
            tvName = mView.findViewById<View>(R.id.tv_sort) as TextView
        }

        override fun bindHolder(string: String, position: Int) {
            tvName.text = string
            if (position == checkedPosition) {
                mView.setBackgroundColor(Color.parseColor("#f3f3f3"))
                tvName.setTextColor(Color.parseColor("#5ACDA5"))
            } else {
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                tvName.setTextColor(Color.parseColor("#1e1d1d"))
            }
        }

    }
}
