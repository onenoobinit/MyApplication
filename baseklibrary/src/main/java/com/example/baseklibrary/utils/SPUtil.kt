package com.example.baseklibrary.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.io.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Created by wangqiang on 2019/5/20.
 */
class SPUtil {
    companion object {
        /**
         * 保存在手机里面的文件名
         */
        val FILE_NAME = "share_data"

        /**
         * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
         *
         * @param context
         * @param key
         * @param object
         */
        fun put(context: Context, key: String, `object`: Any) {

            val sp = context.getSharedPreferences(
                FILE_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sp.edit()

            if (`object` is String) {
                editor.putString(key, `object`)
            } else if (`object` is Int) {
                editor.putInt(key, `object`)
            } else if (`object` is Boolean) {
                editor.putBoolean(key, `object`)
            } else if (`object` is Float) {
                editor.putFloat(key, `object`)
            } else if (`object` is Long) {
                editor.putLong(key, `object`)
            } else {
                editor.putString(key, `object`.toString())
            }
            SharedPreferencesCompat.apply(editor)
        }


        /**
         * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
         *
         * @param context
         * @param key
         * @param defaultObject
         * @return
         */
        operator fun get(context: Context, key: String, defaultObject: Any): Any? {
            val sp = context.getSharedPreferences(
                FILE_NAME,
                Context.MODE_PRIVATE
            )
            if (defaultObject is String) {
                return sp.getString(key, defaultObject)
            } else if (defaultObject is Int) {
                return sp.getInt(key, defaultObject)
            } else if (defaultObject is Boolean) {
                return sp.getBoolean(key, defaultObject)
            } else if (defaultObject is Float) {
                return sp.getFloat(key, defaultObject)
            } else if (defaultObject is Long) {
                return sp.getLong(key, defaultObject)
            }
            return null
        }

        /**
         * 针对复杂类型存储<对象>
         *
         * @param key
         * @param object
        </对象> */
        fun setObject(context: Context, key: String, `object`: Any) {
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

            val baos = ByteArrayOutputStream()
            var out: ObjectOutputStream? = null
            try {

                out = ObjectOutputStream(baos)
                out.writeObject(`object`)
                val objectVal = String(Base64.encode(baos.toByteArray(), Base64.DEFAULT))
                val editor = sp.edit()
                editor.putString(key, objectVal)
                editor.commit()

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    baos?.close()
                    out?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        fun <T> getObject(context: Context, key: String, clazz: Class<T>): T? {
            val sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            if (sp.contains(key)) {
                val objectVal = sp.getString(key, null)
                val buffer = Base64.decode(objectVal, Base64.DEFAULT)
                val bais = ByteArrayInputStream(buffer)
                var ois: ObjectInputStream? = null
                try {
                    ois = ObjectInputStream(bais)
                    return ois.readObject() as T
                } catch (e: StreamCorruptedException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } finally {
                    try {
                        bais?.close()
                        ois?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
            return null
        }

        /**
         * 对于外部不可见的过渡方法
         *
         * @param key
         * @param clazz
         * @param sp
         * @return
         */
        private fun <T> getValue(key: String, clazz: Class<T>, sp: SharedPreferences): T? {
            val t: T
            try {

                t = clazz.newInstance()

                if (t is Int) {
                    return Integer.valueOf(sp.getInt(key, 0)) as T
                } else if (t is String) {
                    return sp.getString(key, "") as T
                } else if (t is Boolean) {
                    return java.lang.Boolean.valueOf(sp.getBoolean(key, false)) as T
                } else if (t is Long) {
                    return java.lang.Long.valueOf(sp.getLong(key, 0L)) as T
                } else if (t is Float) {
                    return java.lang.Float.valueOf(sp.getFloat(key, 0f)) as T
                }
            } catch (e: InstantiationException) {
                e.printStackTrace()
                L.e("system", "类型输入错误或者复杂类型无法解析[" + e.message + "]")
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
                L.e("system", "类型输入错误或者复杂类型无法解析[" + e.message + "]")
            }

            L.e("system", "无法找到" + key + "对应的值")
            return null
        }


        /**
         * 移除某个key值已经对应的值
         *
         * @param context
         * @param key
         */
        fun remove(context: Context, key: String) {
            val sp = context.getSharedPreferences(
                FILE_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sp.edit()
            editor.remove(key)
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * 清除所有数据
         *
         * @param context
         */
        fun clear(context: Context) {
            val sp = context.getSharedPreferences(
                FILE_NAME,
                Context.MODE_PRIVATE
            )
            val editor = sp.edit()
            editor.clear()
            SharedPreferencesCompat.apply(editor)
        }

        /**
         * 查询某个key是否已经存在
         *
         * @param context
         * @param key
         * @return
         */
        fun contains(context: Context, key: String): Boolean {
            val sp = context.getSharedPreferences(
                FILE_NAME,
                Context.MODE_PRIVATE
            )
            return sp.contains(key)
        }

        /**
         * 返回所有的键值对
         *
         * @param context
         * @return
         */
        fun getAll(context: Context): Map<String, *> {
            val sp = context.getSharedPreferences(
                FILE_NAME,
                Context.MODE_PRIVATE
            )
            return sp.all
        }

        /**
         * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
         *
         * @author zhy
         */
        private object SharedPreferencesCompat {
            private val sApplyMethod = findApplyMethod()

            /**
             * 反射查找apply的方法
             *
             * @return
             */
            private fun findApplyMethod(): Method? {
                try {
                    val clz = SharedPreferences.Editor::class.java
                    return clz.getMethod("apply")
                } catch (e: NoSuchMethodException) {
                }

                return null
            }

            /**
             * 如果找到则使用apply执行，否则使用commit
             *
             * @param editor
             */
            fun apply(editor: SharedPreferences.Editor) {
                try {
                    if (sApplyMethod != null) {
                        sApplyMethod.invoke(editor)
                        return
                    }
                } catch (e: IllegalArgumentException) {
                } catch (e: IllegalAccessException) {
                } catch (e: InvocationTargetException) {
                }

                editor.commit()
            }
        }
    }
}