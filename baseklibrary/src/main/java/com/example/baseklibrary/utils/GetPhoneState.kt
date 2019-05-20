package com.example.baseklibrary.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import java.net.NetworkInterface
import java.net.SocketException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wangqiang on 2019/5/20.
 */
class GetPhoneState {
    companion object {
        private var connManager: ConnectivityManager? = null
        private var telephonyManager: TelephonyManager? = null
        var dm: DisplayMetrics? = null

        /**
         * 检测SDCard是否可用
         *
         * @return
         */
        fun isSDCardAvailable(): Boolean {
            return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                true
            } else {
                false
            }
        }

        /**
         * 检测网络是否可用
         *
         * @return
         */
        fun isNetworkAvailable(appContext: Context): Boolean {
            val context = appContext.applicationContext
            val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivity == null) {
                return false
            } else {
                val info = connectivity.allNetworkInfo
                if (info != null) {
                    for (i in info.indices) {
                        if (info[i].isConnected) {
                            return true
                        }
                    }
                }
            }
            return false
        }

        /**
         * 获取当前操作系统的语言
         *
         * @return String es或者zh
         */
        fun getSysLanguage(): String {
            return Locale.getDefault().language
        }

        /**
         * 获取手机型号
         *
         * @return String 手机型号
         */
        fun getModel(): String {
            return android.os.Build.MODEL
        }

        /**
         * 获取操作系统的版本号
         *
         * @return String 系统版本号
         */
        fun getSysRelease(): String {
            return android.os.Build.VERSION.RELEASE
        }

        /**
         * 读取sim卡序列号
         */
        fun readSimSerialNum(con: Context?): String {
            if (con == null) {
                return ""
            }
            if (telephonyManager == null) {
                telephonyManager = con
                    .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            }
            telephonyManager!!.subscriberId
            return telephonyManager!!.simSerialNumber
        }

        /**
         * 读取手机串号
         *
         * @param con
         * 上下文
         * @return String 手机串号
         */
        fun readTelephoneSerialNum(con: Context): String {
            val telephonyManager = con
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // String string = telephonyManager.getDeviceId();
            return telephonyManager.deviceId
        }

        /**
         * 获取运营商信息
         *
         * @param con
         * 上下文
         * @return String 运营商信息
         */
        fun getCarrier(con: Context): String {
            val telManager = con
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val imsi = telManager.subscriberId
            if (imsi != null && "" != imsi) {
                if (imsi.startsWith("46000") || imsi.startsWith("46002")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                    return "中国移动"
                } else if (imsi.startsWith("46001")) {
                    return "中国联通"
                } else if (imsi.startsWith("46003")) {
                    return "中国电信"
                }
            }
            return ""
        }

        /**
         * 获取网络类型
         *
         * @param context
         * 上下文
         * @return String 返回网络类型
         */
        fun getAccessNetworkType(context: Context): String? {
            var type = 0
            if (connManager != null) {
                type = connManager!!.activeNetworkInfo.type
            } else {
                connManager = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                type = connManager!!.activeNetworkInfo.type
            }
            if (type == ConnectivityManager.TYPE_WIFI) {
                return "wifi"
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                return "3G/GPRS"
            }
            return null
        }

        /**
         * 获取当前时间
         */
        fun getNowTime(): String {
            val format = SimpleDateFormat("yyyyMMddHHmmss")
            return format.format(Calendar.getInstance().time)
        }

        /**
         * 获取手机Ip地址
         *
         * @return
         */
        fun getLocalIpAddress(): String? {

            try {

                val en = NetworkInterface
                    .getNetworkInterfaces()
                while (en.hasMoreElements()) {

                    val intf = en.nextElement()

                    val enumIpAddr = intf
                        .inetAddresses
                    while (enumIpAddr.hasMoreElements()) {

                        val inetAddress = enumIpAddr.nextElement()

                        if (!inetAddress.isLoopbackAddress) {


                            return inetAddress.hostAddress.toString()

                        }

                    }

                }

            } catch (ex: SocketException) {

                ex.printStackTrace()

            }

            return null

        }
    }
}