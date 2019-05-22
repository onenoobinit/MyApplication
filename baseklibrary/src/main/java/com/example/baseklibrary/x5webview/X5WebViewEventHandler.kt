package com.example.baseklibrary.x5webview

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension
import com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.export.external.interfaces.MediaAccessPermissionsCallback
import com.tencent.smtt.sdk.WebViewCallbackClient
import java.util.*

/**
 * Created by wangqiang on 2019/5/22.
 */
class X5WebViewEventHandler(
    /**
     * 这个类用于实现由于X5webview适配架构导致的部分client回调不会发生，或者回调中传入的值不正确
     * 这个方法中所有的interface均是直接从内核中获取值并传入内核，请谨慎修改
     * 使用时只需要在对应的方法中加入你自己的逻辑就可以
     * 同时注意：具有返回值的方法在正常情况下保持其返回效果
     * 一般而言：返回true表示消费事件，由用户端直接处理事件
     * 返回false表示需要内核使用默认机制处理事件
     */
    private val webView: X5WebView //the vote of x5webview
) : ProxyWebViewClientExtension(), IX5WebChromeClientExtension {


    /////////////////////////////////////////////////////

    /**
     * 这里使用内核的事件回调链接到对应webview的事件回调
     */
    private val callbackClient = object : WebViewCallbackClient {

        override fun onTouchEvent(event: MotionEvent, view: View): Boolean {
            return webView.tbs_onTouchEvent(event, view)
        }


        override fun overScrollBy(
            deltaX: Int, deltaY: Int, scrollX: Int,
            scrollY: Int, scrollRangeX: Int, scrollRangeY: Int,
            maxOverScrollX: Int, maxOverScrollY: Int,
            isTouchEvent: Boolean, view: View
        ): Boolean {
            return webView.tbs_overScrollBy(
                deltaX, deltaY, scrollX, scrollY,
                scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY,
                isTouchEvent, view
            )
        }

        override fun computeScroll(view: View) {
            webView.tbs_computeScroll(view)
        }


        override fun onOverScrolled(
            scrollX: Int, scrollY: Int, clampedX: Boolean,
            clampedY: Boolean, view: View
        ) {
            webView.tbs_onOverScrolled(scrollX, scrollY, clampedX, clampedY, view)
        }

        override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int, view: View) {
            webView.tbs_onScrollChanged(l, t, oldl, oldt, view)
        }

        override fun invalidate() {

        }

        override fun dispatchTouchEvent(ev: MotionEvent, view: View): Boolean {
            return webView.tbs_dispatchTouchEvent(ev, view)
        }

        override fun onInterceptTouchEvent(ev: MotionEvent, view: View): Boolean {
            return webView.tbs_onInterceptTouchEvent(ev, view)
        }
    }

    init {
        this.webView.setWebViewCallbackClient(callbackClient)

    }

    override fun acquireWakeLock() {


    }

    override fun addFlashView(arg0: View, arg1: ViewGroup.LayoutParams) {


    }

    override fun exitFullScreenFlash() {


    }

    override fun getApplicationContex(): Context? {

        return null
    }

    override fun getVideoLoadingProgressView(): View? {

        return null
    }

    override fun getX5WebChromeClientInstance(): Any? {

        return null
    }

    override fun h5videoExitFullScreen(arg0: String) {


    }

    override fun h5videoRequestFullScreen(arg0: String) {

    }

    override fun onAddFavorite(
        arg0: IX5WebViewExtension, arg1: String,
        arg2: String, arg3: JsResult
    ): Boolean {

        return false
    }

    override fun onAllMetaDataFinished(
        arg0: IX5WebViewExtension,
        arg1: HashMap<String, String>
    ) {


    }

    override fun onBackforwardFinished(arg0: Int) {


    }

    override fun onHitTestResultFinished(
        arg0: IX5WebViewExtension,
        arg1: IX5WebViewBase.HitTestResult
    ) {

    }

    override fun onHitTestResultForPluginFinished(
        arg0: IX5WebViewExtension,
        arg1: IX5WebViewBase.HitTestResult, arg2: Bundle
    ) {

        arg1.data
    }

    override fun onPageNotResponding(arg0: Runnable): Boolean {

        return false
    }

    override fun onPrepareX5ReadPageDataFinished(
        arg0: IX5WebViewExtension,
        arg1: HashMap<String, String>
    ) {


    }

    override fun onPromptNotScalable(arg0: IX5WebViewExtension) {


    }

    override fun onPromptScaleSaved(arg0: IX5WebViewExtension) {

    }

    override fun onSavePassword(
        arg0: String, arg1: String, arg2: String,
        arg3: Boolean, arg4: Message
    ): Boolean {

        return false
    }

    override fun onX5ReadModeAvailableChecked(arg0: HashMap<String, String>) {

    }

    override fun releaseWakeLock() {


    }

    override fun requestFullScreenFlash() {


    }


    //////////////////////////////////////////////////////////////////////

    /**
     * 这里是内核代理的事件处理方法
     */

    override fun onMiscCallBack(
        method: String,
        bundle: Bundle
    ): Any? {

        return null
    }

    override fun onTouchEvent(event: MotionEvent, view: View): Boolean {
        return callbackClient.onTouchEvent(event, view)
    }

    // 1
    override fun onInterceptTouchEvent(ev: MotionEvent, view: View): Boolean {
        return callbackClient.onInterceptTouchEvent(ev, view)
    }

    // 3
    override fun dispatchTouchEvent(ev: MotionEvent, view: View): Boolean {
        return callbackClient.dispatchTouchEvent(ev, view)
    }

    // 4
    override fun overScrollBy(
        deltaX: Int, deltaY: Int, scrollX: Int, scrollY: Int,
        scrollRangeX: Int, scrollRangeY: Int,
        maxOverScrollX: Int, maxOverScrollY: Int,
        isTouchEvent: Boolean, view: View
    ): Boolean {
        return callbackClient.overScrollBy(
            deltaX, deltaY, scrollX, scrollY,
            scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent, view
        )
    }

    // 5
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int, view: View) {
        callbackClient.onScrollChanged(l, t, oldl, oldt, view)
    }

    // 6
    override fun onOverScrolled(
        scrollX: Int, scrollY: Int, clampedX: Boolean,
        clampedY: Boolean, view: View
    ) {
        callbackClient.onOverScrolled(scrollX, scrollY, clampedX, clampedY, view)
    }

    // 7
    override fun computeScroll(view: View) {
        callbackClient.computeScroll(view)
    }

    override fun onPrintPage() {


    }

    override fun onSavePassword(
        arg0: ValueCallback<String>, arg1: String,
        arg2: String, arg3: String, arg4: String, arg5: String, arg6: Boolean
    ): Boolean {

        return false
    }

    override fun openFileChooser(
        arg0: ValueCallback<Array<Uri>>, arg1: String,
        arg2: String
    ) {


    }


    override fun onColorModeChanged(arg0: Long) {

    }

    override fun jsExitFullScreen() {


    }

    override fun jsRequestFullScreen() {


    }

    override fun onPermissionRequest(
        arg0: String, arg1: Long,
        arg2: MediaAccessPermissionsCallback
    ): Boolean {

        return false
    }


}