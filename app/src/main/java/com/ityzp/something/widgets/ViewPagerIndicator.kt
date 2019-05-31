package com.ityzp.something.widgets

import android.content.Context
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.ityzp.something.R
import java.util.*

/**
 * Created by wangqiang on 2019/5/31.
 */
class ViewPagerIndicator(
    private val context: Context,
    private val viewPager: ViewPager,
    private val dotLayout: LinearLayout,
    private val size: Int
) :
    ViewPager.OnPageChangeListener {
    private val img1 = R.mipmap.sy_spgg_dh_xz_icon
    private val img2 = R.mipmap.sy_spgg_dh_wxz_icon
    private val imgSize = 15
    private val dotViewLists = ArrayList<ImageView>()

    init {

        for (i in 0 until size) {
            val imageView = ImageView(context)
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )

            //为小圆点左右添加间距
            params.leftMargin = 13
            params.rightMargin = 13
            //手动给小圆点一个大小
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT
            if (i == 0) {
                imageView.setBackgroundResource(img1)
            } else {
                imageView.setBackgroundResource(img2)
            }
            //为LinearLayout添加ImageView
            dotLayout.addView(imageView, params)
            dotViewLists.add(imageView)
        }

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        for (i in 0 until size) {
            //选中的页面改变小圆点为选中状态，反之为未选中
            if (position % size == i) {
                (dotViewLists[i] as View).setBackgroundResource(img1)
            } else {
                (dotViewLists[i] as View).setBackgroundResource(img2)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}
