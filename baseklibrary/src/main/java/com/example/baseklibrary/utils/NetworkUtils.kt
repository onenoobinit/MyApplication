package com.example.baseklibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.provider.Settings
import android.util.Log

/**
 * Created by wangqiang on 2019/5/20.
 */
class NetworkUtils {
    companion object {
        /**
         * 判断网络是否连接
         *
         * @param context
         * @return
         */
        fun isConnected(context: Context): Boolean {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (null != connectivityManager) {
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo != null && networkInfo.isConnected) {
                    if (networkInfo.state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }

            return false
        }

        fun isWIFI(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkINfo = cm.activeNetworkInfo
            return if (networkINfo != null && networkINfo.type == ConnectivityManager.TYPE_WIFI) {
                true
            } else false
        }

        /**
         * 描述：得到所有的WiFi列表.
         * 此方法需要如下两个权限
         */
        fun getScanResults(context: Context): List<ScanResult>? {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            var list: List<ScanResult>? = null
            //开始扫描WiFi
            @SuppressLint("MissingPermission") val f = wifiManager.startScan()
            if (!f) {
                getScanResults(context)
            } else {
                // 得到扫描结果
                list = wifiManager.scanResults
            }

            return list
        }

        /**
         * 描述：获取连接的WIFI信息.
         * 此方法需要如下权限
         */
        fun getConnectionInfo(context: Context): WifiInfo {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            return wifiManager.connectionInfo
        }

        /**
         * 判断当前网络是否是移动数据网络.
         *
         * @param context the context
         * @return boolean
         */
        fun isMobileConnection(context: Context): Boolean {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE
        }

        /**
         * 打开网络设置界面
         */
        fun openSettingNetActivity(context: Context) {
            val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        /**
         * 开启或关闭数据开关
         * 5.0以上没用
         *
         * @param cxt
         * @param state
         */
        fun setDataConnectionState(cxt: Context, state: Boolean) {
            var connectivityManager: ConnectivityManager? = null
            var connectivityManagerClz: Class<*>? = null
            try {
                connectivityManager = cxt
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                connectivityManagerClz = connectivityManager.javaClass
                val methods = connectivityManagerClz.methods
                for (method in methods) {
                    Log.i("Android数据连接管理", method.toGenericString())
                }
                val method = connectivityManagerClz.getMethod(
                    "setMobileDataEnabled", *arrayOf<Class<*>>(Boolean::class.javaPrimitiveType!!)
                )
                method.invoke(connectivityManager, state)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}