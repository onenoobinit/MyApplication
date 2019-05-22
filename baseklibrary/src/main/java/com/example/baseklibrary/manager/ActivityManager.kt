package com.example.baseklibrary.manager

import android.app.Activity
import android.content.Context
import java.util.*

/**
 * Created by wangqiang on 2019/5/22.
 */
class ActivityManager {

    fun isContains(cls: Class<*>): Boolean {
        if (null != activityStack) {
            for (activity in activityStack!!) {
                if (activity.javaClass == cls) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * 添加指定Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity
     */
    fun currentActivity(): Activity {
        return activityStack!!.lastElement()
    }

    /**
     * 结束当前Activity
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定Class的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack!!) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                return
            }
        }
    }

    /**
     * 结束全部的Activity
     */
    fun finishAllActivity() {
        if (null != activityStack) {
            for (activity in activityStack!!) {
                activity?.finish()
            }
            activityStack!!.clear()
        }
        System.exit(0)
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val activityMgr = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            activityMgr.restartPackage(context.packageName)
            System.exit(0)
        } catch (e: Exception) {
        }

    }

    companion object {
        private var activityStack: Stack<Activity>? = null
        var instance: ActivityManager? = null
            private set

        init {
            instance = ActivityManager()
        }
    }
}
