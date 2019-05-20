package com.example.baseklibrary.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by wangqiang on 2019/5/20.
 */
class MoneySimpleFormat {
    companion object {
        //返回格式:￥200,000.03
        fun getMoneyType(money: String?): String? {
            if (money == null) return null
            // 把string类型的货币转换为double类型。
            val numDouble = java.lang.Double.parseDouble(money)
            // 想要转换成指定国家的货币格式
            val format = NumberFormat.getCurrencyInstance(Locale.CHINA)
            // 把转换后的货币String类型返回
            return format.format(numDouble)
        }


        fun getMoneyType(money: Long): String? {
            return getMoneyType(money.toString() + "")
        }

        fun getMoneyType(money: Double): String? {
            return getMoneyType(money.toString() + "")
        }

        //返回格式:200,000.03
        fun getSimpleType(money: String): String {
            val nf = DecimalFormat(",###.##")
            val numDouble = java.lang.Double.parseDouble(money)
            return nf.format(numDouble)
        }

        fun getSimpleType(money: Long): String {
            return getSimpleType(money.toString() + "")
        }

        fun getSimpleType(money: Double): String {
            return getSimpleType(money.toString() + "")
        }

        /**
         * @param custom 自定义金钱符号
         * @param money
         * @return
         */
        fun getCustomType(custom: String, money: String): String {
            val nf = DecimalFormat("$custom,###.##")
            val numDouble = java.lang.Double.parseDouble(money)
            return nf.format(numDouble)
        }

        fun getCustomType(custom: String, money: Long): String {
            return getCustomType(custom, money.toString() + "")
        }

        fun getCustomType(custom: String, money: Double): String {
            return getCustomType(custom, money.toString() + "")
        }

        fun getYuanType(money: String): String {
            val nf = DecimalFormat(",###.##元")
            val numDouble = java.lang.Double.parseDouble(money)
            return nf.format(numDouble)
        }

        fun getYuanType(money: Long): String {
            return getYuanType(money.toString() + "")
        }

        fun getYuanType(money: Double): String {
            return getYuanType(money.toString() + "")
        }

/*    public static void main(String[] args)
    {
        System.out.println(MoneySimpleFormat.getYuanType("1545.135"));
    }*/

        /**
         * 返回 没有逗号的 ¥200000.03
         *
         * @return
         */
        fun getNoCommaType(money: String): String {
            val nf = DecimalFormat("¥###.##")
            val numDouble = java.lang.Double.parseDouble(money)
            return nf.format(numDouble)
        }

        /**
         * 返回 没有逗号并且没有小数 ¥200000
         *
         * @return
         */
        fun getNoCommaNoDecimalsType(money: String): String {
            val nf = DecimalFormat("¥ ###")
            val numDouble = java.lang.Double.parseDouble(money)
            return nf.format(numDouble)
        }

        fun getNoCommaType(money: Long): String {
            return getNoCommaType(money.toString() + "")
        }

        fun getNoCommaType(money: Double): String {
            return getNoCommaType(money.toString() + "")
        }
    }
}