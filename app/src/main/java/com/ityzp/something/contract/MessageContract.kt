package com.ityzp.something.contract

/**
 * Created by wangqiang on 2019/6/4.
 */
interface MessageContract {
    interface messageView {
        fun getMessage()
        fun removeMessage()
    }
}