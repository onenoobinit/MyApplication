package com.example.baseklibrary.base

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class LazyFragment : BaseFragment() {
    protected var isVisible: Boolean? = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    protected abstract fun lazyLoad()

    protected fun onVisible() {
        lazyLoad()
    }

    protected fun onInvisible() {

    }

}
