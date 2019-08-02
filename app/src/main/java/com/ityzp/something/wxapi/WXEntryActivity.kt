package com.ityzp.something.wxapi

import android.content.Intent
import android.os.Bundle
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.L
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.utils.WXObserver
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * Created by wangqiang on 2019/6/4.
 * 微信分享登录
 */
class WXEntryActivity : BaseActivity(), IWXAPIEventHandler {
    private var wxapi: IWXAPI? = null
    override val layoutId: Int
        get() = R.layout.activity_wxentry

    override fun initViews(savedInstanceState: Bundle?) {
        wxapi = WXAPIFactory.createWXAPI(this, SomeThingApp.APP_ID, true)
        wxapi?.registerApp(SomeThingApp.APP_ID)

        try {
            val result = wxapi!!.handleIntent(intent, this)
            if (!result) {
                L.d("参数不合法，未被sdk处理，退出")
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initToolBar() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        wxapi?.handleIntent(data, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        wxapi?.handleIntent(intent, this)
        finish()
    }

    override fun onResp(p0: BaseResp?) {
        WXObserver.INSTANCE.resultCode(p0!!)
        finish()
    }

    override fun onReq(p0: BaseReq?) {
        L.i("onReq", "baseReq:$p0")
    }
}