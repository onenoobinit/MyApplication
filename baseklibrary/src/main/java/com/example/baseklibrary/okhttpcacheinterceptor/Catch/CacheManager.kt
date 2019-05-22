package com.example.baseklibrary.okhttpcacheinterceptor.Catch

import android.content.Context
import android.content.pm.PackageManager
import com.example.baseklibrary.utils.L
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.Executors

/**
 * Created by wangqiang on 2019/5/22.
 */
class CacheManager private constructor(context: Context) {
    private val cachedThreadPool = Executors.newCachedThreadPool()

    private var mDiskLruCache: DiskLruCache? = null


    @Throws(Exception::class)
    fun delete(context: Context) {
        val diskCacheDir = getDiskCacheDir(context, CACHE_DIR)
        if (mDiskLruCache != null) {
            DiskLruCache.deleteContents(diskCacheDir)
        }
    }

    init {
        val diskCacheDir = getDiskCacheDir(context, CACHE_DIR)
        if (!diskCacheDir.exists()) {
            val b = diskCacheDir.mkdirs()
            L.d(TAG, "!diskCacheDir.exists() --- diskCacheDir.mkdirs()=$b")
        }
        if (diskCacheDir.usableSpace > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(
                    diskCacheDir,
                    getAppVersion(context), 1/*一个key对应多少个文件*/, DISK_CACHE_SIZE
                )
                L.d(TAG, "mDiskLruCache created")
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 同步设置缓存
     */
    fun putCache(key: String, value: String) {
        if (mDiskLruCache == null)
            return
        var os: OutputStream? = null
        try {
            val editor = mDiskLruCache!!.edit(encryptMD5(key))
            os = editor.newOutputStream(DISK_CACHE_INDEX)
            os!!.write(value.toByteArray())
            os.flush()
            editor.commit()
            mDiskLruCache!!.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (os != null) {
                try {
                    os.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }

    /**
     * 异步设置缓存
     */
    fun setCache(key: String, value: String) {
        cachedThreadPool.submit { putCache(key, value) }
    }

    /**
     * 同步获取缓存
     */
    fun getCache(key: String): String? {
        if (mDiskLruCache == null) {
            return null
        }
        var fis: FileInputStream? = null
        var bos: ByteArrayOutputStream? = null
        try {
            val snapshot = mDiskLruCache!![encryptMD5(key)]
            if (snapshot != null) {
                fis = snapshot.getInputStream(DISK_CACHE_INDEX) as FileInputStream
                bos = ByteArrayOutputStream()
                val buf = ByteArray(1024)
                var len: Int = fis.read(buf)
                while (len != -1) {
                    bos.write(buf, 0, len)
                }
                val data = bos.toByteArray()
                return String(data)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            if (bos != null) {
                try {
                    bos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return null
    }

    /**
     * 异步获取缓存
     */
    fun getCache(key: String, callback: CacheCallback) {
        cachedThreadPool.submit {
            val cache = getCache(key)
            if (cache != null) {
                callback.onGetCache(cache)
            }
        }
    }

    /**
     * 移除缓存
     */
    fun removeCache(key: String): Boolean {
        if (mDiskLruCache != null) {
            try {
                return mDiskLruCache!!.remove(encryptMD5(key))
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return false
    }

    /**
     * 获取缓存目录
     */
    private fun getDiskCacheDir(context: Context, uniqueName: String): File {
        val cachePath = context.cacheDir.path
        return File(cachePath + File.separator + uniqueName)
    }

    /**
     * 获取APP版本号
     */
    private fun getAppVersion(context: Context): Int {
        val pm = context.packageManager
        try {
            val pi = pm.getPackageInfo(context.packageName, 0)
            return pi?.versionCode ?: 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return 0
    }

    companion object {

        val TAG = "CacheManager"

        //max cache size 10mb
        private val DISK_CACHE_SIZE = (1024 * 1024 * 10).toLong()

        private val DISK_CACHE_INDEX = 0

        private val CACHE_DIR = "responses"

        @Volatile
        private var mCacheManager: CacheManager? = null

        fun getInstance(context: Context?): CacheManager? {
            if (mCacheManager == null) {
                synchronized(CacheManager::class.java) {
                    if (mCacheManager == null) {
                        mCacheManager = CacheManager(context!!)
                    }
                }
            }
            return mCacheManager
        }

        /**
         * 对字符串进行MD5编码
         */
        fun encryptMD5(string: String): String {
            try {
                val hash = MessageDigest.getInstance("MD5").digest(
                    string.toByteArray(charset("UTF-8"))
                )
                val hex = StringBuilder(hash.size * 2)
                for (b in hash) {
                    if ((b < 0x10) and (0xFF < 0x10)) {
                        hex.append("0")
                    }
                    hex.append(Integer.toHexString(b.toInt()))
                    hex.append(Integer.toHexString(0xFF))
                }
                return hex.toString()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return string
        }
    }
}