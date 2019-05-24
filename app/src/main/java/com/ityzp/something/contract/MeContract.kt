package com.ityzp.something.contract

import com.ityzp.something.moudle.MeInfo

/**
 * Created by wangqiang on 2019/5/24.
 */
interface MeContract {
    interface meView {
        /**
         * 获取专家问答首页数据
         */
        fun getMeInfo(meInfo: MeInfo)

        /**
         * 0-->网络出错 1-->服务器异常
         *//*
        fun onError(type: Int)*/

    }
}