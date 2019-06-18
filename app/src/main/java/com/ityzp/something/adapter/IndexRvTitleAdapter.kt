package com.ityzp.something.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/5/31.
 */
class IndexRvTitleAdapter(var context: Context, var datas: ArrayList<String>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<IndexRvTitleAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_index_title, p0, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        if (p1 == 0) {
            p0.v_left.visibility = View.GONE
        } else {
            p0.v_left.visibility = View.VISIBLE
        }
        p0.tv_index_rv_title.setText(datas.get(p1))

        p0.rl_item.setOnClickListener {
            if (::setOnItemListener.isInitialized) {
                setOnItemListener.invoke(p1)
            }
        }
    }


    class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var v_left = itemView.findViewById<View>(R.id.v_left)
        var tv_index_rv_title = itemView.findViewById<TextView>(R.id.tv_index_rv_title)
        var rl_item = itemView.findViewById<RelativeLayout>(R.id.rl_item)
    }

    lateinit var setOnItemListener: (postion: Int) -> Unit
}