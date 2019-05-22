package com.example.baseklibrary.x5webview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient
import com.tencent.smtt.export.external.interfaces.JsPromptResult
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView

/**
 * Created by wangqiang on 2019/5/22.
 */
class X5WebView(context: Context? = null) : BaseWebView(context!!) {
    private val resourceUrl = ""
    private val mTBSWebView: WebView? = null
    private var isClampedY = false
    private val mJsBridges: Map<String, Any>? = null
    private var refresh = false
    private val title: TextView? = null
    private val mWebSetting: WebSettings? = null
    private val appCacheDir: String? = null

    init {
        val handler: Handler? = null
        setBackgroundColor(85621)
        this.webViewClientExtension = X5WebViewEventHandler(this)// 配置X5webview的事件处理
        this.view.isClickable = true
        this.view.setOnTouchListener { v, event -> false }
    }

    private val chromeClient = object : WebChromeClient() {

        internal var myVideoView: View? = null
        internal var myNormalView: View? = null
        internal var callback: IX5WebChromeClient.CustomViewCallback? = null
        override fun onProgressChanged(view: WebView?, newProgress: Int) {

            super.onProgressChanged(view, newProgress)
        }

        override fun onJsConfirm(arg0: WebView?, arg1: String?, arg2: String?, arg3: JsResult?): Boolean {
            return super.onJsConfirm(arg0, arg1, arg2, arg3)
        }


        override fun onHideCustomView() {
            if (callback != null) {
                callback!!.onCustomViewHidden()
                callback = null
            }
            if (myVideoView != null) {
                val viewGroup = myVideoView!!.parent as ViewGroup
                viewGroup.removeView(myVideoView)
                viewGroup.addView(myNormalView)
            }
        }

        override fun onShowFileChooser(
            arg0: WebView?,
            arg1: ValueCallback<Array<Uri>>?, arg2: WebChromeClient.FileChooserParams?
        ): Boolean {

            Log.e("app", "onShowFileChooser")
            return super.onShowFileChooser(arg0, arg1, arg2)
        }

        override fun openFileChooser(uploadFile: ValueCallback<Uri>, acceptType: String?, captureType: String?) {
            Log.e("app", "openFileChooser")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            try {
                (this@X5WebView.getContext() as Activity).startActivityForResult(
                    Intent.createChooser(intent, "choose files"),
                    1
                )
            } catch (ex: android.content.ActivityNotFoundException) {

            }

            super.openFileChooser(uploadFile, acceptType, captureType)
        }


        /**
         * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
         */
        override fun onJsPrompt(
            arg0: WebView?,
            arg1: String?,
            arg2: String?,
            arg3: String?,
            arg4: JsPromptResult?
        ): Boolean {
            // 在这里可以判定js传过来的数据，用于调起android native 方法
            return if (this@X5WebView.isMsgPrompt(arg1)) {
                if (this@X5WebView.onJsPrompt(arg2, arg3)) {
                    true
                } else {
                    false
                }
            } else super.onJsPrompt(arg0, arg1, arg2, arg3, arg4)
        }

        override fun onReceivedTitle(arg0: WebView?, arg1: String?) {
            super.onReceivedTitle(arg0, arg1)
        }
    }


    protected override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {


        return super.drawChild(canvas, child, drawingTime)
    }


    /**
     * 当webchromeClient收到 web的prompt请求后进行拦截判断，用于调起本地android方法
     *
     * @param methodName 方法名称
     * @param blockName  区块名称
     * @return true ：调用成功 ； false ：调用失败
     */
    private fun onJsPrompt(methodName: String?, blockName: String?): Boolean {
        val tag = SecurityJsBridgeBundle.BLOCK + blockName + "-" + SecurityJsBridgeBundle.METHOD + methodName

        if (this.mJsBridges != null && this.mJsBridges.containsKey(tag)) {
            (this.mJsBridges[tag] as SecurityJsBridgeBundle).onCallMethod()
            return true
        } else {
            return false
        }
    }

    /**
     * 判定当前的prompt消息是否为用于调用native方法的消息
     *
     * @param msg 消息名称
     * @return true 属于prompt消息方法的调用
     */
    private fun isMsgPrompt(msg: String?): Boolean {
        return if (msg != null && msg.startsWith(SecurityJsBridgeBundle.PROMPT_START_OFFSET)) {
            true
        } else {
            false
        }
    }

    // TBS: Do not use @Override to avoid false calls
    fun tbs_dispatchTouchEvent(ev: MotionEvent, view: View): Boolean {
        return super.super_dispatchTouchEvent(ev)
    }

    // TBS: Do not use @Override to avoid false calls
    fun tbs_onInterceptTouchEvent(ev: MotionEvent, view: View): Boolean {
        return super.super_onInterceptTouchEvent(ev)
    }

    fun tbs_onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int, view: View) {
        super_onScrollChanged(l, t, oldl, oldt)
    }

    fun tbs_onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean, view: View) {
        if (clampedY) {
            this.isClampedY = true
        } else {
            this.isClampedY = false
        }


        super_onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }

    fun tbs_computeScroll(view: View) {
        super_computeScroll()
    }

    fun tbs_overScrollBy(
        deltaX: Int, deltaY: Int, scrollX: Int, scrollY: Int, scrollRangeX: Int,
        scrollRangeY: Int, maxOverScrollX: Int, maxOverScrollY: Int, isTouchEvent: Boolean, view: View
    ): Boolean {

        if (this.top >= 100) {
            refresh = true
        }

        if (this.isClampedY && deltaY <= 0) {
            this.layout(
                this.left, this.top + -deltaY / 4, this.right,
                this.bottom + -deltaY / 4
            )
        }



        return super_overScrollBy(
            deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
            maxOverScrollY, isTouchEvent
        )
    }


    fun tbs_onTouchEvent(event: MotionEvent, view: View): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            this.isClampedY = false
            if (refresh) {
                this.reload()
                refresh = false
            }
            this.layout(this.left, 0, this.right, this.bottom)
        }

        return super_onTouchEvent(event)
    }

    companion object {
        val FILE_CHOOSER = 0
        private var isSmallWebViewDisplayed = false


        fun setSmallWebViewEnabled(enabled: Boolean) {
            isSmallWebViewDisplayed = enabled
        }
    }

}
