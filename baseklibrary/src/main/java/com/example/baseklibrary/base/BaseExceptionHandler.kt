package com.example.baseklibrary.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import com.example.baseklibrary.utils.ApplicationUtils
import com.example.baseklibrary.utils.DateUtil
import com.example.baseklibrary.utils.L
import com.example.baseklibrary.utils.SDCardUtil
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class BaseExceptionHandler(protected var context: Context) : Thread.UncaughtExceptionHandler {
    private val mDefaultHandler: Thread.UncaughtExceptionHandler?

    init {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    }


    /**
     * 未捕获异常跳转
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        //如果正确处理的为捕获异常 FIXME 此处不知道为何会执行3次
        if (!handleException(ex) && null != mDefaultHandler) {
            mDefaultHandler.uncaughtException(thread, ex)
        }

        onfinish()
    }

    /**
     * 自定义错误处理,手机错误信息,发送错误报告操作均在此完成,开发者可以根据自己的情况来自定义异常处理逻辑
     * @param ex
     * @return
     */
    abstract fun handleException(ex: Throwable): Boolean


    /**
     * 最终是应该 跳往某个activity 还是 退出程序 在这里写
     */
    abstract fun onfinish()


    /**
     * 收集设备参数信息
     */
    protected fun collectDeviceInfo(): Map<String, String> {
        val infos = LinkedHashMap<String, String>()
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            )
            if (pi != null) {
                val versionName = if (pi.versionName == null)
                    "null"
                else
                    pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            L.e(TAG, "an error occured when collect package info$e")
        }

        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
            } catch (e: Exception) {
                L.e(TAG, "an error occured when collect crash info$e")
            }

        }
        return infos
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    protected fun saveCrashInfo2File(ex: Throwable, deviceInfo: Map<String, String>): String? {
        val sb = StringBuffer()
        for ((key, value) in deviceInfo) {
            sb.append("$key=$value\n")
        }
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        L.e("Exception", sb.toString())//打印出异常，以便调试
        try {
            val timestamp = System.currentTimeMillis()
            val time = DateUtil.date2Str(Date(), DateUtil.FORMAT_FULL_SN)
            val fileName = time + "-" + timestamp + ".log"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val packgeName = ApplicationUtils.getPackgeName(context)
                val folderName = packgeName.substring(packgeName.lastIndexOf(".") + 1)
                val path = SDCardUtil.getSDCardPath() + folderName + File.separator + "log" + File.separator
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
            return fileName
        } catch (e: Exception) {
            L.e(TAG, "an error occured while writing file...$e")
        }

        return null
    }

    companion object {

        val TAG = "BaseExceptionHandler"
    }
}