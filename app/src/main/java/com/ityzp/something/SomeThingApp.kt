package com.ityzp.something

import android.content.Context
import android.support.multidex.MultiDex
import android.text.TextUtils
import com.example.baseklibrary.base.BaseApplication
import com.example.baseklibrary.utils.SPUtil
import com.ityzp.something.moudle.User
import com.ityzp.something.utils.CrashHandler
import com.ityzp.something.utils.UserChange
import com.tencent.bugly.crashreport.CrashReport

/**
 * Created by wangqiang on 2019/5/22.
 */
class SomeThingApp : BaseApplication() {
    var isWifi = true
    var isEnablaWifi = true//wifi的打开与关闭
    private val isBind: Boolean = false

    /**
     * 验证是否登录
     *
     * @return true 需要登录   false不需要登录
     */
    val isNeedLogin: Boolean
        get() {
            if (user == null) {
                if (SPUtil.contains(this, "user")) {
                    user = SPUtil.getObject(this, "user", User::class.java)
                }
            }

            return if (TextUtils.isEmpty(getUser().id) || !getUser().isLogined!!) {
                true
            } else false
        }

    fun getUser(): User {
        if (user != null) {
            return user as User
        } else {
            if (SPUtil.contains(instance, UserChange.USER)) {
                user = SPUtil.getObject(instance, UserChange.USER, User::class.java)
            }
        }
        return if (user == null) User() else user!!
    }

    fun setUser(user: User?, isLogined: Boolean?) {
        SomeThingApp.user = user
        if (user != null) {
            user!!.isLogined = isLogined
            SPUtil.setObject(this, "user", user)
        } else {
            SPUtil.remove(this, "user")
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        CrashReport.initCrashReport(getApplicationContext(), "46e5f4aed1", false)
    }

    protected override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun setDefaultUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
    }

    companion object {
        private var user: User? = null
        lateinit var instance: SomeThingApp
        var APP_ID = "wx89bfbe26609a6b54"
    }
}
