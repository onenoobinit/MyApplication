package com.example.baseklibrary.utils

import android.util.Log
import com.example.baseklibrary.base.BaseApplication

/**
 * Created by wangqiang on 2019/5/20.
 */
class L {
    companion object {
        // 是否需要打印log，可以在application的onCreate函数里面初始化
        var isDebug = ApplicationUtils.isApkDebugable(BaseApplication.instance)
        private val TAG = "MWY"

        // 下面四个是默认tag的函数
        fun i(msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.i(TAG, msg)
            }
        }

        fun d(msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.d(TAG, msg)
            }
        }

        fun e(msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.e(TAG, msg)
            }
        }

        fun v(msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.v(TAG, msg)
            }
        }

        // 下面是传入自定义tag的函数
        fun i(tag: String, msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.i(tag, msg)
            }
        }

        fun d(tag: String, msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.d(tag, msg)
            }
        }

        fun e(tag: String, msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.e(tag, msg)
            }
        }

        fun v(tag: String, msg: String) {
            var msg = msg
            if (isDebug) {
                msg = replaceNullMsg(msg)
                Log.v(tag, msg)
            }
        }

        private fun replaceNullMsg(msg: String?): String {
            var msg = msg
            if (msg == null) {
                msg = "(Warning: log print is null!)"
            } else if ("" == msg.trim { it <= ' ' }) {
                msg = "(Warning: log print is space!)"
            }
            return msg
        }
    }
}