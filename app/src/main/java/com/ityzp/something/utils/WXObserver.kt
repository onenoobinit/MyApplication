package com.ityzp.something.utils

import com.tencent.mm.opensdk.modelbase.BaseResp
import java.util.*

/**
 * 观察者模式  区分登录和分享的回调
 * Created by wangqiang on 2019/6/4.
 */
class WXObserver : Observable() {
    companion object {
        val INSTANCE = WXObserver()
    }

    fun resultCode(res: BaseResp) {
        setChanged()
        notifyObservers(res)
    }
}