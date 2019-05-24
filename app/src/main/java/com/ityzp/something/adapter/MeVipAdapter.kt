package com.ityzp.something.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/5/24.
 */
class MeVipAdapter(var datas: List<String>, var context: Context) : RecyclerView.Adapter<MeVipAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MeVipAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_me_vip, p0, false))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(p0: MeVipAdapter.MyViewHolder, p1: Int) {
        when (p1) {
            0 -> {
                p0.iv_me_vip.setImageResource(R.mipmap.ic_me_vip)
                p0.tv_me_vip_type.setText("我的vip会员")
                p0.tv_me_vip_content.setText("1张代金券即将过期！")
            }
            1 -> {
                p0.iv_me_vip.setImageResource(R.mipmap.ic_vip1)
                p0.tv_me_vip_type.setText("开通直播会员")
                p0.tv_me_vip_content.setText("4k画面清晰来袭")
            }
            2 -> {
                p0.iv_me_vip.setImageResource(R.mipmap.ic_vip2)
                p0.tv_me_vip_type.setText("开通游戏会员")
                p0.tv_me_vip_content.setText("免费畅玩各种游戏")
            }
        }
        p0.rl_vip_item.setOnClickListener { ToastUtil.show(context, "功能开发中！") }
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var rl_vip_item: RelativeLayout = itemView!!.findViewById(R.id.rl_vip_item)
        var iv_me_vip: ImageView = itemView!!.findViewById(R.id.iv_me_vip)
        var tv_me_vip_type: TextView = itemView!!.findViewById(R.id.tv_me_vip_type)
        var tv_me_vip_content: TextView = itemView!!.findViewById(R.id.tv_me_vip_content)
    }
}