package com.example.baseklibrary.utils

import android.content.Context
import android.os.Environment
import java.io.*

/**
 * Created by wangqiang on 2019/5/20.
 */
class FileUtil {
    companion object {
        /**
         * 在指定的位置创建指定的文件
         *
         * @param filePath 完整的文件路径
         * @param mkdir    是否创建相关的文件夹
         * @throws Exception
         */
        @Throws(Exception::class)
        fun mkFile(filePath: String, mkdir: Boolean) {
            var file: File? = File(filePath)
            file!!.parentFile.mkdirs()
            file.createNewFile()
            file = null
        }

        /**
         * 在指定的位置创建文件夹
         *
         * @param dirPath 文件夹路径
         * @return 若创建成功，则返回True；反之，则返回False
         */
        fun mkDir(dirPath: String): Boolean {
            return File(dirPath).mkdirs()
        }

        /**
         * 删除指定的文件
         *
         * @param filePath 文件路径
         * @return 若删除成功，则返回True；反之，则返回False
         */
        fun delFile(filePath: String): Boolean {
            return File(filePath).delete()
        }

        /**
         * 删除指定的文件夹
         *
         * @param dirPath 文件夹路径
         * @param delFile 文件夹中是否包含文件
         * @return 若删除成功，则返回True；反之，则返回False
         */
        fun delDir(dirPath: String, delFile: Boolean): Boolean {
            if (delFile) {
                val file = File(dirPath)
                if (file.isFile) {
                    return file.delete()
                } else if (file.isDirectory) {
                    if (file.listFiles().size == 0) {
                        return file.delete()
                    } else {
                        val zfiles = file.listFiles().size
                        val delfile = file.listFiles()
                        for (i in 0 until zfiles) {
                            if (delfile[i].isDirectory) {
                                delDir(delfile[i].absolutePath, true)
                            }
                            delfile[i].delete()
                        }
                        return file.delete()
                    }
                } else {
                    return false
                }
            } else {
                return File(dirPath).delete()
            }
        }

        /**
         * 复制文件/文件夹 若要进行文件夹复制，请勿将目标文件夹置于源文件夹中
         *
         * @param source   源文件（夹）
         * @param target   目标文件（夹）
         * @param isFolder 若进行文件夹复制，则为True；反之为False
         * @throws Exception
         */
        @Throws(Exception::class)
        fun copy(source: String, target: String, isFolder: Boolean) {
            if (isFolder) {
                File(target).mkdirs()
                val a = File(source)
                val file = a.list()
                var temp: File? = null
                for (i in file.indices) {
                    if (source.endsWith(File.separator)) {
                        temp = File(source + file[i])
                    } else {
                        temp = File(source + File.separator + file[i])
                    }
                    if (temp.isFile) {
                        val input = FileInputStream(temp)
                        val output = FileOutputStream(target + "/" + temp.name.toString())
                        val b = ByteArray(1024)
                        var len: Int = input.read(b)
                        while (len != -1) {
                            output.write(b, 0, len)
                        }
                        output.flush()
                        output.close()
                        input.close()
                    }
                    if (temp.isDirectory) {
                        copy(source + "/" + file[i], target + "/" + file[i], true)
                    }
                }
            } else {
                var byteread = 0
                val oldfile = File(source)
                if (oldfile.exists()) {
                    val inStream = FileInputStream(source)
                    val file = File(target)
                    file.parentFile.mkdirs()
                    file.createNewFile()
                    val fs = FileOutputStream(file)
                    val buffer = ByteArray(1024)
                    byteread = inStream.read(buffer)
                    while (byteread != -1) {
                        fs.write(buffer, 0, byteread)
                    }
                    inStream.close()
                    fs.close()
                }
            }
        }

        /**
         * 移动指定的文件（夹）到目标文件（夹）
         *
         * @param source   源文件（夹）
         * @param target   目标文件（夹）
         * @param isFolder 若为文件夹，则为True；反之为False
         * @return
         * @throws Exception
         */
        @Throws(Exception::class)
        fun move(source: String, target: String, isFolder: Boolean): Boolean {
            copy(source, target, isFolder)
            return if (isFolder) {
                delDir(source, true)
            } else {
                delFile(source)
            }
        }

        /**
         * 获取缓存文件夹
         *
         * @param context
         * @return
         */
        fun getDiskCacheDir(context: Context): String {
            val cachePath: String
            //isExternalStorageEmulated()设备的外存是否是用内存模拟的，是则返回true
            if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageEmulated()) {
                cachePath = context.externalCacheDir!!.absolutePath
            } else {
                cachePath = context.cacheDir.absolutePath
            }
            return cachePath
        }

        /**
         * 将数据流保存为文件
         *
         * @param input
         * 要保存的数据流
         * @param file
         * 保存后的文件
         * @return true 保存成功，false 保存失败
         */
        fun saveStreamAsFile(input: InputStream?, file: File): Boolean {
            if (GetPhoneState.isSDCardAvailable()) {
                var os: OutputStream? = null
                var buffer: ByteArray? = null
                try {
                    buffer = ByteArray(input!!.available())
                    val work = file.parentFile
                    if (!work.exists()) {
                        work.mkdirs()
                    }
                    os = BufferedOutputStream(FileOutputStream(file))
                    var length = input.read(buffer)
                    while (length != -1) {
                        os.write(buffer, 0, length)
                    }
                    os.flush()
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                    return false
                } finally {
                    try {
                        os?.close()
                        input?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            } else {
                return false
            }
        }
    }
}