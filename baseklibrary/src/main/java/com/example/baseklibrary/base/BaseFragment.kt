package com.example.baseklibrary.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.baseklibrary.sonic.SonicJavaScriptInterface
import com.example.baseklibrary.utils.NetworkUtils
import com.google.gson.Gson
import com.tencent.sonic.sdk.SonicEngine
import com.tencent.sonic.sdk.SonicSessionConfig
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class BaseFragment : RxFragment() {
    /**
     * 访问网络提交数据的集合
     */
    protected var params: Map<String, Any> = HashMap()

    protected var gson = Gson()

    protected lateinit var parentView: View

    protected lateinit var activity: Activity

    protected lateinit var inflater: LayoutInflater

    protected lateinit var mContext: Context

    @get:LayoutRes
    abstract val layoutResId: Int
    private val RETRY_TIMES: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        doSomeThingInStart()
        //过场动画
        // “内存重启”时调用  解决重叠问题  #0001
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)

            val ft = fragmentManager!!.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
        initWebView()
    }

    protected open fun doSomeThingInStart() {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        mContext = container!!.context
        //初始化Dialog
        this.inflater = inflater
        parentView = inflater.inflate(layoutResId, container, false)
        activity = this!!.getActivity()!!
        return parentView

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        finishCreateView(savedInstanceState)
    }

    abstract fun finishCreateView(state: Bundle?)
    open fun initWebView() {}


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onAttach(activity: Activity?) {

        super.onAttach(activity)
        this.activity = (activity as Activity?)!!
    }

    override fun onDetach() {

        super.onDetach()
        this.activity.finish()
    }

    fun <T : View> `$`(id: Int): T {

        return parentView.findViewById<View>(id) as T
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    fun <T> newWorkAvailable(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.retry(RETRY_TIMES)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    Log.i("newWorkAvailable", "Thread:" + Thread.currentThread().name)
                    if (!NetworkUtils.isConnected(mContext)) {
                        Toast.makeText(
                            mContext,
                            "网络连接不可用，请检查网络 ", Toast.LENGTH_LONG
                        ).show()
                        /*disposable.dispose();*/
                        /*throw new NetworkErrorException();*/
                    }
                }.subscribeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 跳转WEB ACtivity的方法。
     * 此方法为了提前预加载，使用sonic 让网页加载更快
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

    companion object {

        private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    }
}