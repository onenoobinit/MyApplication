package com.example.baseklibrary.utils

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import java.io.*

/**
 * Created by wangqiang on 2019/5/20.
 */
class ImageUtils {
    companion object {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        fun getBitmapSize(bitmap: Bitmap): Int {

            return if (DeviceUtils.hasHoneycombMR1()) {
                bitmap.byteCount
            } else bitmap.rowBytes * bitmap.height
            // Pre HC-MR1
        }

        /**
         * 旋转图片
         */
        fun rotate(b: Bitmap?, degrees: Int): Bitmap? {
            var b = b

            if (degrees != 0 && b != null) {
                val m = Matrix()
                m.setRotate(degrees.toFloat(), b.width.toFloat() / 2, b.height.toFloat() / 2)
                try {
                    val b2 = Bitmap.createBitmap(b, 0, 0, b.width, b.height, m, true)
                    if (b != b2) {
                        b.recycle()
                        b = b2
                    }
                } catch (ex: OutOfMemoryError) {

                } catch (ex: Exception) {

                }

            }
            return b
        }

        /**
         * Decode and sample down a bitmap from a file to the requested width and
         * height.
         *
         * @param filename  The full path of the file to decode
         * @param reqWidth  The requested width of the resulting bitmap
         * @param reqHeight The requested height of the resulting bitmap
         * @return A bitmap sampled down from the original with the same aspect
         * ratio and dimensions that are equal to or greater than the
         * requested width and height
         */
        @Synchronized
        fun decodeSampledBitmapFromFile(filename: String, reqWidth: Int, reqHeight: Int): Bitmap {

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(filename, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(filename, options)
        }

        /**
         * Calculate an inSampleSize for use in a
         * [BitmapFactory.Options] object when decoding
         * bitmaps using the decode* methods from [BitmapFactory]. This
         * implementation calculates the closest inSampleSize that will result in
         * the final decoded bitmap having a width and height equal to or larger
         * than the requested width and height. This implementation does not ensure
         * a power of 2 is returned for inSampleSize which can be faster when
         * decoding but results in a larger bitmap which isn't as useful for caching
         * purposes.
         *
         * @param options   An options object with out* params already populated (run
         * through a decode* method with inJustDecodeBounds==true
         * @param reqWidth  The requested width of the resulting bitmap
         * @param reqHeight The requested height of the resulting bitmap
         * @return The value to be used for inSampleSize
         */
        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                if (width > height) {
                    inSampleSize = Math.round(height.toFloat() / reqHeight.toFloat())
                } else {
                    inSampleSize = Math.round(width.toFloat() / reqWidth.toFloat())
                }

                // This offers some additional logic in case the image has a strange
                // aspect ratio. For example, a panorama may have a much larger
                // width than height. In these cases the total pixels might still
                // end up being too large to fit comfortably in memory, so we should
                // be more aggressive with sample down the image (=larger
                // inSampleSize).

                val totalPixels = (width * height).toFloat()

                // Anything more than 2x the requested pixels we'll sample down
                // further.
                val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

                while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                    inSampleSize++
                }
            }
            return inSampleSize
        }

        fun saveBitmap(path: String, bitmap: Bitmap): Boolean {

            return saveBitmap(File(path), bitmap)
        }

        /**
         * 保存图片到文件
         */
        fun saveBitmap(f: File, bitmap: Bitmap?): Boolean {

            if (bitmap == null || bitmap.isRecycled)
                return false

            var fOut: FileOutputStream? = null
            try {
                if (f.exists())
                    f.createNewFile()

                fOut = FileOutputStream(f)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)

                fOut.flush()
                return true
            } catch (e: FileNotFoundException) {

            } catch (e: IOException) {

            } catch (e: Exception) {

            } finally {
                if (fOut != null) {
                    try {
                        fOut.close()
                    } catch (e: IOException) {

                    }

                }
            }
            return false
        }

        fun decodeUriAsBitmap(ctx: Context, uri: Uri): Bitmap? {

            var bitmap: Bitmap? = null
            try {
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                // options.outWidth = reqWidth;
                // options.outHeight = reqHeight;
                BitmapFactory.decodeStream(ctx.contentResolver.openInputStream(uri), null, options)

                var be = (options.outHeight / 350.toFloat()).toInt()
                if (be <= 0)
                    be = 1
                options.inSampleSize = be// calculateInSampleSize(options,
                // reqWidth, reqHeight);

                options.inJustDecodeBounds = false
                bitmap = BitmapFactory.decodeStream(ctx.contentResolver.openInputStream(uri), null, options)
            } catch (e: FileNotFoundException) {

            } catch (e: OutOfMemoryError) {

            }

            return bitmap
        }

        fun zoomBitmap(bitmap: Bitmap, w: Int, h: Int): Bitmap {

            val width = bitmap.width
            val height = bitmap.height
            val matrix = Matrix()
            val scaleWidht = w.toFloat() / width
            val scaleHeight = h.toFloat() / height
            matrix.postScale(scaleWidht, scaleHeight)
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
        }

        fun drawableToBitmap(drawable: Drawable): Bitmap {

            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val bitmap = Bitmap.createBitmap(
                width,
                height,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * 获取图片圆角
         *
         * @param bitmap
         * @param roundPx 圆角度数
         * @return
         */
        fun getRoundedCornerBitmap(bitmap: Bitmap, roundPx: Float): Bitmap {

            val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)

            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)

            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        }

        /**
         * 转行Drawable为Bitmap对象
         *
         * @param drawable
         * @return
         */
        fun toBitmap(drawable: Drawable): Bitmap {

            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            val bitmap = Bitmap.createBitmap(
                width,
                height,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, width, height)
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * 缩放图片
         *
         * @param src       缩放原图
         * @param dstWidth  缩放后宽
         * @param dstHeight 缩放后高
         * @return
         */
        fun scaledBitmap(src: Bitmap?, dstWidth: Int, dstHeight: Int): Bitmap? {
            // 原图不能为空也不能已经被回收掉了
            var result: Bitmap? = null
            if (src != null && !src.isRecycled) {
                if (src.width == dstWidth && src.height == dstHeight) {
                    result = src
                } else {
                    result = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true)
                }
            }
            // ThumbnailUtils.extractThumbnail(source, width, height)
            return result
        }

        /**
         * 按比例缩放图片
         *
         * @param src
         * @param scale 例如2 就是二分之一
         * @return
         */
        fun scaledBitmap(src: Bitmap?, scale: Int): Bitmap? {

            if (src == null || src.isRecycled) {
                return null
            }
            val dstWidth = src.width / scale
            val dstHeight = src.height / scale
            return Bitmap.createScaledBitmap(src, dstWidth, dstHeight, true)
        }

        /**
         * 将图片转换成字节数组
         *
         * @param bitmap
         * @return
         */
        fun toBytes(bitmap: Bitmap): ByteArray {

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }
    }
}