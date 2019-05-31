package com.ityzp.something.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.baseklibrary.mvp.MvpFragment
import com.example.baseklibrary.utils.DensityUtil
import com.ityzp.something.R
import com.ityzp.something.adapter.IndexBannerAdapter
import com.ityzp.something.adapter.IndexRvTitleAdapter
import com.ityzp.something.contract.IndexContract
import com.ityzp.something.presenter.IndexPresenter
import com.ityzp.something.widgets.ViewPagerIndicator
import com.ityzp.something.widgets.popouwindow.IndexPopupWindow
import com.tmall.ultraviewpager.UltraViewPager
import kotlinx.android.synthetic.main.layout_index_center.*
import kotlinx.android.synthetic.main.layout_index_levitate.*
import kotlinx.android.synthetic.main.layout_inex_top.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

/**
 * Created by wangqiang on 2019/5/23.
 */
class IndexFragment : MvpFragment<IndexContract.indexView, IndexPresenter>(), IndexContract.indexView,
    View.OnClickListener {
    private var indexBanners: ArrayList<Int> = ArrayList()
    private var indexTitles: ArrayList<String> = ArrayList()
    private var indexPopupWindow: IndexPopupWindow? = null

    override fun initPresenter(): IndexPresenter {
        return IndexPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_index

    override fun finishCreateView(state: Bundle?) {
        initBanner()
        initRv()
        rl_index_top_more.setOnClickListener(this)
        indexPopupWindow = IndexPopupWindow(mContext)


//        mPresenter.getIndex()
    }

    private fun initRv() {
        indexTitles.add("推荐")
        indexTitles.add("销量")
        indexTitles.add("价格")
        indexTitles.add("其他")
        rv_index_title.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        val indexRvTitleAdapter = IndexRvTitleAdapter(mContext, indexTitles)
        rv_index_title.adapter = indexRvTitleAdapter
        OverScrollDecoratorHelper.setUpOverScroll(rv_index_title, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
    }

    private fun initBanner() {
        indexBanners.clear()
        indexBanners.add(R.mipmap.ic_index_x1)
        indexBanners.add(R.mipmap.ic_index_x2)
        indexBanners.add(R.mipmap.ic_index_x3)
        val indexBannerAdapter = IndexBannerAdapter(mContext, indexBanners)
        uvp_index.adapter = indexBannerAdapter
        uvp_index.setOffscreenPageLimit(1)
        uvp_index.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        uvp_index.setInfiniteLoop(true)
        uvp_index.setAutoScroll(4000)
        uvp_index.viewPager.pageMargin = DensityUtil.px2dp(mContext, 60F).toInt()
        val viewPagerIndicator = ViewPagerIndicator(mContext, uvp_index.viewPager, ll_indicator, indexBanners.size)
        uvp_index.viewPager.addOnPageChangeListener(viewPagerIndicator)
    }

    override fun getIndex() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_index_top_more -> {
                indexPopupWindow!!.showPopupWindow(rl_index_top_more, indexTitles)
            }
        }
    }

}