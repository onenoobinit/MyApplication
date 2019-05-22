package com.example.baseklibrary.sonic

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import com.tencent.sonic.sdk.SonicDiffDataCallback
import org.json.JSONObject

/**
 * Created by wangqiang on 2019/5/22.
 */
class SonicJavaScriptInterface(private val sessionClient: SonicSessionClientImpl?, private val intent: Intent) {

    val performance: String
        @JavascriptInterface
        get() {
            val clickTime = intent.getLongExtra(PARAM_CLICK_TIME, -1)
            val loadUrlTime = intent.getLongExtra(PARAM_LOAD_URL_TIME, -1)
            try {
                val result = JSONObject()
                result.put(PARAM_CLICK_TIME, clickTime)
                result.put(PARAM_LOAD_URL_TIME, loadUrlTime)
                return result.toString()
            } catch (e: Exception) {

            }

            return ""
        }

    @JavascriptInterface
    fun getDiffData() {
        // 演示页面的回调函数是硬编码为“getdiffdatacallback”
        getDiffData2("getDiffDataCallback")
    }

    @JavascriptInterface
    fun getDiffData2(jsCallbackFunc: String) {
        if (null != sessionClient) {
            sessionClient!!.getDiffData(SonicDiffDataCallback { resultData ->
                val callbackRunnable = Runnable {
                    val jsCode = "javascript:" + jsCallbackFunc + "('" + toJsString(resultData) + "')"
                    sessionClient!!.webView!!.loadUrl(jsCode)
                }
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    callbackRunnable.run()
                } else {
                    Handler(Looper.getMainLooper()).post(callbackRunnable)
                }
            })
        }
    }

    companion object {

        val PARAM_CLICK_TIME = "clickTime"

        val PARAM_LOAD_URL_TIME = "loadUrlTime"

        /*
     **从RFC 4627，“所有Unicode字符都可以放在引号中，除了
     *必须转义字符：引号，
     *反斜线，和控制字符（U + 0000通过U+ 001f）。"
     */
        private fun toJsString(value: String?): String {
            if (value == null) {
                return "null"
            }
            val out = StringBuilder(1024)
            var i = 0
            val length = value.length
            while (i < length) {
                val c = value[i]


                when (c) {
                    '"', '\\', '/' -> out.append('\\').append(c)

                    '\t' -> out.append("\\t")

                    '\b' -> out.append("\\b")

                    '\n' -> out.append("\\n")

                    '\r' -> out.append("\\r")

                    else -> if (c.toInt() <= 0x1F) {
                        out.append(String.format("\\u%04x", c.toInt()))
                    } else {
                        out.append(c)
                    }
                }
                i++

            }
            return out.toString()
        }
    }
}