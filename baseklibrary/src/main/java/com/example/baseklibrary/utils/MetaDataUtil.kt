package com.example.baseklibrary.utils

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo

/**
 * Created by wangqiang on 2019/5/20.
 */
class MetaDataUtil {
    companion object {
        /**
         * 在application节点取metadata
         * <application>
         * <meta-data android:value="hello my application" android:name="data_Name"></meta-data>
        </application> *
         * @param context
         * @param key
         * @return
         */
        fun getFromApplication(context: Context, key: String): String? {
            var appInfo: ApplicationInfo? = null
            try {
                appInfo = context.packageManager
                    .getApplicationInfo(
                        context.packageName,
                        PackageManager.GET_META_DATA
                    )
                return appInfo!!.metaData.getString(key)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return ""
            }

        }

        /**
         * 在Activity应用<meta-data>元素。
         * xml代码段：
         * <activity...>
         * <meta-data android:name="data_Name" android:value="hello my activity"></meta-data>
         *
         * @param activity
         * @param key
         * @return
        </activity...></meta-data> */
        fun getFromActivity(activity: Activity, key: String): String? {
            var info: ActivityInfo? = null
            try {
                info = activity.packageManager
                    .getActivityInfo(
                        activity.componentName,
                        PackageManager.GET_META_DATA
                    )
                return info!!.metaData.getString(key)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return ""
            }

        }

        /**
         * 在service应用<meta-data>元素。
         * xml代码段：
         * <service android:name="MetaDataService">
         * <meta-data android:value="hello my service" android:name="data_Name"></meta-data>
        </service> *
         * @param context
         * @param key
         * @return
        </meta-data> */
        fun getFromService(context: Context, key: String, clazz: Class<*>): String? {
            var info: ServiceInfo? = null
            try {
                val cn = ComponentName(context, clazz)
                info = context.packageManager
                    .getServiceInfo(cn, PackageManager.GET_META_DATA)
                return info!!.metaData.getString(key)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return ""
            }

        }

        /**
         * 在receiver应用<meta-data>元素。
         * xml代码段:
         * <receiver android:name="MetaDataReceiver">
         * <meta-data android:value="hello my receiver" android:name="data_Name"></meta-data>
         * <intent-filter>
         * <action android:name="android.intent.action.PHONE_STATE"></action>
        </intent-filter> *
        </receiver> *
         * @param context
         * @param key
         * @param clazz
         * @return
        </meta-data> */
        fun getFromReceiver(context: Context, key: String, clazz: Class<*>): String? {
            var info: ActivityInfo? = null
            try {
                val cn = ComponentName(context, clazz)
                info = context.packageManager
                    .getReceiverInfo(cn, PackageManager.GET_META_DATA)
                return info!!.metaData.getString(key)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return ""
            }

        }
    }
}