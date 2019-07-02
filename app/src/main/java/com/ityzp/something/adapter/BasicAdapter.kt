package com.ityzp.something.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ityzp.something.R
import com.ityzp.something.view.activity.DrawerLayoutActivity
import com.ityzp.something.view.activity.SlideLinkActivity
import com.ityzp.something.view.activity.SpecisalBasicActivity

/**
 * Created by wangqiang on 2019/7/2.
 */
class BasicAdapter(var datas: ArrayList<String>, var mContext: Context) :
    RecyclerView.Adapter<BasicAdapter.MyViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        return MyViewholder(LayoutInflater.from(mContext).inflate(R.layout.adapter_basic, parent, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.tv_basic.setText(datas.get(position))
        holder.rl_item_basic.setOnClickListener {
            when (position) {
                0->{
                    val intent = Intent()
                    intent.setClass(mContext,DrawerLayoutActivity::class.java)
                    mContext.startActivity(intent)
                }
                1->{
                    val intent = Intent()
                    intent.setClass(mContext, SlideLinkActivity::class.java)
                    mContext.startActivity(intent)
                }
                2->{
                    val intent = Intent()
                    intent.setClass(mContext,SpecisalBasicActivity::class.java)
                    mContext.startActivity(intent)
                }
            }
        }
    }


    class MyViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rl_item_basic: RelativeLayout = itemView.findViewById(R.id.rl_item_basic)
        val tv_basic: TextView = itemView.findViewById(R.id.tv_basic)
    }
}