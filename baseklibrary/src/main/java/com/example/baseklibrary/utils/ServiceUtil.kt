package com.example.baseklibrary.utils

import android.app.ActivityManager
import android.content.Context

/**
 * Created by wangqiang on 2019/5/20.
 */
class ServiceUtil {
    companion object {
        fun isServiceExisted(context: Context, className: String): Boolean {
            val activityManager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE)

            if (serviceList.size <= 0) {
                return false
            }

            for (i in serviceList.indices) {
                val serviceInfo = serviceList[i]
                val serviceName = serviceInfo.service

                if (serviceName.className == className) {
                    return true
                }
            }
            return false
        }
    }
}