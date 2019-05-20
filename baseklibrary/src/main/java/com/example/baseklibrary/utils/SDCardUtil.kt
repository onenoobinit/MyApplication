package com.example.baseklibrary.utils

import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.support.annotation.RequiresApi
import java.io.File

/**
 * Created by wangqiang on 2019/5/20.
 */
class SDCardUtil {
    companion object {
        /**
         * 判断SDCard是否可用
         *
         * @return
         */
        fun isSDCardEnable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 获取SD卡路径
         *
         * @return
         */
        fun getSDCardPath(): String {
            return Environment.getExternalStorageDirectory().absolutePath + File.separator
        }

        /**
         * 获取SD卡的剩余容量 单位byte
         *
         * @return
         */
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        fun getSDCardAllSize(): Long {
            if (isSDCardEnable()) {
                val statFs = StatFs(getSDCardPath()) as StatFs
                val availableBlocks = statFs.availableBlocksLong - 4
                val freeBlocks = statFs.availableBlocks
                return freeBlocks * availableBlocks
            }
            return 0
        }

        /**
         * 获取指定路径所在空间的剩余可用容量字节数，单位byte
         *
         * @param filePath
         * @return 容量字节 SDCard可用空间，内部存储可用空间
         */
        fun getFreeBytes(filePath: String): Long {
            var filePath = filePath
            // 如果是sd卡的下的路径，则获取sd卡可用容量
            if (filePath.startsWith(getSDCardPath())) {
                filePath = getSDCardPath()
            } else {
                // 如果是内部存储的路径，则获取内存存储的可用容量
                filePath = Environment.getDataDirectory().absolutePath
            }
            val stat = StatFs(filePath)
            val availableBlocks = stat.availableBlocks.toLong() - 4
            return stat.blockSize * availableBlocks
        }

        /**
         * 获取系统存储路径
         *
         * @return
         */
        fun getRootDirectoryPath(): String {
            return Environment.getRootDirectory().absolutePath
        }
    }
}