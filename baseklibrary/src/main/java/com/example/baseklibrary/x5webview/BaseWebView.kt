package com.example.baseklibrary.x5webview

import android.content.Context
import android.util.AttributeSet
import com.example.baseklibrary.utils.L
import com.tencent.smtt.sdk.WebView

/**
 * Created by wangqiang on 2019/5/22.
 */
open class BaseWebView : WebView {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}

    constructor(context: Context, attributeSet: AttributeSet, i: Int) : super(context, attributeSet, i) {}

    override fun loadUrl(s: String?, map: Map<String, String>) {
        if (s != null && !s.toLowerCase().contains("javascript") && !s.toLowerCase().contains("file:")) {
            /*s = CRequest.append(s,"platform=app");
            s = CRequest.append(s,"os=android");
            s = CRequest.append(s,"channel="+ BaseApplication.getInstance().getChannel());*/
        }
        super.loadUrl(s, map)
    }

    override fun loadUrl(s: String?) {
        if (s != null && !s.toLowerCase().contains("javascript") && !s.toLowerCase().contains("file:")) {
            /*s = CRequest.append(s,"platform=app");
            s = CRequest.append(s,"os=android");
            s = CRequest.append(s,"channel="+ BaseApplication.getInstance().getChannel());*/
        }
        L.i("loadUrl:" + s!!)
        super.loadUrl(s)
    }
}
