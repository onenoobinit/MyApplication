package com.ityzp.something.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.adapter.BasicAdapter
import com.ityzp.something.contract.BasicContract
import com.ityzp.something.presenter.BasicPresenter
import kotlinx.android.synthetic.main.activity_basic.*
import kotlinx.android.synthetic.main.activity_head.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class BasicActivity : MvpActivity<BasicContract.basicView, BasicPresenter>(), BasicContract.basicView {
    var bassicList: ArrayList<String> = ArrayList()

    override fun initPresenter(): BasicPresenter {
        return BasicPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_basic

    @SuppressLint("WrongConstant")
    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        bassicList.add("侧拉菜单")
        bassicList.add("滑动联动")
        bassicList.add("特殊控件")
        rv_basic.adapter = BasicAdapter(bassicList, this)
        rv_basic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(rv_basic, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
    }

    override fun initToolBar() {
        tb_title.setText("基础组件")
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun getBasic() {
    }

}
