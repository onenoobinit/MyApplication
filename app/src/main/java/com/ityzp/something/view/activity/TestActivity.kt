package com.ityzp.something.view.activity

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.contract.TestContract
import com.ityzp.something.presenter.TestPresenter
import kotlinx.android.synthetic.main.activity_head.*

class TestActivity : MvpActivity<TestContract.testView,TestPresenter>(),TestContract.testView {
    override fun getTest() {

    }

    override fun initPresenter(): TestPresenter {
        return TestPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_test

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
    }

    override fun initToolBar() {
        tb_title.setText("测试")
        toobar.setNavigationOnClickListener { finish() }
    }

}
