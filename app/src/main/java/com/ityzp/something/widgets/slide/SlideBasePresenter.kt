package com.ityzp.something.widgets.slide

/**
 * Created by wangqiang on 2019/7/3.
 */
abstract class SlideBasePresenter {

    protected var mViewCallBack: ViewCallBack<*>? = null

    internal fun add(viewCallBack: ViewCallBack<*>) {
        this.mViewCallBack = viewCallBack
    }

    internal fun remove() {
        this.mViewCallBack = null
    }

    protected abstract fun getData()
}
