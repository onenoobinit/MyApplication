package com.example.baseklibrary.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * Created by wangqiang on 2019/5/20.
 */
class ApplicationUtils {
    companion object {
        /**
         * 备份App 首先无需提升权限就就可以复制APK，查看权限你就会知道，在data/app下的APK权限如下：-rw-r--r-- system
         *
         * @param packageName
         * @param mActivity
         * @throws IOException
         */
        fun backupApp(packageName: String?, mActivity: Activity) {
            val newFile = Environment.getExternalStorageDirectory().absolutePath + File.separator
            var oldFile: String? = null
            try {
                oldFile = mActivity.packageManager.getApplicationInfo(packageName, 0).sourceDir
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            println(newFile)
            println(oldFile)
            val inFile = File(oldFile)
            val out = File(newFile + packageName + ".apk")
            if (!out.exists()) {
                out.createNewFile()
                Toast.makeText(mActivity, "文件备份成功！" + "存放于" + newFile + "目录下", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(mActivity, "文件已经存在！" + "查看" + newFile + "目录下", Toast.LENGTH_SHORT).show()
            }

            val fis = FileInputStream(inFile)
            val fos = FileOutputStream(out)
            var count: Int
            val buffer = ByteArray(256 * 1024)
            count = fis.read(buffer)
            while (count > 0) {
                fos.write(buffer, 0, count)
            }
            fis.close()
            fos.flush()
            fos.close()
        }

        /**
         * 获取当前Apk版本号 android:versionCode
         *
         * @param context
         * @return
         */
        fun getVerCode(context: Context): Int {
            var verCode = -1
            try {
                verCode = context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
            }
            return verCode
        }

        fun getVerName(context: Context): String {
            try {
                return context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
            }
            return ""
        }

        /**
         * 返回当前的应用是否处于前台显示状态 不需要android.permission.GET_TASKS权限
         *
         * @param packageName
         * @return
         */
        fun isTopActivity(context: Context, packageName: String?): Boolean {
            val activityManager =
                context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val list = activityManager.runningAppProcesses
            if (list.size == 0)
                return false
            for (process in list) {
                if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && process.processName == packageName) {
                    return true
                }
            }
            return false
        }

        /**
         * 检测APP是否存在
         *
         * @param context
         * @param packageName
         * @return
         */
        fun checkAppExist(context: Context, packageName: String?): Boolean {
            try {
                val info = context.packageManager.getApplicationInfo(packageName, 0) as ApplicationInfo
                return info != null && info.packageName == packageName
            } catch (e: PackageManager.NameNotFoundException) {
            }
            return false
        }

        /**
         * 判断是否是DEBUG模式
         *
         * @param context
         * @return
         */
        fun isApkDebugable(context: Context): Boolean {
            try {
                val info = context.applicationContext as ApplicationInfo
                return info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
            } catch (e: PackageManager.NameNotFoundException) {

            }
            return false
        }

        /**
         * 获取app名字
         *
         * @param context
         * @return
         */
        fun getAppName(context: Context): String {
            val pm = context.packageManager
            return context.applicationInfo.loadLabel(pm).toString()
        }

        /**
         * 获取包名
         *
         * @param context
         * @return
         */
        fun getPackgeName(context: Context): String {
            return context.packageName
        }

        /**
         * 获取在sd卡中文件路径
         *
         * @param context
         * @return
         */
        fun getSDFilePath(context: Context): String {
            val packgeName = ApplicationUtils.getPackgeName(context)
            val folderName = packgeName.substring(packgeName.lastIndexOf(".") + 1)
            return SDCardUtil.getSDCardPath() + folderName + File.separator + "log" + File.separator
        }

        /**
         * 调用系统浏览器下载
         *
         * @param context
         * @param url
         */
        fun download(context: Context, url: String) {
            if (url != null) {
                val intent = Intent()
                intent.setAction("android.intent.action.VIEW")
                val parse = Uri.parse(url)
                intent.setData(parse)
                context.startActivity(intent)
            }
        }

        fun getSDFilePackagePath(context: Context): String {
            val packgeName = ApplicationUtils.getPackgeName(context)
            val folderName = packgeName.substring(packgeName.lastIndexOf(".") + 1)
            return SDCardUtil.getSDCardPath() + folderName + File.separator + "log" + File.separator
        }
    }
}