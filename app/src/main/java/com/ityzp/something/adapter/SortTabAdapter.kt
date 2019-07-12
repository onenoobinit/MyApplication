package com.ityzp.something.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ityzp.something.view.fragment.MsgContentFragment

/**
 * Created by wangqiang on 2019/7/12.
 */
class SortTabAdapter(fm: FragmentManager, private val names:List<String>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return names.size
    }

//    /**
//     * 数据列表
//     *
//     * @param datas
//     */
//    fun setList(datas: List<String>) {
//        this.names.clear()
//        this.names.addAll(datas)
//        names
//        notifyDataSetChanged()
//    }

    override fun getItem(position: Int): Fragment {
        val fragment = MsgContentFragment()
        val bundle = Bundle()
        bundle.putString("name", names[position])
        fragment.setArguments(bundle)
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        var plateName: String? = names[position]
        if (plateName == null) {
            plateName = ""
        } else if (plateName.length > 15) {
            plateName = plateName.substring(0, 15) + "..."
        }
        return plateName
    }
}