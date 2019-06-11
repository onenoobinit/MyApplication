package com.ityzp.something.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.ityzp.something.R
import com.ityzp.something.moudle.MessageInfo

/**
 * Created by wangqiang on 2019/6/6.
 */
class MessageAdapter(var context: Context, var datas: ArrayList<MessageInfo>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return datas.get(position).viewType
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        if (p1 == 1) {
            return GrideHolder(LayoutInflater.from(context).inflate(R.layout.adapter_item_top, p0, false))
        } else {
            return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_message, p0, false))
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        if (datas.get(p1).viewType == 0) {
            val myViewHolder = p0 as MyViewHolder
            myViewHolder.tv_message_title.setText(datas.get(p1).title)
            myViewHolder.tv_message_content.setText(datas.get(p1).content)
            myViewHolder.tv_message_time.setText(datas.get(p1).time)
            myViewHolder.ll_message_item.setOnClickListener {
                if (::setOnItemListener.isInitialized) {
                    setOnItemListener.invoke(datas.get(p1).title!!)
                }
            }
        } else {
            val grideHolder = p0 as GrideHolder
            if (p1 == 0) {
                grideHolder.iv_message_top.setImageResource(R.mipmap.ic_new_message)
                grideHolder.tv_message_top.setText("最新消息")
            } else {
                grideHolder.iv_message_top.setImageResource(R.mipmap.ic_out_message)
                grideHolder.tv_message_top.setText("两周前的消息")
            }
        }
    }

    class GrideHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_message_top = itemView.findViewById<TextView>(R.id.tv_message_top)
        val iv_message_top = itemView.findViewById<ImageView>(R.id.iv_message_top)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_message_title = itemView.findViewById<TextView>(R.id.tv_message_title)
        val tv_message_content = itemView.findViewById<TextView>(R.id.tv_message_content)
        val tv_message_time = itemView.findViewById<TextView>(R.id.tv_message_time)
        val iv_message = itemView.findViewById<ImageView>(R.id.iv_message)
        val ll_message_item = itemView.findViewById<LinearLayout>(R.id.ll_message_item)
    }

    lateinit var setOnItemListener: (content: String) -> Unit
}