package com.example.baseklibrary.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.baseklibrary.sonic.SonicJavaScriptInterface
import com.example.baseklibrary.sonic.SonicRuntimeImpl
import com.example.baseklibrary.sonic.SonicSessionClientImpl
import com.example.baseklibrary.utils.L
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSession
import com.tencent.sonic.sdk.SonicSessionConfig

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class BaseWebFragment : BaseFragment() {
    protected var url: String? = null
    private var sonicSessionClient: SonicSessionClientImpl? = null

    private var sonicSession: SonicSession? = null

    //-------------TBS------------------
    protected var mTBSWebView: WebView? = null
    protected lateinit var mWebSetting: WebSettings
    private val appCacheDir: String? = null

    override fun initWebView() {
        super.initWebView()
        val bundle = arguments
        val mode = bundle!!.getInt(MODE, 1)
        val iurl = bundle.getString(URL)
        url = iurl
        if (mTBSWebView != null && url != null) {
            initializeWebSetting(Intent(), url!!, mode)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(BaseApplication.instance), SonicConfig.Builder().build())
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun initializeWebSetting(intent: Intent, url: String, mode: Int) {
        // 如果是声波模式，在第一时间启动声波会议。
        if (MODE_DEFAULT != mode) { // sonic mode
            val sessionConfigBuilder = SonicSessionConfig.Builder()
            sessionConfigBuilder.setSupportLocalServer(true)

            // 如果是离线包的模式，我们需要拦截会话连接
            //            if (MODE_SONIC_WITH_OFFLINE_CACHE == mode) {
            //                sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
            //                    @Override
            //                    public String getCacheData(SonicSession session) {
            //                        return null; // 脱机缓存不需要
            //                    }
            //                });
            //
            //                sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
            //                    @Override
            //                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
            //                        return new OfflinePkgSessionConnection(getBaseContext(), session, intent);
            //                    }
            //                });
            //            }
            try {
                sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build())
            } catch (e: Exception) {
            }

            // 创建声波会话并运行音速流
            if (null != sonicSession) {
                sonicSessionClient = SonicSessionClientImpl()
                sonicSession!!.bindClient(sonicSessionClient)
            } else {
                // 这只发生在同一音速会话已经运行时
                // u可以将以下代码注释为默认模式。.
                L.d("---------------------------create session fail!-----------------------")
                //Toast.makeText(this, "create sonic session fail!", Toast.LENGTH_LONG).show();
            }
        }

        //获取WebSettings
        mWebSetting = mTBSWebView!!.settings
        mWebSetting.defaultTextEncodingName = "utf-8" //设置文本编码
        //确认加载JS
        mWebSetting.javaScriptEnabled = true
        mTBSWebView!!.removeJavascriptInterface("searchBoxJavaBridge_")
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis())
        mTBSWebView!!.addJavascriptInterface(SonicJavaScriptInterface(sonicSessionClient, intent), "sonic")
        mWebSetting.loadWithOverviewMode = true
        mWebSetting.javaScriptCanOpenWindowsAutomatically = true
        //设置自适应屏幕，两者合用
        mWebSetting.useWideViewPort = true //将图片调整到适合webview的大小
        mWebSetting.domStorageEnabled = true//存储机制
        mWebSetting.allowFileAccess = true// 允许访问文件
        mWebSetting.loadsImagesAutomatically = true //支持自动加载图片
        mWebSetting.setSupportZoom(true)
        mWebSetting.builtInZoomControls = true
        mTBSWebView!!.setOnLongClickListener { true }
        loadUrl()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun loadUrl() {
        // WebView是准备好了，就告诉客户绑定会话
        if (sonicSessionClient != null) {
            sonicSessionClient!!.bindWebView(mTBSWebView)
            sonicSessionClient!!.clientReady()
        } else { // 默认模式
            mTBSWebView!!.loadUrl(url)
        }
    }

    override fun onDestroy() {
        if (null != sonicSession) {
            sonicSession!!.destroy()
            sonicSession = null
        }
        if (null != mTBSWebView) {
            mTBSWebView!!.destroy()
        }
        super.onDestroy()
    }

    override fun onResume() {
        L.i("onResume:" + this.javaClass.getSimpleName())
        if (mTBSWebView != null) {
            mTBSWebView!!.onResume()
            mTBSWebView!!.resumeTimers()
        }
        super.onResume()
    }

    override fun onPause() {
        L.i("onPause:" + this.javaClass.getSimpleName())
        if (mTBSWebView != null) {
            mTBSWebView!!.onPause()
            mTBSWebView!!.pauseTimers()
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
