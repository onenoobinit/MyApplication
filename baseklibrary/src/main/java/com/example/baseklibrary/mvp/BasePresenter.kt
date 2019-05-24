package com.example.baseklibrary.mvp

import com.google.gson.Gson
import java.util.*

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class BasePresenter<T> {
    var params: Map<String, Any> = HashMap()
    val gson: Gson? = Gson()
    var view: T? = null

    fun attach(view: T) {
        this.view = view
    }

    fun detach() {
        this.view = null
    }
}