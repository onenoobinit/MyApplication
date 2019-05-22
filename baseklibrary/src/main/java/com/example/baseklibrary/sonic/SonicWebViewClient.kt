package com.example.baseklibrary.sonic

import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.tencent.sonic.sdk.SonicSession

/**
 * Created by wangqiang on 2019/5/22.
 */
class SonicWebViewClient(private val sonicSession: SonicSession?) : WebViewClient() {

    override fun onPageFinished(webView: WebView?, s: String?) {
        super.onPageFinished(webView, s)
        sonicSession?.sessionClient?.pageFinish(s)
    }

    override fun shouldInterceptRequest(webView: WebView, webResourceRequest: WebResourceRequest): WebResourceResponse {
        return super.shouldInterceptRequest(webView, webResourceRequest.url.toString())
    }

    override fun shouldInterceptRequest(webView: WebView?, s: String?): WebResourceResponse? {
        return if (sonicSession != null) {
            sonicSession.sessionClient.requestResource(s) as WebResourceResponse
        } else null
    }

}
