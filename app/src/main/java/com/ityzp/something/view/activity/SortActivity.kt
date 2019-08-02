package com.ityzp.something.view.activity

import android.os.Bundle
import android.view.View
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.adapter.SortTabAdapter
import com.ityzp.something.contract.SortContract
import com.ityzp.something.presenter.SortPresenter
import com.ityzp.something.widgets.popouwindow.MoreWindow
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_sort.*

class SortActivity : MvpActivity<SortContract.sortView, SortPresenter>(), SortContract.sortView, View.OnClickListener {
    private var moreWindow: MoreWindow? = null
    private var names: ArrayList<String>? = null
    private var sortAdapter: SortTabAdapter? = null
    override fun initPresenter(): SortPresenter {
        return SortPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_sort

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        moreWindow = MoreWindow(this)
        moreWindow?.init(ll_container)
        iv_tab_menu.setOnClickListener(this)
        initData()
        sortAdapter = names?.let { SortTabAdapter(supportFragmentManager, it) }
        view_pager.adapter = sortAdapter
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun initData() {
        names = ArrayList()
        names?.let {
            it.add("关注")
            it.add("推荐")
            it.add("热点")
            it.add("视频")
            it.add("小说")
            it.add("娱乐")
            it.add("问答")
            it.add("图片")
            it.add("科技")
            it.add("懂车帝")
            it.add("体育")
            it.add("财经")
            it.add("军事")
            it.add("国际")
            it.add("健康")
        }
    }

    override fun initToolBar() {
        tb_title.setText("分类")
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun getSort() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_tab_menu -> {
                showMOreWindow()
            }
        }
    }

    private fun showMOreWindow() {
        moreWindow?.let { it.showMoreWindow(ll_container) }
    }

    override fun onDestroy() {
        super.onDestroy()
        moreWindow?.let { it.dismiss() }
    }
}
