package com.example.baseklibrary.utils

import android.text.TextPaint
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by wangqiang on 2019/5/20.
 */
class StringUtils {
    companion object {
        val EMPTY = ""

        private val DEFAULT_DATE_PATTERN = "yyyy-MM-dd"

        private val DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd hh:mm:ss"

        /**
         * 用于生成文件
         */
        private val DEFAULT_FILE_PATTERN = "yyyy-MM-dd-HH-mm-ss"

        private val KB = 1024.0

        private val MB = 1048576.0

        private val GB = 1073741824.0

        val DATE_FORMAT_PART = SimpleDateFormat("HH:mm")

        fun currentTimeString(): String {

            return DATE_FORMAT_PART.format(Calendar.getInstance().time)
        }

        fun chatAt(pinyin: String?, index: Int): Char {

            return if (pinyin != null && pinyin.length > 0) pinyin[index] else ' '
        }

        /**
         * 获取字符串宽度
         */
        fun GetTextWidth(Sentence: String, Size: Float): Float {

            if (isEmpty(Sentence))
                return 0f
            val FontPaint = TextPaint()
            FontPaint.textSize = Size
            return FontPaint.measureText(Sentence.trim { it <= ' ' }) + (Size * 0.1).toInt() // 留点余地
        }

        /**
         * 格式化日期字符串
         *
         * @param date
         * @param pattern
         * @return
         */
        fun formatDate(date: Date, pattern: String): String {

            val format = SimpleDateFormat(pattern)
            return format.format(date)
        }

        /**
         * 格式化日期字符串
         *
         * @param date
         * @return 例如2011-3-24
         */
        fun formatDate(date: Date): String {

            return formatDate(date, DEFAULT_DATE_PATTERN)
        }

        fun formatDate(date: Long): String {

            return formatDate(Date(date), DEFAULT_DATE_PATTERN)
        }

        /**
         * 获取当前时间 格式为yyyy-MM-dd 例如2011-07-08
         *
         * @return
         */
        fun getDate(): String {

            return formatDate(Date(), DEFAULT_DATE_PATTERN)
        }

        /**
         * 生成一个文件名，不含后缀
         */
        fun createFileName(): String {

            val date = Date(System.currentTimeMillis())
            val format = SimpleDateFormat(DEFAULT_FILE_PATTERN)
            return format.format(date)
        }

        /**
         * 获取当前时间
         *
         * @return
         */
        fun getDateTime(): String {

            return formatDate(Date(), DEFAULT_DATETIME_PATTERN)
        }

        /**
         * 格式化日期时间字符串
         *
         * @param date
         * @return 例如2011-11-30 16:06:54
         */
        fun formatDateTime(date: Date): String {

            return formatDate(date, DEFAULT_DATETIME_PATTERN)
        }

        fun formatDateTime(date: Long): String {

            return formatDate(Date(date), DEFAULT_DATETIME_PATTERN)
        }

        /**
         * 格林威时间转换
         *
         * @param gmt
         * @return
         */
        fun formatGMTDate(gmt: String): String {

            val timeZoneLondon = TimeZone.getTimeZone(gmt)
            return formatDate(Calendar.getInstance(timeZoneLondon).timeInMillis)
        }

        /**
         * 拼接数组
         *
         * @param array
         * @param separator
         * @return
         */
        fun join(array: ArrayList<String>?, separator: String): String {

            val result = StringBuffer()
            if (array != null && array.size > 0) {
                for (str in array) {
                    result.append(str)
                    result.append(separator)
                }
                result.delete(result.length - 1, result.length)
            }
            return result.toString()
        }

        fun join(iter: Iterator<String>?, separator: String): String {

            val result = StringBuffer()
            if (iter != null) {
                while (iter.hasNext()) {
                    val key = iter.next()
                    result.append(key)
                    result.append(separator)
                }
                if (result.length > 0)
                    result.delete(result.length - 1, result.length)
            }
            return result.toString()
        }

        /**
         * 判断字符串是否为空
         *
         * @param str
         * @return
         */
        fun isEmpty(str: String?): Boolean {

            return str == null || str.length == 0
        }

        /**
         * @param str
         * @return
         */
        fun trim(str: String?): String {

            return str?.trim { it <= ' ' } ?: EMPTY
        }

        /**
         * 转换时间显示
         *
         * @param time 毫秒
         * @return
         */
        fun generateTime(time: Long): String {

            val totalSeconds = (time / 1000).toInt()
            val seconds = totalSeconds % 60
            val minutes = totalSeconds / 60 % 60
            val hours = totalSeconds / 3600

            return if (hours > 0) String.format(
                "%02d:%02d:%02d",
                hours,
                minutes,
                seconds
            ) else String.format("%02d:%02d", minutes, seconds)
        }

        /**
         * 根据秒速获取时间格式
         */
        fun gennerTime(totalSeconds: Int): String {

            val seconds = totalSeconds % 60
            val minutes = totalSeconds / 60 % 60
            return String.format("%02d:%02d", minutes, seconds)
        }

        /**
         * 转换文件大小
         *
         * @param size
         * @return
         */
        fun generateFileSize(size: Long): String {

            val fileSize: String
            if (size < KB)
                fileSize = size.toString() + "B"
            else if (size < MB)
                fileSize = String.format("%.1f", size / KB) + "KB"
            else if (size < GB)
                fileSize = String.format("%.1f", size / MB) + "MB"
            else
                fileSize = String.format("%.1f", size / GB) + "GB"

            return fileSize
        }

        fun getTimeDiff(time: Long): String {
            // Calendar cal = Calendar.getInstance();
            var diff: Long = 0
            // Date dnow = cal.getTime();
            var str = ""
            diff = System.currentTimeMillis() - time

            if (diff > 2592000000L) {// 30 * 24 * 60 * 60 * 1000=2592000000 毫秒
                str = "1个月前"
            } else if (diff > 1814400000) {// 21 * 24 * 60 * 60 * 1000=1814400000 毫秒
                str = "3周前"
            } else if (diff > 1209600000) {// 14 * 24 * 60 * 60 * 1000=1209600000 毫秒
                str = "2周前"
            } else if (diff > 604800000) {// 7 * 24 * 60 * 60 * 1000=604800000 毫秒
                str = "1周前"
            } else if (diff > 86400000) { // 24 * 60 * 60 * 1000=86400000 毫秒
                // System.out.println("X天前");
                str = Math.floor((diff / 86400000f).toDouble()).toInt().toString() + "天前"
            } else if (diff > 18000000) {// 5 * 60 * 60 * 1000=18000000 毫秒
                // System.out.println("X小时前");
                str = Math.floor((diff / 18000000f).toDouble()).toInt().toString() + "小时前"
            } else if (diff > 60000) {// 1 * 60 * 1000=60000 毫秒
                // System.out.println("X分钟前");
                str = Math.floor((diff / 60000).toDouble()).toInt().toString() + "分钟前"
            } else {
                str = Math.floor((diff / 1000).toDouble()).toInt().toString() + "秒前"
            }
            return str
        }

        /**
         * 截取字符串
         *
         * @param search       待搜索的字符串
         * @param start        起始字符串 例如：<title>
         * @param end          结束字符串 例如：</title>
         * @param defaultValue
         * @return
         */
        fun substring(search: String, start: String, end: String, defaultValue: String): String {

            val start_len = start.length
            val start_pos = if (StringUtils.isEmpty(start)) 0 else search.indexOf(start)
            if (start_pos > -1) {
                val end_pos = if (StringUtils.isEmpty(end)) -1 else search.indexOf(end, start_pos + start_len)
                return if (end_pos > -1)
                    search.substring(start_pos + start.length, end_pos)
                else
                    search.substring(start_pos + start.length)
            }
            return defaultValue
        }

        /**
         * 截取字符串
         *
         * @param search 待搜索的字符串
         * @param start  起始字符串 例如：<title>
         * @param end    结束字符串 例如：</title>
         * @return
         */
        fun substring(search: String, start: String, end: String): String {

            return substring(search, start, end, "")
        }

        /**
         * 拼接字符串
         *
         * @param strs
         * @return
         */
        fun concat(vararg strs: String): String {

            val result = StringBuffer()
            if (strs != null) {
                for (str in strs) {
                    if (str != null)
                        result.append(str)
                }
            }
            return result.toString()
        }

        /**
         * 获取中文字符个数
         */
        fun getChineseCharCount(str: String): Int {

            var tempStr: String
            var count = 0
            for (i in 0 until str.length) {
                tempStr = str[i].toString()
                if (tempStr.toByteArray().size == 3) {
                    count++
                }
            }
            return count
        }

        /**
         * 获取英文字符个数
         */
        fun getEnglishCount(str: String): Int {

            var tempStr: String
            var count = 0
            for (i in 0 until str.length) {
                tempStr = str[i].toString()
                if (tempStr.toByteArray().size != 3) {
                    count++
                }
            }
            return count
        }

        fun encode(url: String): String {

            try {
                return URLEncoder.encode(url, "UTF-8")
            } catch (e: UnsupportedEncodingException) {

            }

            return url
        }

        /**
         * is null or its length is 0 or it is made by space
         *
         *
         * <pre>
         * isBlank(null) = true;
         * isBlank(&quot;&quot;) = true;
         * isBlank(&quot;  &quot;) = true;
         * isBlank(&quot;a&quot;) = false;
         * isBlank(&quot;a &quot;) = false;
         * isBlank(&quot; a&quot;) = false;
         * isBlank(&quot;a b&quot;) = false;
        </pre> *
         *
         * @param str str
         * @return if string is null or its size is 0 or it is made by space, return
         * true, else return false.
         */
        fun isBlank(str: String?): Boolean {

            return str == null || str.trim { it <= ' ' }.length == 0
        }


        /**
         * is null or its length is 0
         *
         *
         * <pre>
         * isEmpty(null) = true;
         * isEmpty(&quot;&quot;) = true;
         * isEmpty(&quot;  &quot;) = false;
        </pre> *
         *
         * @param str str
         * @return if string is null or its size is 0, return true, else return
         * false.
         */
        fun isEmpty(str: CharSequence?): Boolean {

            return str == null || str.length == 0
        }


        /**
         * get length of CharSequence
         *
         *
         * <pre>
         * length(null) = 0;
         * length(\"\") = 0;
         * length(\"abc\") = 3;
        </pre> *
         *
         * @param str str
         * @return if str is null or empty, return 0, else return [ ][CharSequence.length].
         */
        fun length(str: CharSequence?): Int {

            return str?.length ?: 0
        }


        /**
         * null Object to empty string
         *
         *
         * <pre>
         * nullStrToEmpty(null) = &quot;&quot;;
         * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
         * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
        </pre> *
         *
         * @param str str
         * @return String
         */
        fun nullStrToEmpty(str: Any?): String {

            return if (str == null)
                ""
            else
                str as? String ?: str.toString()
        }


        /**
         * @param str str
         * @return String
         */
        fun capitalizeFirstLetter(str: String): String? {

            if (isEmpty(str)) {
                return str
            }

            val c = str[0]
            return if (!Character.isLetter(c) || Character.isUpperCase(c))
                str
            else
                StringBuilder(str.length).append(
                    Character.toUpperCase(c)
                )
                    .append(str.substring(1))
                    .toString()
        }


        /**
         * encoded in utf-8
         *
         * @param str 字符串
         * @return 返回一个utf8的字符串
         */
        fun utf8Encode(str: String): String? {

            if (!isEmpty(str) && str.toByteArray().size != str.length) {
                try {
                    return URLEncoder.encode(str, "UTF-8")
                } catch (e: UnsupportedEncodingException) {
                    throw RuntimeException(
                        "UnsupportedEncodingException occurred. ", e
                    )
                }

            }
            return str
        }


        /**
         * @param href 字符串
         * @return 返回一个html
         */
        fun getHrefInnerHtml(href: String): String {

            if (isEmpty(href)) {
                return ""
            }

            val hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*"
            val hrefPattern = Pattern.compile(
                hrefReg,
                Pattern.CASE_INSENSITIVE
            )
            val hrefMatcher = hrefPattern.matcher(href)
            return if (hrefMatcher.matches()) {
                hrefMatcher.group(1)
            } else href
        }


        /**
         * @param source 字符串
         * @return 返回htmL到字符串
         */
        fun htmlEscapeCharsToString(source: String): String? {

            return if (StringUtils.isEmpty(source))
                source
            else
                source.replace("&lt;".toRegex(), "<")
                    .replace("&gt;".toRegex(), ">")
                    .replace("&amp;".toRegex(), "&")
                    .replace("&quot;".toRegex(), "\"")
        }


        /**
         * @param s str
         * @return String
         */
        fun fullWidthToHalfWidth(s: String): String? {

            if (isEmpty(s)) {
                return s
            }

            val source = s.toCharArray()
            for (i in source.indices) {
                if (source[i].toInt() == 12288) {
                    source[i] = ' '
                    // } else if (source[i] == 12290) {
                    // source[i] = '.';
                } else if (source[i].toInt() >= 65281 && source[i].toInt() <= 65374) {
                    source[i] = (source[i].toInt() - 65248).toChar()
                } else {
                    source[i] = source[i]
                }
            }
            return String(source)
        }


        /**
         * @param s 字符串
         * @return 返回的数值
         */
        fun halfWidthToFullWidth(s: String): String? {

            if (isEmpty(s)) {
                return s
            }

            val source = s.toCharArray()
            for (i in source.indices) {
                if (source[i] == ' ') {
                    source[i] = 12288.toChar()
                    // } else if (source[i] == '.') {
                    // source[i] = (char)12290;
                } else if (source[i].toInt() >= 33 && source[i].toInt() <= 126) {
                    source[i] = (source[i].toInt() + 65248).toChar()
                } else {
                    source[i] = source[i]
                }
            }
            return String(source)
        }


        /**
         * @param str 资源
         * @return 特殊字符串切换
         */

        fun replaceBlanktihuan(str: String?): String {

            var dest = ""
            if (str != null) {
                val p = Pattern.compile("\\s*|\t|\r|\n")
                val m = p.matcher(str)
                dest = m.replaceAll("")
            }
            return dest
        }


        /**
         * 判断给定的字符串是否不为null且不为空
         *
         * @param string 给定的字符串
         */
        fun isNotEmpty(string: String): Boolean {
            return !isEmpty(string)
        }


        /**
         * 判断给定的字符串数组中的所有字符串是否都为null或者是空的
         *
         * @param strings 给定的字符串
         */
        fun isEmpty(vararg strings: String): Boolean {
            var result = true
            for (string in strings) {
                if (isNotEmpty(string)) {
                    result = false
                    break
                }
            }
            return result
        }


        /**
         * 判断给定的字符串数组中是否全部都不为null且不为空
         *
         * @param strings 给定的字符串数组
         * @return 是否全部都不为null且不为空
         */
        fun isNotEmpty(vararg strings: String): Boolean {
            var result = true
            for (string in strings) {
                if (isEmpty(string)) {
                    result = false
                    break
                }
            }
            return result
        }


        /**
         * 如果字符串是null或者空就返回""
         */
        fun filterEmpty(string: String): String {
            return if (StringUtils.isNotEmpty(string)) string else ""
        }


        /**
         * 在给定的字符串中，用新的字符替换所有旧的字符
         *
         * @param string  给定的字符串
         * @param oldchar 旧的字符
         * @param newchar 新的字符
         * @return 替换后的字符串
         */
        fun replace(string: String, oldchar: Char, newchar: Char): String {
            val chars = string.toCharArray()
            for (w in chars.indices) {
                if (chars[w] == oldchar) {
                    chars[w] = newchar
                    break
                }
            }
            return String(chars)
        }


        /**
         * 把给定的字符串用给定的字符分割
         *
         * @param string 给定的字符串
         * @param ch     给定的字符
         * @return 分割后的字符串数组
         */
        fun split(string: String, ch: Char): Array<String> {
            val stringList = ArrayList<String>()
            val chars = string.toCharArray()
            var nextStart = 0
            for (w in chars.indices) {
                if (ch == chars[w]) {
                    stringList.add(String(chars, nextStart, w - nextStart))
                    nextStart = w + 1
                    if (nextStart == chars.size) {    //当最后一位是分割符的话，就再添加一个空的字符串到分割数组中去
                        stringList.add("")
                    }
                }
            }
            if (nextStart < chars.size) {    //如果最后一位不是分隔符的话，就将最后一个分割符到最后一个字符中间的左右字符串作为一个字符串添加到分割数组中去
                stringList.add(
                    String(
                        chars, nextStart,
                        chars.size - 1 - nextStart + 1
                    )
                )
            }
            return stringList.toTypedArray()
        }


        /**
         * 计算给定的字符串的长度，计算规则是：一个汉字的长度为2，一个字符的长度为1
         *
         * @param string 给定的字符串
         * @return 长度
         */
        fun countLength(string: String): Int {
            var length = 0
            val chars = string.toCharArray()
            for (w in 0 until string.length) {
                val ch = chars[w]
                if (ch >= '\u0391' && ch <= '\uFFE5') {
                    length++
                    length++
                } else {
                    length++
                }
            }
            return length
        }


        private fun getChars(chars: CharArray, startIndex: Int): CharArray {
            var endIndex = startIndex + 1
            //如果第一个是数字
            if (Character.isDigit(chars[startIndex])) {
                //如果下一个是数字
                while (endIndex < chars.size && Character.isDigit(chars[endIndex])) {
                    endIndex++
                }
            }
            val resultChars = CharArray(endIndex - startIndex)
            System.arraycopy(chars, startIndex, resultChars, 0, resultChars.size)
            return resultChars
        }


        /**
         * 是否全是数字
         */
        fun isAllDigital(chars: CharArray): Boolean {
            var result = true
            for (w in chars.indices) {
                if (!Character.isDigit(chars[w])) {
                    result = false
                    break
                }
            }
            return result
        }


        /**
         * 删除给定字符串中所有的旧的字符
         *
         * @param string 源字符串
         * @param ch     要删除的字符
         * @return 删除后的字符串
         */
        fun removeChar(string: String, ch: Char): String {
            val sb = StringBuffer()
            for (cha in string.toCharArray()) {
                if (cha != '-') {
                    sb.append(cha)
                }
            }
            return sb.toString()
        }


        /**
         * 删除给定字符串中给定位置处的字符
         *
         * @param string 给定字符串
         * @param index  给定位置
         */
        fun removeChar(string: String, index: Int): String {
            var result: String? = null
            val chars = string.toCharArray()
            if (index == 0) {
                result = String(chars, 1, chars.size - 1)
            } else if (index == chars.size - 1) {
                result = String(chars, 0, chars.size - 1)
            } else {
                result = String(chars, 0, index) + String(chars, index + 1, chars.size - index)
            }
            return result
        }


        /**
         * 删除给定字符串中给定位置处的字符
         *
         * @param string 给定字符串
         * @param index  给定位置
         * @param ch     如果同给定位置处的字符相同，则将给定位置处的字符删除
         */
        fun removeChar(string: String, index: Int, ch: Char): String {
            var result: String? = null
            val chars = string.toCharArray()
            if (chars.size > 0 && chars[index] == ch) {
                if (index == 0) {
                    result = String(chars, 1, chars.size - 1)
                } else if (index == chars.size - 1) {
                    result = String(chars, 0, chars.size - 1)
                } else {
                    result = String(chars, 0, index) + String(chars, index + 1, chars.size - index)
                }
            } else {
                result = string
            }
            return result
        }


        /**
         * 对给定的字符串进行空白过滤
         *
         * @param string 给定的字符串
         * @return 如果给定的字符串是一个空白字符串，那么返回null；否则返回本身。
         */
        fun filterBlank(string: String): String? {
            return if ("" == string) {
                null
            } else {
                string
            }
        }


        /**
         * 将给定字符串中给定的区域的字符转换成小写
         *
         * @param str        给定字符串中
         * @param beginIndex 开始索引（包括）
         * @param endIndex   结束索引（不包括）
         * @return 新的字符串
         */
        fun toLowerCase(str: String, beginIndex: Int, endIndex: Int): String {
            return str.replaceFirst(
                str.substring(beginIndex, endIndex).toRegex(), str.substring(beginIndex, endIndex)
                    .toLowerCase(Locale.getDefault())
            )
        }


        /**
         * 将给定字符串中给定的区域的字符转换成大写
         *
         * @param str        给定字符串中
         * @param beginIndex 开始索引（包括）
         * @param endIndex   结束索引（不包括）
         * @return 新的字符串
         */
        fun toUpperCase(str: String, beginIndex: Int, endIndex: Int): String {
            return str.replaceFirst(
                str.substring(beginIndex, endIndex).toRegex(), str.substring(beginIndex, endIndex)
                    .toUpperCase(Locale.getDefault())
            )
        }


        /**
         * 将给定字符串的首字母转为小写
         *
         * @param str 给定字符串
         * @return 新的字符串
         */
        fun firstLetterToLowerCase(str: String): String {
            return toLowerCase(str, 0, 1)
        }


        /**
         * 将给定字符串的首字母转为大写
         *
         * @param str 给定字符串
         * @return 新的字符串
         */
        fun firstLetterToUpperCase(str: String): String {
            return toUpperCase(str, 0, 1)
        }


        /**
         * 将给定的字符串MD5加密
         *
         * @param string 给定的字符串
         * @return MD5加密后生成的字符串
         */
        fun MD5(string: String): String? {
            var result: String? = null
            try {
                val charArray = string.toCharArray()
                val byteArray = ByteArray(charArray.size)
                for (i in charArray.indices) {
                    byteArray[i] = charArray[i].toByte()
                }

                val hexValue = StringBuffer()
                val md5Bytes = MessageDigest.getInstance("MD5")
                    .digest(byteArray)
                for (i in md5Bytes.indices) {
                    val `val` = md5Bytes[i].toInt() and 0xff
                    if (`val` < 16) {
                        hexValue.append("0")
                    }
                    hexValue.append(Integer.toHexString(`val`))
                }

                result = hexValue.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }


        /**
         * 判断给定的字符串是否以一个特定的字符串开头，忽略大小写
         *
         * @param sourceString 给定的字符串
         * @param newString    一个特定的字符串
         */
        fun startsWithIgnoreCase(sourceString: String, newString: String): Boolean {
            val newLength = newString.length
            val sourceLength = sourceString.length
            if (newLength == sourceLength) {
                return newString.equals(sourceString, ignoreCase = true)
            } else if (newLength < sourceLength) {
                val newChars = CharArray(newLength)
                sourceString.toCharArray(newChars, 0, 0, newLength)
                return newString.equals(String(newChars), ignoreCase = true)
            } else {
                return false
            }
        }


        /**
         * 判断给定的字符串是否以一个特定的字符串结尾，忽略大小写
         *
         * @param sourceString 给定的字符串
         * @param newString    一个特定的字符串
         */
        fun endsWithIgnoreCase(sourceString: String, newString: String): Boolean {
            val newLength = newString.length
            val sourceLength = sourceString.length
            if (newLength == sourceLength) {
                return newString.equals(sourceString, ignoreCase = true)
            } else if (newLength < sourceLength) {
                val newChars = CharArray(newLength)
                sourceString.toCharArray(
                    newChars, 0, sourceLength - newLength, sourceLength
                )
                return newString.equals(String(newChars), ignoreCase = true)
            } else {
                return false
            }
        }


        /**
         * 检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上appendString
         */
        fun checkLength(string: String, maxLength: Int, appendString: String?): String {
            var string = string
            if (string.length > maxLength) {
                string = string.substring(0, maxLength)
                if (appendString != null) {
                    string += appendString
                }
            }
            return string
        }


        /**
         * 检查字符串长度，如果字符串的长度超过maxLength，就截取前maxLength个字符串并在末尾拼上…
         */
        fun checkLength(string: String, maxLength: Int): String {
            return checkLength(string, maxLength, "…")
        }
    }
}