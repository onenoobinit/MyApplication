package com.example.baseklibrary.mvp

/**
 * Created by wangqiang on 2019/5/22.
 */
interface BaseView {
    /**
     * 显示加载框
     */
    fun showLoading()

    /**
     * 隐藏加载框
     */
    fun hideLoading()

    /**
     * 弹出提示
     * @param s
     */
    fun toast(s: String)
}