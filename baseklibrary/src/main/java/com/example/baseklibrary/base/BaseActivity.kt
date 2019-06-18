package com.example.baseklibrary.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.baseklibrary.manager.ActivityManager
import com.example.baseklibrary.sonic.SonicJavaScriptInterface
import com.example.baseklibrary.sonic.SonicSessionClientImpl
import com.example.baseklibrary.utils.L
import com.example.baseklibrary.utils.NetworkUtils
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.x5webview.BaseWebView
import com.google.gson.Gson
import com.jaeger.library.StatusBarUtil
import com.tencent.smtt.sdk.WebSettings
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSession
import com.tencent.sonic.sdk.SonicSessionConfig
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class BaseActivity : RxAppCompatActivity() {
    private val RETRY_TIMES: Long = 1
    /**
     * 访问网络时 提交数据的集合
     */
    protected var params: Map<String, Any> = HashMap()

    protected var gson = Gson()

    /**
     * ----------------TBS 配置--------------------
     */

    protected lateinit var mWebSetting: WebSettings

    private val appCacheDir: String? = null

    protected var mTBSWebView: BaseWebView? = null
    protected var url: String? = null
    protected var sonicSession: SonicSession? = null
    protected var sonicSessionClient: SonicSessionClientImpl? = null

    abstract val layoutId: Int

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        doSomeThingBeforeInitView()
        setContentView(layoutId)
        if (null != intent) {
            handleIntent(intent)
        }
        setStatusColor()
        /*
         * 状态栏字体颜色,适配小米的MIUI、魅族的Flyme和Android6.0以上
         */
        val b = FitterSetStatusBarLightMode(this.window, true)
        if (!b) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            }
        }

        initViews(savedInstanceState)
        initToolBar()
        ActivityManager.instance!!.addActivity(this)
        if (mTBSWebView != null) {
            initializeWebSetting(url)
        }
        initWebView()

    }

    /**
     * TBSWebSetting，TbsWeb缓存
     */
    private fun initializeWebSetting(url: String?) {
        // 如果是声波模式，在第一时间启动声波会议。
        val sessionConfigBuilder = SonicSessionConfig.Builder()
        sessionConfigBuilder.setSupportLocalServer(true)
        // 创建声波会话并运行音速流
        if (!TextUtils.isEmpty(url)) {
            sonicSession = SonicEngine.getInstance().createSession(url!!, sessionConfigBuilder.build())
        }
        if (null != sonicSession) {
            sonicSessionClient = SonicSessionClientImpl()
            sonicSession!!.bindClient(sonicSessionClient)
            val intent = Intent()
            intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis())
            mTBSWebView!!.removeJavascriptInterface("searchBoxJavaBridge_")
            mTBSWebView!!.addJavascriptInterface(SonicJavaScriptInterface(sonicSessionClient, intent), "sonic")
        }
        //获取WebSettings
        mWebSetting = mTBSWebView!!.getSettings()
        mWebSetting.defaultTextEncodingName = "utf-8" //设置文本编码
        //确认加载JS
        mWebSetting.javaScriptEnabled = true
        mWebSetting.loadWithOverviewMode = true
        mWebSetting.javaScriptCanOpenWindowsAutomatically = true
        //设置自适应屏幕，两者合用
        mWebSetting.useWideViewPort = true //将图片调整到适合webview的大小
        mWebSetting.domStorageEnabled = true//存储机制
        mWebSetting.allowFileAccess = true// 允许访问文件
        mWebSetting.loadsImagesAutomatically = true //支持自动加载图片
        mWebSetting.setSupportZoom(false)
        mWebSetting.builtInZoomControls = true
        mTBSWebView!!.setOnLongClickListener(View.OnLongClickListener { true })
    }

    fun loadUrl(url: String) {
        // WebView是准备好了，就告诉客户绑定会话
        if (sonicSessionClient != null) {
            sonicSessionClient!!.bindWebView(mTBSWebView)
            sonicSessionClient!!.clientReady()
        } else { // 默认模式
            mTBSWebView!!.loadUrl(url)
        }
    }

    protected open fun doSomeThingBeforeInitView() {

    }

    fun setStatusColor() {
        StatusBarUtil.setColor(this, Color.parseColor("#ffffff"), 0)
    }


    override fun onStop() {
        super.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mTBSWebView != null) {
            //释放资源
            mTBSWebView!!.stopLoading()
            mTBSWebView!!.removeAllViews()
            mTBSWebView!!.destroy()
            mTBSWebView = null
        }
        if (null != sonicSession) {
            sonicSession!!.destroy()
            sonicSession = null
        }
        ActivityManager.instance!!.finishActivity(this)
    }

    protected abstract fun initViews(savedInstanceState: Bundle?)

    abstract fun initToolBar()

    open fun initWebView() {}

    /**
     * 获取Intent
     */
    protected fun handleIntent(intent: Intent) {}

    fun <T> newWorkAvailable(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.retry(RETRY_TIMES)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    Log.i("newWorkAvailable", "Thread:" + Thread.currentThread().name)
                    if (!NetworkUtils.isConnected(this@BaseActivity)) {
                        Toast.makeText(
                            this@BaseActivity,
                            "网络连接不可用，请检查网络 ", Toast.LENGTH_LONG
                        ).show()
                        /*disposable.dispose();*/
                        /*throw new NetworkErrorException();*/
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
        }
    }

    override fun onResume() {
        super.onResume()
        L.i("onResume:" + this.javaClass.simpleName)
        if (mTBSWebView != null) {
            mTBSWebView!!.onResume()
            mTBSWebView!!.resumeTimers()
        }
    }

    override fun onPause() {
        L.i("onPause:" + this.javaClass.simpleName)
        super.onPause()
        if (mTBSWebView != null) {
            mTBSWebView!!.onPause()
            mTBSWebView!!.pauseTimers()
        }
    }

    /**
     * 跳转WEB ACtivity的方法。
     * 此方法为了提前预加载，使用sonic 让网页加载更快
     *
     * @param url
     * @param intent
     */
    fun startWebActivity(url: String?, intent: Intent?) {
        //-----------------------
        val sessionConfigBuilder = SonicSessionConfig.Builder()
        sessionConfigBuilder.setSupportLocalServer(true)
        val preloadSuccess = SonicEngine.getInstance().preCreateSession(url!!, sessionConfigBuilder.build())
        intent!!.putExtra(BaseWebActivity.URL, url)
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis())
        startActivity(intent)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideInput(v, ev)) {
                //                if (hideInputMethod(this, v)) {
                //                    return false; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                //                }
                hideInputMethod(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {

        /**
         * 设置状态栏字体图标为深色，需要小米的MIUI、魅族的Flyme或者Android6.0以上
         *
         * @param window 需要设置的窗口
         * @param dark   是否把状态栏字体及图标颜色设置为深色
         * @return boolean 成功执行返回true
         */
        fun FitterSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                try {
                    val clazz = window.javaClass
                    var darkModeFlag = 0
                    val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                    val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                    darkModeFlag = field.getInt(layoutParams)
                    val extraFlagField =
                        clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                    }
                    result = true
                } catch (e: Exception) {

                }

                try {
                    val lp = window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java
                        .getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(lp)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }
                    meizuFlags.setInt(lp, value)
                    window.attributes = lp
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }

        fun isShouldHideInput(v: View?, event: MotionEvent): Boolean {
            if (v != null && v is EditText) {
                val leftTop = intArrayOf(0, 0)
                v.getLocationInWindow(leftTop)
                val left = leftTop[0]
                val top = leftTop[1]
                val bottom = top + v.height
                val right = left + v.width
                return if (event.x > left && event.x < right
                    && event.y > top && event.y < bottom
                ) {
                    // 保留点击EditText的事件
                    false
                } else {
                    true
                }
            }
            return false
        }

        fun hideInputMethod(context: Context, v: View): Boolean {
            val imm = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return imm?.hideSoftInputFromWindow(v.windowToken, 0) ?: false
        }
    }
}
