package com.example.baseklibrary.base

import android.app.Application
import android.text.TextUtils
import com.example.baseklibrary.sonic.SonicRuntimeImpl
import com.example.baseklibrary.utils.*
import com.tencent.sonic.sdk.SonicConfig
import com.tencent.sonic.sdk.SonicEngine
import java.io.File

/**
 * Created by wangqiang on 2019/5/20.
 */
abstract class BaseApplication : Application() {
    private var channel: String? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        // 设置工程是否打印LOG
        L.isDebug = isDebug
        //配置时候显示toast
        ToastUtil.isShow = true
        //设置SD卡 项目目录
        createAPPDir()
        //配置程序异常退出,异常捕获处理类
        setDefaultUncaughtExceptionHandler()
        // 必要时初始化音速引擎，或者U在应用程序创建时可以这样做
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(SonicRuntimeImpl(this), SonicConfig.Builder().build())
        }
    }

    /**
     * 创建SD卡 项目目录
     */
    fun createAPPDir() {
        val sdFilePath = ApplicationUtils.getSDFilePath(this)
        fileName = ApplicationUtils.getPackgeName(this)
        if (SDCardUtil.isSDCardEnable()) {
            val file = File(sdFilePath)
            if (!file.exists()) {
                file.mkdirs()
            }
        }
    }

    /**
     * 配置程序异常退出,异常捕获处理类
     */
    abstract fun setDefaultUncaughtExceptionHandler()

    /**
     * 获取渠道号
     *
     * @return
     */
    fun getChannel(): String? {
        if (TextUtils.isEmpty(channel)) {
            channel = MetaDataUtil.getFromApplication(this, "BaiduMobAd_CHANNEL")
            //根据后台需求 去掉渠道号下划线
            if (!TextUtils.isEmpty(channel) && channel!!.contains("_")) {
                channel = channel!!.replace("_", "")
            }
        }
        return channel
    }

    companion object {

        lateinit var instance: BaseApplication

        /**
         * 是否是测试模式,打包上线APK的时候一定要更改
         *
         */
        /**
         * 获取当前是否是测试模式。true为测试环境，false为正式环境
         *
         * @return
         */
        /**
         * 设置是否是测试模式
         */
        var isDebug = true
            set(isDebug) {
                field = isDebug
            }

        /**
         * 首页按返回键的次数
         */
        var BackKeyCount = 0

        /**
         * 是否使用自动堆栈管理
         */
        var isUseActivityManager = true


        /**
         * 是否保存错误日志
         */
        var isSaveErrorLog = true


        /**
         * SD卡项目地址
         */
        var fileName = "designer"
    }
}
