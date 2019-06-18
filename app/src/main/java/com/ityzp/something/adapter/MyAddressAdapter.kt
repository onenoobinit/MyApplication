package com.ityzp.something.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/5/30.
 */
class MyAddressAdapter(var datas: ArrayList<String>, var context: Context) :
    androidx.recyclerview.widget.RecyclerView.Adapter<MyAddressAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_my_address, p0, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        if (p1 == 0) {
            p0.rl_address_left.setBackgroundResource(R.drawable.bg_address_normal)
            p0.tv_address_nick.visibility = TextView.GONE
            p0.iv_address.visibility = ImageView.VISIBLE
            p0.tv_address_normal.visibility = TextView.VISIBLE
            p0.tv_address_type.visibility = TextView.VISIBLE

        } else {
            p0.rl_address_left.setBackgroundResource(R.drawable.bg_address_other)
            p0.tv_address_nick.visibility = TextView.VISIBLE
            p0.iv_address.visibility = TextView.GONE
            p0.tv_address_normal.visibility = TextView.GONE
            p0.tv_address_type.visibility = TextView.GONE

        }
    }

    class MyViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val ll_myaddress_item: LinearLayout = itemView.findViewById(R.id.ll_myaddress_item)
        val rl_address_left: RelativeLayout = itemView.findViewById(R.id.rl_address_left)
        val iv_address: ImageView = itemView.findViewById(R.id.iv_address)
        val tv_address_nick: TextView = itemView.findViewById(R.id.tv_address_nick)
        val tv_address_name: TextView = itemView.findViewById(R.id.tv_address_name)
        val tv_address_phone: TextView = itemView.findViewById(R.id.tv_address_phone)
        val tv_address_normal: TextView = itemView.findViewById(R.id.tv_address_normal)
        val tv_address_type: TextView = itemView.findViewById(R.id.tv_address_type)
        val tv_address: TextView = itemView.findViewById(R.id.tv_address)
        val tv_address_detail: TextView = itemView.findViewById(R.id.tv_address_detail)
        val tv_address_edit: TextView = itemView.findViewById(R.id.tv_address_edit)
    }
}