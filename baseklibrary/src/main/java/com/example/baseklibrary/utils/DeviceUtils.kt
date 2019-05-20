package com.example.baseklibrary.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * Created by wangqiang on 2019/5/20.
 */
class DeviceUtils {
    companion object {
        /**
         * >=2.2
         */
        fun hasFroyo(): Boolean {

            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO
        }

        /**
         * >=2.3
         */
        fun hasGingerbread(): Boolean {

            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
        }

        /**
         * >=3.0 LEVEL:11
         */
        fun hasHoneycomb(): Boolean {

            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
        }

        /**
         * >=3.1
         */
        fun hasHoneycombMR1(): Boolean {

            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1
        }

        /**
         * >=4.0 14
         */
        fun hasICS(): Boolean {

            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
        }

        fun getSDKVersionInt(): Int {

            return Build.VERSION.SDK_INT
        }

        fun getSDKVersion(): String {

            return Build.VERSION.SDK
        }

        /**
         * 判断是否是平板电脑
         *
         * @param context
         * @return
         */
        fun isTablet(context: Context): Boolean {

            return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        }

        fun isHoneycombTablet(context: Context): Boolean {

            return hasHoneycomb() && isTablet(context)
        }

        /**
         * 获得设备型号
         *
         * @return
         */
        fun getDeviceModel(): String {

            return StringUtils.trim(Build.MODEL)
        }

        /**
         * 检测是否魅族手机
         */
        fun isMeizu(): Boolean {

            return getDeviceModel().toLowerCase().indexOf("meizu") != -1
        }

        /**
         * 检测是否HTC手机
         */
        fun isHTC(): Boolean {

            return getDeviceModel().toLowerCase().indexOf("htc") != -1
        }

        fun isXiaomi(): Boolean {

            return getDeviceModel().toLowerCase().indexOf("xiaomi") != -1
        }

        /**
         * 获得设备制造商
         *
         * @return
         */
        fun getManufacturer(): String {

            return StringUtils.trim(Build.MANUFACTURER)
        }

        fun getScreenHeight(context: Context): Int {

            val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            return display.height
        }

        /**
         * 获取屏幕宽度
         */
        fun getScreenWidth(context: Context): Int {

            val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            return display.width
        }

        /**
         * 获得设备屏幕密度
         */
        fun getScreenDensity(context: Context): Float {

            val metrics = context.applicationContext.resources.displayMetrics
            return metrics.density
        }

        fun getScreenSize(w: Int, h: Int, context: Context): IntArray {

            var phoneW = getScreenWidth(context)
            var phoneH = getScreenHeight(context)

            if (w * phoneH > phoneW * h) {
                phoneH = phoneW * h / w
            } else if (w * phoneH < phoneW * h) {
                phoneW = phoneH * w / h
            }

            return intArrayOf(phoneW, phoneH)
        }

        fun getScreenSize(w: Int, h: Int, phoneW: Int, phoneH: Int): IntArray {
            var phoneW = phoneW
            var phoneH = phoneH

            if (w * phoneH > phoneW * h) {
                phoneH = phoneW * h / w
            } else if (w * phoneH < phoneW * h) {
                phoneW = phoneH * w / h
            }
            return intArrayOf(phoneW, phoneH)
        }

        /**
         * 设置屏幕亮度
         */
        fun setBrightness(context: Activity, f: Float) {

            val lp = context.window.attributes
            lp.screenBrightness = f
            if (lp.screenBrightness > 1.0f)
                lp.screenBrightness = 1.0f
            else if (lp.screenBrightness < 0.01f)
                lp.screenBrightness = 0.01f
            context.window.attributes = lp
        }

        // private static final long NO_STORAGE_ERROR = -1L;
        private val CANNOT_STAT_ERROR = -2L

        /**
         * 检测磁盘状态
         */
        // public static int getStorageStatus(boolean mayHaveSd) {
        // long remaining = mayHaveSd ? getAvailableStorage() : NO_STORAGE_ERROR;
        // if (remaining == NO_STORAGE_ERROR) {
        // return CommonStatus.STORAGE_STATUS_NONE;
        // }
        // return remaining < CommonConstants.LOW_STORAGE_THRESHOLD ?
        // CommonStatus.STORAGE_STATUS_LOW : CommonStatus.STORAGE_STATUS_OK;
        // }
        fun getAvailableStorage(): Long {

            try {
                val storageDirectory = Environment.getExternalStorageDirectory().toString()
                val stat = StatFs(storageDirectory)
                return stat.availableBlocks.toLong() * stat.blockSize.toLong()
            } catch (ex: RuntimeException) {
                // if we can't stat the filesystem then we don't know how many
                // free bytes exist. It might be zero but just leave it
                // blank since we really don't know.
                return CANNOT_STAT_ERROR
            }

        }

        /**
         * 隐藏软键盘
         */
        fun hideSoftInput(ctx: Context, v: View) {

            val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // 这个方法可以实现输入法在窗口上切换显示，如果输入法在窗口上已经显示，则隐藏，如果隐藏，则显示输入法到窗口上
            imm.hideSoftInputFromWindow(v.applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        /**
         * 显示软键盘
         */
        fun showSoftInput(ctx: Context) {

            val imm = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED)// (v,
            // InputMethodManager.SHOW_FORCED);
        }

        /**
         * 软键盘是否已经打开
         *
         * @return
         */
        protected fun isHardKeyboardOpen(ctx: Context): Boolean {

            return ctx.resources.configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO
        }

        fun getCpuInfo(): String? {

            var cpuInfo: String? = ""
            try {
                if (File("/proc/cpuinfo").exists()) {
                    val fr = FileReader("/proc/cpuinfo")
                    val localBufferedReader = BufferedReader(fr, 8192)
                    cpuInfo = localBufferedReader.readLine()
                    localBufferedReader.close()

                    if (cpuInfo != null) {
                        cpuInfo =
                            cpuInfo.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].trim { it <= ' ' }.split(
                                " ".toRegex()
                            ).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                    }
                }
            } catch (e: IOException) {
            } catch (e: Exception) {
            }

            return cpuInfo
        }

        fun startApkActivity(ctx: Context, packageName: String) {

            val pm = ctx.packageManager
            val pi: PackageInfo
            try {
                pi = pm.getPackageInfo(packageName, 0)
                val intent = Intent(Intent.ACTION_MAIN, null)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.setPackage(pi.packageName)

                val apps = pm.queryIntentActivities(intent, 0)

                val ri = apps.iterator().next()
                if (ri != null) {
                    val className = ri.activityInfo.name
                    intent.component = ComponentName(packageName, className)
                    ctx.startActivity(intent)
                }
            } catch (e: PackageManager.NameNotFoundException) {

            }

        }
    }
}