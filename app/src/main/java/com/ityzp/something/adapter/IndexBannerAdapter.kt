package com.ityzp.something.adapter

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.baseklibrary.utils.L
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/5/31.
 */
class IndexBannerAdapter(var context: Context, var datas: ArrayList<Int>) : androidx.viewpager.widget.PagerAdapter() {
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 == p1
    }

    override fun getCount(): Int {
        return datas.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_index_banner, container, false)
        val iv_pic = view.findViewById<ImageView>(R.id.iv_index_banner)
        L.i("AAAA",datas.get(position).toString())
        iv_pic.setImageResource(datas.get(position))
        container.addView(view)
        return view
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}