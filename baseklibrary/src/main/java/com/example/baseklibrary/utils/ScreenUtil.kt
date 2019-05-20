package com.example.baseklibrary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import java.io.*

/**
 * Created by wangqiang on 2019/5/20.
 */
class ScreenUtil {
    companion object {
        /**
         * 获得屏幕宽度
         *
         * @param context
         * @return
         */
        fun getScreenWidth(context: Context): Int {
            val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.widthPixels
        }

        /**
         * 获得屏幕高度
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }

        /**
         * 获得状态栏的高度
         *
         * @param context
         * @return
         */
        fun getStatusHeight(context: Context): Int {

            var statusHeight = -1
            try {
                val clazz = Class.forName("com.android.internal.R\$dimen")
                val `object` = clazz.newInstance()
                val height = Integer.parseInt(
                    clazz.getField("status_bar_height")
                        .get(`object`).toString()
                )
                statusHeight = context.resources.getDimensionPixelSize(height)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return statusHeight
        }

        /**
         * 获取当前屏幕截图，包含状态栏
         *
         * @param activity
         * @return
         */
        fun snapShotWithStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val width = getScreenWidth(activity)
            val height = getScreenHeight(activity)
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
            view.destroyDrawingCache()
            return bp
        }

        /**
         * 获取当前屏幕截图，不包含状态栏
         *
         * @param activity
         * @return
         */
        fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
            val view = activity.window.decorView
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache()
            val bmp = view.drawingCache
            val frame = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(frame)
            val statusBarHeight = frame.top

            val width = getScreenWidth(activity)
            val height = getScreenHeight(activity)
            var bp: Bitmap? = null
            bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
            view.destroyDrawingCache()
            return bp
        }


        fun savePicture(context: Context, bm: Bitmap?, fileName: String) {
            Log.i("xing", "savePicture: ------------------------")
            if (null == bm) {
                Log.i("xing", "savePicture: ------------------图片为空------")
                return
            } //建立指定文件夹
            val foder = File(Environment.getExternalStorageDirectory(), "sjm_save")
            if (!foder.exists()) {
                foder.mkdirs()
            }
            val myCaptureFile = File(foder, fileName)
            try {
                if (!myCaptureFile.exists()) {
                    myCaptureFile.createNewFile()
                }
                val bos = BufferedOutputStream(FileOutputStream(myCaptureFile)) //压缩保存到本地
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bos)
                bos.flush()
                bos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // 把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.contentResolver, myCaptureFile.absolutePath, fileName, null)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }


        }
    }
}