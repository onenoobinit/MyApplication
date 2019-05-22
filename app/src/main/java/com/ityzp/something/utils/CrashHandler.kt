package com.ityzp.something.utils

import android.content.Context
import android.os.Looper
import android.widget.Toast
import com.example.baseklibrary.base.BaseApplication
import com.example.baseklibrary.base.BaseExceptionHandler
import com.example.baseklibrary.manager.ActivityManager

/**
 * Created by wangqiang on 2019/5/22.
 */
class CrashHandler(context: Context) : BaseExceptionHandler(context) {
    override fun handleException(ex: Throwable): Boolean {
        if (ex == null) {
            return false
        }

        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(context, "很抱歉，程序出现异常", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }.start()
        try {
            if (BaseApplication.isSaveErrorLog) {
                //保存错误日志
                val filename = saveCrashInfo2File(ex, collectDeviceInfo())
                //如果处理了,让程序继续运行1秒后退出,保证错误日志的保存
                Thread.sleep(1000)
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return true
    }

    override fun onfinish() {
        //退出程序  只要执行推出程序  就会执行3次
        ActivityManager.instance!!.finishAllActivity()
    }
}
