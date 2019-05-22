package com.example.baseklibrary.mvp

import android.os.Bundle
import com.example.baseklibrary.base.BaseFragment

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class MvpFragment<V, P : BasePresenter<V>> : BaseFragment() {
    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected override fun doSomeThingInStart() {
        super.doSomeThingInStart()
        mPresenter = initPresenter()
        mPresenter.attach(this as V)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        mPresenter.detach()
        super.onDestroyView()
    }

    /**
     * 初始化presenter
     *
     * @return
     */
    abstract fun initPresenter(): P
}