package com.ityzp.something.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ityzp.something.utils.FragmentFactory

/**
 * Created by wangqiang on 2019/7/12.
 */
class SortTabAdapter(fm: FragmentManager, private val names:List<String>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return names.size
    }

    override fun getItem(position: Int): Fragment? {
        val fragment = FragmentFactory.createdById(position)
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