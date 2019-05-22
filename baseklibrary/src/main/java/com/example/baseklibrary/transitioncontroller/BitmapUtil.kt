package com.example.baseklibrary.transitioncontroller

import android.graphics.Bitmap
import android.view.View

/**
 * Created by wangqiang on 2019/5/22.
 */
object BitmapUtil {
    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    fun getCacheBitmapFromView(view: View): Bitmap? {
        val drawingCacheEnabled = true
        view.isDrawingCacheEnabled = drawingCacheEnabled
        view.buildDrawingCache(drawingCacheEnabled)
        val drawingCache = view.drawingCache
        val bitmap: Bitmap?
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache)
            view.isDrawingCacheEnabled = false
        } else {
            bitmap = null
        }
        return bitmap
    }
}