package com.ityzp.something.widgets.slide

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ityzp.something.R
import com.ityzp.something.moudle.RightBean

/**
 * Created by wangqiang on 2019/7/3.
 */
class ClassifyDetailAdapter(context: Context, list: List<RightBean>, listener: RvListener) :
    RvAdapter<RightBean>(context, list, listener) {


    protected override fun getLayoutId(viewType: Int): Int {
        return if (viewType == 0) R.layout.item_title else R.layout.item_classify_detail
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].isTitle) 0 else 1
    }

    protected override fun getHolder(view: View, viewType: Int): RvHolder<RightBean> {
        return ClassifyHolder(view, viewType, listener)
    }

    inner class ClassifyHolder(itemView: View, type: Int, listener: RvListener) :
        RvHolder<RightBean>(itemView, type, listener) {
        internal lateinit var tvCity: TextView
        internal lateinit var avatar: ImageView
        internal lateinit var tvTitle: TextView

        init {
            when (type) {
                0 -> tvTitle = itemView.findViewById<View>(R.id.tv_title) as TextView
                1 -> {
                    tvCity = itemView.findViewById<View>(R.id.tvCity) as TextView
                    avatar = itemView.findViewById<View>(R.id.ivAvatar) as ImageView
                }
            }

        }

        override fun bindHolder(sortBean: RightBean, position: Int) {
            val itemViewType = this@ClassifyDetailAdapter.getItemViewType(position)
            when (itemViewType) {
                0 -> tvTitle.setText(sortBean.name)
                1 -> {
                    tvCity.setText(sortBean.name)
                    println("tvCity=" + sortBean.name)
                }
            }

        }
    }
}
