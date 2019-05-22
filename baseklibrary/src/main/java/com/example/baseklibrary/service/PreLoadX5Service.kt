package com.example.baseklibrary.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.baseklibrary.base.BaseApplication
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsDownloader

/**
 * Created by wangqiang on 2019/5/22.
 */
class PreLoadX5Service : Service() {

    internal var cb: QbSdk.PreInitCallback = object : QbSdk.PreInitCallback {

        override fun onViewInitFinished(arg0: Boolean) {}

        override fun onCoreInitFinished() {
            preinitX5WebCore()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initX5()
        preinitX5WebCore()
    }

    private fun initX5() {
        TbsDownloader.needDownload(this, false)
        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.initX5Environment(BaseApplication.instance, cb)
    }

    private fun preinitX5WebCore() {

        if (!QbSdk.isTbsCoreInited()) {

            if (!QbSdk.isTbsCoreInited()) {
                // preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
                QbSdk.preInit(BaseApplication.instance, null)// 设置X5初始化完成的回调接口
            }
            val webView = com.tencent.smtt.sdk.WebView(this)
            val width = webView.view.width
        }
    }
}