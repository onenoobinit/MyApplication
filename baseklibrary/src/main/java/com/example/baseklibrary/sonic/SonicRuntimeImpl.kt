package com.example.baseklibrary.sonic

import android.content.Context
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.webkit.CookieManager
import android.webkit.WebResourceResponse
import com.example.baseklibrary.base.BaseApplication
import com.example.baseklibrary.utils.L
import com.example.baseklibrary.utils.PhoneUtil
import com.example.baseklibrary.utils.ToastUtil
import com.tencent.sonic.sdk.SonicRuntime
import com.tencent.sonic.sdk.SonicSessionClient
import java.io.File
import java.io.InputStream

/**
 * Created by wangqiang on 2019/5/22.
 */
class SonicRuntimeImpl(context: Context) : SonicRuntime(context) {

    /**
     * 获取用户UA信息
     * @return
     */
    override fun getUserAgent(): String {
        return PhoneUtil.getMobileModel(BaseApplication.instance)
    }

    /**
     * 获取用户ID信息
     * @return
     */
    override fun getCurrentUserAccount(): String {
        return "com.youjuke.designer"
    }

    override fun getCookie(url: String): String {
        val cookieManager = CookieManager.getInstance()
        return cookieManager.getCookie(url)
    }

    override fun log(tag: String, level: Int, message: String) {
        when (level) {
            Log.ERROR -> L.e(tag, message)
            Log.INFO -> L.i(tag, message)
            else -> L.d(tag, message)
        }
    }

    override fun createWebResourceResponse(
        mimeType: String,
        encoding: String,
        data: InputStream,
        headers: Map<String, String>
    ): Any {
        val resourceResponse = WebResourceResponse(mimeType, encoding, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.responseHeaders = headers
        }
        return resourceResponse
    }

    override fun showToast(text: CharSequence, duration: Int) {
        ToastUtil.show(BaseApplication.instance, text.toString(), Gravity.CENTER)
    }

    override fun notifyError(client: SonicSessionClient, url: String, errorCode: Int) {

    }

    override fun isSonicUrl(url: String): Boolean {
        return true
    }

    override fun setCookie(url: String, cookies: List<String>?): Boolean {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size > 0) {
            val cookieManager = CookieManager.getInstance()
            for (cookie in cookies) {
                cookieManager.setCookie(url, cookie)
            }
            return true
        }
        return false
    }

    override fun isNetworkValid(): Boolean {
        return true
    }

    override fun postTaskToThread(task: Runnable, delayMillis: Long) {
        val thread = Thread(task, "SonicThread")
        thread.start()
    }

    override fun getSonicCacheDir(): File {
        if (BuildConfig.DEBUG) {
            val path = Environment.getExternalStorageDirectory().absolutePath + File.separator + "Designer/"
            val file = File(path.trim { it <= ' ' })
            if (!file.exists()) {
                file.mkdir()
            }
            return file
        }
        return super.getSonicCacheDir()
    }

    override fun getHostDirectAddress(url: String?): String? {
        return null
    }
}
