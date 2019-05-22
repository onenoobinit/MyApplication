package com.example.baseklibrary.mvp

import com.example.baseklibrary.base.BaseActivity

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class MvpActivity<V, P : BasePresenter<V>> : BaseActivity() {
    protected lateinit var mPresenter: P

    protected override fun doSomeThingBeforeInitView() {
        super.doSomeThingBeforeInitView()
        this.mPresenter = initPresenter()
    }

    protected override fun onResume() {
        super.onResume()
        mPresenter.attach(this as V)
    }

    protected override fun onDestroy() {
        mPresenter.detach()
        super.onDestroy()
    }

    /**
     * 初始化presenter
     *
     * @return
     */
    abstract fun initPresenter(): P
}