package com.example.baseklibrary.utils

import android.content.Context
import android.support.annotation.UiThread
import android.view.Gravity
import android.widget.Toast

/**
 * Created by wangqiang on 2019/5/20.
 */
class ToastUtil {
    companion object {
        var isShow = true
        private var mToast: Toast? = null

        /**
         * 短时间显示Toast
         *
         * @param context
         * @param message
         */
        @UiThread
        fun showShort(context: Context, message: CharSequence) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                } else {
                    mToast!!.setText(message)
                    mToast!!.setDuration(Toast.LENGTH_SHORT)
                }
                mToast!!.setGravity(Gravity.BOTTOM, 0, 50)
                mToast!!.show()
            }
        }

        /**
         * 短时间显示Toast
         *
         * @param context
         * @param message
         */
        @UiThread
        fun showShort(context: Context, message: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                } else {
                    mToast!!.setText(message)
                    mToast!!.setDuration(Toast.LENGTH_SHORT)
                }
                mToast!!.setGravity(Gravity.BOTTOM, 0, 50)
                mToast!!.show()
            }
        }

        /**
         * 长时间显示Toast
         *
         * @param context
         * @param message
         */
        @UiThread
        fun showLong(context: Context, message: CharSequence) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                } else {
                    mToast!!.setText(message)
                    mToast!!.setDuration(Toast.LENGTH_LONG)
                }
                mToast!!.setGravity(Gravity.BOTTOM, 0, 50)
                mToast!!.show()
            }
        }

        /**
         * 长时间显示Toast
         *
         * @param context
         * @param message
         */
        @UiThread
        fun showLong(context: Context, message: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                } else {
                    mToast!!.setText(message)
                    mToast!!.setDuration(Toast.LENGTH_LONG)
                }
                mToast!!.setGravity(Gravity.BOTTOM, 0, 50)
                mToast!!.show()
            }
        }

        /**
         * 长时间显示Toast
         *
         * @param context
         * @param message
         */
        @UiThread
        fun showLong(context: Context, message: String, duration: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                } else {
                    mToast!!.setText(message)
                    mToast!!.setDuration(Toast.LENGTH_LONG)
                }
                mToast!!.setGravity(duration, 0, 0)
                mToast!!.show()
            }
        }

        /**
         * 自定义显示Toast时间
         *
         * @param context
         * @param message
         * @param duration
         */
        @UiThread
        fun show(context: Context, message: Int, duration: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, message, duration)
                } else {
                    mToast!!.setText(message)
                    mToast!!.setDuration(duration)
                }
                mToast!!.setGravity(Gravity.BOTTOM, 0, 50)
                mToast!!.show()
            }
        }

        @UiThread
        fun show(context: Context, content: String, Grivaty: Int) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
                } else {
                    mToast!!.setText(content)
                    mToast!!.setDuration(Toast.LENGTH_SHORT)
                }
                mToast!!.setGravity(Grivaty, 0, 0)
                mToast!!.show()
            }
        }

        /**
         * 默认短时间show
         *
         * @param context
         * @param content
         */
        @UiThread
        fun show(context: Context, content: String) {
            if (isShow) {
                if (mToast == null) {
                    mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
                } else {
                    mToast!!.setText(content)
                    mToast!!.setDuration(Toast.LENGTH_SHORT)
                }
                mToast!!.setGravity(Gravity.BOTTOM, 0, 50)
                mToast!!.show()
            }
        }
    }
}