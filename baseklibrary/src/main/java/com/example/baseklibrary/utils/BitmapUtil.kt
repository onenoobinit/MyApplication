package com.example.baseklibrary.utils

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 * Created by wangqiang on 2019/5/20.
 */
class BitmapUtil {
    companion object {
        fun drawableToBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(

                drawable.intrinsicWidth,

                drawable.intrinsicHeight,

                if (drawable.opacity != PixelFormat.OPAQUE)
                    Bitmap.Config.ARGB_8888
                else
                    Bitmap.Config.RGB_565
            )

            val canvas = Canvas(bitmap)

            //canvas.setBitmap(bitmap);

            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

            drawable.draw(canvas)

            return bitmap
        }

        fun saveBitmap(
            bitmap: Bitmap,
            dir: String,
            name: String,
            isShowPhotos: Boolean,
            application: Application
        ): Boolean {
            val path = File(dir)
            if (!path.exists()) {
                path.mkdirs()
            }
            val file = File("$path/$name")
            if (file.exists()) {
                file.delete()
            }
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return false
                }

            } else {
                return true
            }
            var fileOutputStream: FileOutputStream? = null
            try {
                fileOutputStream = FileOutputStream(file)
                bitmap.compress(
                    Bitmap.CompressFormat.PNG, 100,
                    fileOutputStream
                )
                fileOutputStream.flush()

            } catch (e: Exception) {
                e.printStackTrace()
                return false
            } finally {
                try {
                    fileOutputStream!!.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            // 其次把文件插入到系统图库
            if (isShowPhotos) {
                try {
                    MediaStore.Images.Media.insertImage(
                        application.contentResolver,
                        file.absolutePath, name, null
                    )
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }

                // 最后通知图库更新
                application.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://$file")))
            }

            return true
        }

        fun compressBitmapToBytes(
            filePath: String,
            reqWidth: Int,
            reqHeight: Int,
            quality: Int,
            format: Bitmap.CompressFormat
        ): ByteArray {
            val bitmap = getSmallBitmap(filePath, reqWidth, reqHeight)
            val baos = ByteArrayOutputStream()
            bitmap.compress(format, quality, baos)
            val bytes = baos.toByteArray()
            bitmap.recycle()
            L.i("Bitmap compressed success, size: " + bytes.size)
            return bytes
        }

        fun getSmallBitmap(filePath: String, reqWidth: Int, reqHeight: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filePath, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            //      options.inPreferQualityOverSpeed = true;
            return BitmapFactory.decodeFile(filePath, options)
        }

        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            val h = options.outHeight
            val w = options.outWidth
            var inSampleSize = 0
            if (h > reqHeight || w > reqWidth) {
                val ratioW = w.toFloat() / reqWidth
                val ratioH = h.toFloat() / reqHeight
                inSampleSize = Math.min(ratioH, ratioW).toInt()
            }
            inSampleSize = Math.max(1, inSampleSize)
            return inSampleSize
        }
    }
}