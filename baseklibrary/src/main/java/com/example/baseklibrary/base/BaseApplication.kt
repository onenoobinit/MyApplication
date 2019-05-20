package com.example.baseklibrary.base

import android.app.Application

/**
 * Created by wangqiang on 2019/5/20.
 */
abstract class BaseApplication : Application() {
    companion object {
        lateinit var instance: BaseApplication

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        createAPPDir()
    }

    private fun createAPPDir() {

    }

    abstract fun setDefaultUncaughtExceptionHandler()

}