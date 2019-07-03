package com.ityzp.something.widgets.slide

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by wangqiang on 2019/7/3.
 */
abstract class RvHolder<T>(itemView: View, type: Int, protected var mListener: RvListener) :
    RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener { v -> mListener.onItemClick(v.id, getAdapterPosition()) }
    }

    abstract fun bindHolder(t: T, position: Int)

}