package com.example.baseklibrary.base

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.text.TextUtils
import com.example.baseklibrary.sonic.SonicJavaScriptInterface
import com.example.baseklibrary.sonic.SonicRuntimeImpl
import com.example.baseklibrary.sonic.SonicSessionClientImpl
import com.example.baseklibrary.utils.L
import com.example.baseklibrary.x5webview.BaseWebView
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSession
import com.tencent.sonic.sdk.SonicSessionConfig

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class BaseWebActivity : BaseActivity() {
    protected var wurl: String? = null
    private var wsonicSessionClient: SonicSessionClientImpl? = null

    private var wsonicSession: SonicSession? = null

    //-------------TBS------------------
    protected var wTBSWebView: WebView? = null
    protected lateinit var wWebSetting: WebSettings


    override fun initWebView() {
        super.initWebView()
        val intent = intent
        val mode = intent.getIntExtra(MODE, 1)
        url = intent.getStringExtra(URL)
        if (wTBSWebView != null && !TextUtils.isEmpty(url)) {
            initializeWebSetting(intent, wurl, mode)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 必要时初始化音速引擎，或者U在应用程序创建时可以这样做
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(application), SonicConfig.Builder().build())
        }
    }

    fun initializeWebSetting(intent: Intent, wurl: String?, mode: Int) {
        // 如果是声波模式，在第一时间启动声波会议。
        if (MODE_DEFAULT != mode) { // sonic mode
            val sessionConfigBuilder = SonicSessionConfig.Builder()
            sessionConfigBuilder.setSupportLocalServer(true)
            wsonicSession = SonicEngine.getInstance().createSession(wurl!!, sessionConfigBuilder.build())
            if (null != wsonicSession) {
                wsonicSessionClient = SonicSessionClientImpl()
                wsonicSession!!.bindClient(wsonicSessionClient)
            } else {
                // 这只发生在同一音速会话已经运行时
                // u可以将以下代码注释为默认模式。.
                L.d("---------------------------create session fail!-----------------------")
                //Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
            }
        }

        //获取WebSettings
        wWebSetting = wTBSWebView!!.settings
        wWebSetting.defaultTextEncodingName = "utf-8" //设置文本编码
        //确认加载JS
        wWebSetting.javaScriptEnabled = true
        wTBSWebView!!.removeJavascriptInterface("searchBoxJavaBridge_")
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis())
        wTBSWebView!!.addJavascriptInterface(SonicJavaScriptInterface(wsonicSessionClient, intent), "sonic")
        wWebSetting.loadWithOverviewMode = true
        wWebSetting.javaScriptCanOpenWindowsAutomatically = true
        //设置自适应屏幕，两者合用
        wWebSetting.useWideViewPort = true //将图片调整到适合webview的大小
        wWebSetting.domStorageEnabled = true//存储机制
        wWebSetting.allowFileAccess = true// 允许访问文件
        wWebSetting.loadsImagesAutomatically = true //支持自动加载图片
        wWebSetting.setSupportZoom(true)
        wWebSetting.builtInZoomControls = true
        wTBSWebView!!.setOnLongClickListener { true }
        loadUrl()
    }

    fun loadUrl() {
        // WebView是准备好了，就告诉客户绑定会话
        if (wsonicSessionClient != null) {
            wsonicSessionClient!!.bindWebView(wTBSWebView as BaseWebView?)
            wsonicSessionClient!!.clientReady()
        } else { // 默认模式
            wTBSWebView!!.loadUrl(wurl)
        }
    }

    protected override fun onDestroy() {
        if (null != wsonicSession) {
            wsonicSession!!.destroy()
            wsonicSession = null
        }
        super.onDestroy()
    }

    protected override fun onResume() {
        L.i("onResume:" + this.javaClass.getSimpleName())
        if (wTBSWebView != null) {
            wTBSWebView!!.onResume()
            wTBSWebView!!.resumeTimers()
        }
        super.onResume()
    }

    protected override fun onPause() {
        L.i("onPause:" + this.javaClass.getSimpleName())
        if (wTBSWebView != null) {
            wTBSWebView!!.onPause()
            wTBSWebView!!.pauseTimers()
        }
        super.onPause()
    }

    companion object {


        val URL = "url"

        val MODE = "mode"

        val MODE_DEFAULT = 0

        val MODE_SONIC = 1

        val MODE_SONIC_WITH_OFFLINE_CACHE = 2

        private val PERMISSION_REQUEST_CODE_STORAGE = 1
    }

}
