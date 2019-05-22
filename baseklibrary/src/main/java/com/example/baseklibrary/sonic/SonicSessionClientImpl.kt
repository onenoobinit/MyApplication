package com.example.baseklibrary.sonic

import android.os.Bundle
import com.tencent.smtt.sdk.WebView
import com.tencent.sonic.sdk.SonicSessionClient
import java.util.*

/**
 * Created by wangqiang on 2019/5/22.
 */
class SonicSessionClientImpl : SonicSessionClient() {

    var webView: WebView? = null
        private set

    fun bindWebView(webView: WebView?) {
        this.webView = webView
    }

    override fun loadUrl(url: String, extraData: Bundle) {
        webView!!.loadUrl(url)
    }

    override fun loadDataWithBaseUrl(
        baseUrl: String,
        data: String,
        mimeType: String,
        encoding: String,
        historyUrl: String
    ) {
        webView!!.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
    }


    override fun loadDataWithBaseUrlAndHeader(
        baseUrl: String,
        data: String,
        mimeType: String,
        encoding: String,
        historyUrl: String,
        headers: HashMap<String, String>
    ) {
        loadDataWithBaseUrl(baseUrl, data, mimeType, encoding, historyUrl)
    }

    fun destroy() {
        if (null != webView) {
            webView!!.destroy()
            webView = null
        }
    }

}
