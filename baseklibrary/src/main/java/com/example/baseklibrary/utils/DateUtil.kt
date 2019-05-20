package com.example.baseklibrary.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by wangqiang on 2019/5/20.
 */
class DateUtil {
    companion object {
        /**
         * 英文简写如：2016
         */
        var FORMAT_Y = "yyyy"

        /**
         * 英文简写如：12:01
         */
        var FORMAT_HM = "HH:mm"

        /**
         * 英文简写如：1-12 12:01
         */
        var FORMAT_MDHM = "MM-dd HH:mm"

        /**
         * 英文简写（默认）如：2016-12-01
         */
        var FORMAT_YMD = "yyyy-MM-dd"

        /**
         * 英文全称  如：2016-12-01 23:15
         */
        var FORMAT_YMDHM = "yyyy-MM-dd HH:mm"

        /**
         * 英文全称  如：2016-12-01 23:15:06
         */
        var FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss"

        /**
         * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
         */
        var FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S"

        /**
         * 精确到毫秒的完整时间    如：yyyy-MM-dd HH:mm:ss.S
         */
        var FORMAT_FULL_SN = "yyyyMMddHHmmssS"

        /**
         * 中文简写  如：2016年12月01日
         */
        var FORMAT_YMD_CN = "yyyy年MM月dd日"

        /**
         * 中文简写  如：2016年12月01日  12时
         */
        var FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时"

        /**
         * 中文简写  如：2016年12月01日  12时12分
         */
        var FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH时mm分"

        /**
         * 中文全称  如：2016年12月01日  23时15分06秒
         */
        var FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH时mm分ss秒"

        /**
         * 精确到毫秒的完整中文时间
         */
        var FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒"

        var calendar: Calendar? = null
        private val FORMAT = "yyyy-MM-dd HH:mm:ss"


        fun str2Date(str: String): Date? {
            return str2Date(str, null)
        }


        fun str2Date(str: String?, format: String?): Date? {
            var format = format
            if (str == null || str.length == 0) {
                return null
            }
            if (format == null || format.length == 0) {
                format = FORMAT
            }
            var date: Date? = null
            try {
                val sdf = SimpleDateFormat(format)
                date = sdf.parse(str)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return date
        }


        fun str2Calendar(str: String): Calendar? {
            return str2Calendar(str, null)
        }


        fun str2Calendar(str: String, format: String?): Calendar? {

            val date = str2Date(str, format) ?: return null
            val c = Calendar.getInstance()
            c.time = date

            return c
        }


        fun date2Str(c: Calendar): String? {// yyyy-MM-dd HH:mm:ss
            return date2Str(c, null)
        }


        fun date2Str(c: Calendar?, format: String?): String? {
            return if (c == null) {
                null
            } else date2Str(c.time, format)
        }


        fun date2Str(d: Date): String? {// yyyy-MM-dd HH:mm:ss
            return date2Str(d, null)
        }


        fun date2Str(d: Date?, format: String?): String? {// yyyy-MM-dd HH:mm:ss
            var format = format
            if (d == null) {
                return null
            }
            if (format == null || format.length == 0) {
                format = FORMAT
            }
            val sdf = SimpleDateFormat(format)
            return sdf.format(d)
        }


        fun getCurDateStr(): String {
            val c = Calendar.getInstance()
            c.time = Date()
            return c.get(Calendar.YEAR).toString() + "-" + (c.get(Calendar.MONTH) + 1) + "-" +
                    c.get(Calendar.DAY_OF_MONTH) + "-" +
                    c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) +
                    ":" + c.get(Calendar.SECOND)
        }


        /**
         * 获得当前日期的字符串格式
         *
         * @param format 格式化的类型
         * @return 返回格式化之后的事件
         */
        fun getCurDateStr(format: String): String? {
            val c = Calendar.getInstance()
            return date2Str(c, format)

        }


        /**
         * @param time 当前的时间
         * @return 格式到秒
         */

        fun getMillon(time: Long): String {

            return SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time)

        }


        /**
         * @param time 当前的时间
         * @return 当前的天
         */
        fun getDay(time: Long): String {

            return SimpleDateFormat("yyyy-MM-dd").format(time)

        }


        /**
         * @param time 时间
         * @return 返回一个毫秒
         */
        // 格式到毫秒
        fun getSMillon(time: Long): String {

            return SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time)

        }


        /**
         * 在日期上增加数个整月
         *
         * @param date 日期
         * @param n    要增加的月数
         * @return 增加数个整月
         */
        fun addMonth(date: Date, n: Int): Date {
            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.MONTH, n)
            return cal.time

        }


        /**
         * 在日期上增加天数
         *
         * @param date 日期
         * @param n    要增加的天数
         * @return 增加之后的天数
         */
        fun addDay(date: Date, n: Int): Date {
            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.DATE, n)
            return cal.time

        }


        /**
         * 获取距现在某一小时的时刻
         *
         * @param format 格式化时间的格式
         * @param h      距现在的小时 例如：h=-1为上一个小时，h=1为下一个小时
         * @return 获取距现在某一小时的时刻
         */
        fun getNextHour(format: String, h: Int): String {
            val sdf = SimpleDateFormat(format)
            val date = Date()
            date.time = date.time + h * 60 * 60 * 1000
            return sdf.format(date)

        }


        /**
         * 获取时间戳
         *
         * @return 获取时间戳
         */
        fun getTimeString(): String {
            val df = SimpleDateFormat(FORMAT_FULL)
            val calendar = Calendar.getInstance()
            return df.format(calendar.time)

        }


        /**
         * 功能描述：返回月
         *
         * @param date Date 日期
         * @return 返回月份
         */
        fun getMonth(date: Date): Int {
            calendar = Calendar.getInstance()
            calendar!!.time = date
            return calendar!!.get(Calendar.MONTH) + 1
        }


        /**
         * 功能描述：返回日
         *
         * @param date Date 日期
         * @return 返回日份
         */
        fun getDay(date: Date): Int {
            calendar = Calendar.getInstance()
            calendar!!.time = date
            return calendar!!.get(Calendar.DAY_OF_MONTH)
        }


        /**
         * 功能描述：返回小
         *
         * @param date 日期
         * @return 返回小时
         */
        fun getHour(date: Date): Int {
            calendar = Calendar.getInstance()
            calendar!!.time = date
            return calendar!!.get(Calendar.HOUR_OF_DAY)
        }


        /**
         * 功能描述：返回分
         *
         * @param date 日期
         * @return 返回分钟
         */
        fun getMinute(date: Date): Int {
            calendar = Calendar.getInstance()
            calendar!!.time = date
            return calendar!!.get(Calendar.MINUTE)
        }


        /**
         * 获得默认的 date pattern
         *
         * @return 默认的格式
         */
        fun getDatePattern(): String {

            return FORMAT_YMDHMS
        }


        /**
         * 返回秒钟
         *
         * @param date Date 日期
         * @return 返回秒钟
         */
        fun getSecond(date: Date): Int {
            calendar = Calendar.getInstance()

            calendar!!.time = date
            return calendar!!.get(Calendar.SECOND)
        }


        /**
         * 使用预设格式提取字符串日期
         *
         * @param strDate 日期字符串
         * @return 提取字符串的日期
         */
        fun parse(strDate: String): Date? {
            return parse(strDate, getDatePattern())

        }


        /**
         * 功能描述：返回毫
         *
         * @param date 日期
         * @return 返回毫
         */
        fun getMillis(date: Date): Long {
            calendar = Calendar.getInstance()
            calendar!!.time = date
            return calendar!!.timeInMillis
        }


        /**
         * 按默认格式的字符串距离今天的天数
         *
         * @param date 日期字符串
         * @return 按默认格式的字符串距离今天的天数
         */
        fun countDays(date: String): Int {
            val t = Calendar.getInstance().time.time
            val c = Calendar.getInstance()
            c.time = parse(date)
            val t1 = c.time.time
            return (t / 1000 - t1 / 1000).toInt() / 3600 / 24

        }


        /**
         * 使用用户格式提取字符串日期
         *
         * @param strDate 日期字符串
         * @param pattern 日期格式
         * @return 提取字符串日期
         */
        fun parse(strDate: String, pattern: String): Date? {
            val df = SimpleDateFormat(pattern)
            try {
                return df.parse(strDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                return null
            }

        }


        /**
         * 按用户格式字符串距离今天的天数
         *
         * @param date   日期字符串
         * @param format 日期格式
         * @return 按用户格式字符串距离今天的天数
         */
        fun countDays(date: String, format: String): Int {
            val t = Calendar.getInstance().time.time
            val c = Calendar.getInstance()
            c.time = parse(date, format)
            val t1 = c.time.time
            return (t / 1000 - t1 / 1000).toInt() / 3600 / 24
        }

        /**
         * @param nowFormatPattern 现在的时间格式   比如 yyyy-MM-dd
         * @param toFormatPattern  要转换的时间格式 比如 MM月dd日
         * @param strDdate 要转换的字符
         * @return 返回已经转换的字符
         */
        fun simpleFormat(nowFormatPattern: String, toFormatPattern: String, strDdate: String): String? {
            val sdf1 = SimpleDateFormat(nowFormatPattern)
            val sdf2 = SimpleDateFormat(toFormatPattern)
            var date: Date? = null
            try {
                date = sdf1.parse(strDdate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return if (date == null) null else sdf2.format(date)
        }

        /**
         * @param formatPattern 时间格式
         * @param strData 要转换的字符
         * @return 返回星期
         */
        fun getWeek(formatPattern: String, strData: String): String? {
            val sdf = SimpleDateFormat(formatPattern)
            val sdf2 = SimpleDateFormat("EEEE")
            var date: Date? = null
            try {
                date = sdf.parse(strData)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return if (date == null) null else sdf2.format(date)
        }

        /**
         * 将毫秒数换算成x天x时x分x秒x毫秒
         * @param ms
         * @return
         */
        fun milliSecondToDHMSS(ms: Long): String {
            val ss = 1000
            val mi = ss * 60
            val hh = mi * 60
            val dd = hh * 24

            val day = ms / dd
            val hour = (ms - day * dd) / hh
            val minute = (ms - day * dd - hour * hh) / mi
            val second = (ms - day * dd - hour * hh - minute * mi) / ss
            val milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss

            val strDay = if (day < 10) "0$day" else "" + day
            val strHour = if (hour < 10) "0$hour" else "" + hour
            val strMinute = if (minute < 10) "0$minute" else "" + minute
            val strSecond = if (second < 10) "0$second" else "" + second
            var strMilliSecond = if (milliSecond < 10) "0$milliSecond" else "" + milliSecond
            strMilliSecond = if (milliSecond < 100) "0$strMilliSecond" else "" + strMilliSecond
            return "$strDay $strHour:$strMinute:$strSecond $strMilliSecond"
        }

        /**
         * 将秒数换算成x天x时x分x秒
         * @param ss
         * @return
         */
        fun secondToDHMS(ss: Long): String {
            val mi = 60
            val hh = mi * 60
            val dd = hh * 24

            val day = ss / dd
            val hour = (ss - day * dd) / hh
            val minute = (ss - day * dd - hour * hh) / mi
            val second = ss - day * dd - hour * hh - minute * mi

            return day.toString() + "天" + hour + "小时" + minute + "分" + second + "秒"
        }

        /**
         * 判断时间是否在时间段内
         *
         * @param date
         * 当前时间 yyyy-MM-dd HH:mm:ss
         * @param strDateBegin
         * 开始时间 00:00:00
         * @return
         */
        fun isInDate(date: String, strDateBegin: String): Boolean {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val strDate = sdf.format(date)
            // 截取当前时间时分秒
            val strDateH = Integer.parseInt(date.substring(11, 13))
            val strDateM = Integer.parseInt(date.substring(14, 16))
            // 截取开始时间时分秒
            val strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2))
            val strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5))

            return if (strDateH >= strDateBeginH) {
                true
            } else {
                false
            }
        }

        /**
         * 时间戳转成提示性日期格式（昨天、今天……)
         */
        fun getDateToString(milSecond: Long): String {
            val date = Date(milSecond)
            val format: SimpleDateFormat
            var hintDate = ""
            //先获取年份
            val year = Integer.valueOf(SimpleDateFormat("yyyy").format(date))
            //获取一年中的第几天
            val day = Integer.valueOf(SimpleDateFormat("d").format(date))
            //获取当前年份 和 一年中的第几天
            val currentDate = Date(System.currentTimeMillis())
            val currentYear = Integer.valueOf(SimpleDateFormat("yyyy").format(currentDate))
            val currentDay = Integer.valueOf(SimpleDateFormat("d").format(currentDate))
            //计算 如果是去年的
            if (currentYear - year == 1) {
                //如果当前正好是 1月1日 计算去年有多少天，指定时间是否是一年中的最后一天
                if (currentDay == 1) {
                    val yearDay: Int
                    if (year % 400 == 0) {
                        yearDay = 366//世纪闰年
                    } else if (year % 4 == 0 && year % 100 != 0) {
                        yearDay = 366//普通闰年
                    } else {
                        yearDay = 365//平年
                    }
                    if (day == yearDay) {
                        hintDate = "昨天"
                    }
                }
            } else {
                if (currentDay - day == 1) {
                    hintDate = "昨天"
                }
                if (currentDay - day == 0) {
                    hintDate = "今天"
                }
            }
            if (TextUtils.isEmpty(hintDate)) {
                format = SimpleDateFormat("yyyy-MM-dd HH:mm")
                return format.format(date)
            } else {
                format = SimpleDateFormat("HH:mm")
                return hintDate + " " + format.format(date)
            }

        }
    }
}