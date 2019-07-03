package com.ityzp.something.widgets.slide

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by wangqiang on 2019/7/3.
 */
abstract class RvAdapter<T>(
    protected var mContext: Context,
    protected var list: List<T>,
    protected var listener: RvListener
) : RecyclerView.Adapter<RvHolder<T>>() {
    protected var mInflater: LayoutInflater
    private val mRecyclerView: RecyclerView? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder<T> {
        val view = mInflater.inflate(getLayoutId(viewType), parent, false)
        return getHolder(view, viewType)
    }

    protected abstract fun getLayoutId(viewType: Int): Int

    override fun onBindViewHolder(holder: RvHolder<T>, position: Int) {
        holder.bindHolder(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    protected abstract fun getHolder(view: View, viewType: Int): RvHolder<T>

}