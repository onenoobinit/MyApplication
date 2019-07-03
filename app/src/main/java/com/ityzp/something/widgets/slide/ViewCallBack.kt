package com.ityzp.something.widgets.slide

/**
 * Created by wangqiang on 2019/7/3.
 */
interface ViewCallBack<V> {

    /**
     * @param code code:0:有数据，1：数据为空,2:加载失败
     * @param data 定义好的数据类型
     */

    fun refreshView(code: Int, data: V)
}