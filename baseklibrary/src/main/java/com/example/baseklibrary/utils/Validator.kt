package com.example.baseklibrary.utils

import java.util.regex.Pattern

/**
 * Created by wangqiang on 2019/5/20.
 */
class Validator {
    companion object {
        /**
         * 正则表达式：验证用户名
         */
        val REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$"

        /**
         * 正则表达式：验证密码
         */
        val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$"

        /**
         * 正则表达式：验证手机号
         */
        val REGEX_MOBILE = "^1(3[0-9]|4[57]|5[0-35-9]|7[013678]|8[0-9])\\d{8}$"

        /**
         * 正则表达式：验证邮箱
         */
        val REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

        /**
         * 正则表达式：验证汉字
         */
        val REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$"

        /**
         * 正则表达式：验证身份证
         */
        val REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)"

        /**
         * 正则表达式：验证URL
         */
        val REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w -./?%&=]*)?"

        /**
         * 正则表达式：验证IP地址
         */
        val REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)"

        /**
         * 正则表达式:验证手机验证码纯5为数字
         */
        val REGEX_CODE = "^[0-9]{4}$"

        /**
         * 正则表达式:企业code
         */
        val QY_CODE = "^[0-9]{10}$"

        /**
         * 正则表达式:验证8-16位 包含数字和字母 区分大小写
         */
        val REGISTER_CODE = "^(?![^a-zA-Z]+$)(?!\\\\D+$).{8,16}$"


        /**
         * 军官证
         */
        val JUNGUAN_CODE = "^^[0-9]{8}$"

        /**
         * 护照
         */
        val HUZHAO_CODE = "^[a-zA-Z0-9]{5,17}$"

        /**
         * 昵称
         */
        val NICHENG_CODE = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{2,10}"

        /**
         * 验证验证么5位数字
         *
         * @param code
         * @return
         */
        fun isCode(code: String): Boolean {
            return Pattern.matches(REGEX_CODE, code)
        }

        /**
         * 验证企业code
         *
         * @param code
         * @return
         */
        fun isQYCode(code: String): Boolean {
            return Pattern.matches(QY_CODE, code)
        }

        /**
         * 校验用户名
         *
         * @param username
         * @return 校验通过返回true，否则返回false
         */
        fun isUsername(username: String): Boolean {
            return Pattern.matches(REGEX_USERNAME, username)
        }

        /**
         * 校验密码
         *
         * @param password
         * @return 校验通过返回true，否则返回false
         */
        fun isPassword(password: String): Boolean {
            return Pattern.matches(REGEX_PASSWORD, password)
        }

        /**
         * 校验手机号
         *
         * @param mobile
         * @return 校验通过返回true，否则返回false
         */
        fun isMobile(mobile: String): Boolean {
            return Pattern.matches(REGEX_MOBILE, mobile)
        }

        /**
         * 校验邮箱
         *
         * @param email
         * @return 校验通过返回true，否则返回false
         */
        fun isEmail(email: String): Boolean {
            return Pattern.matches(REGEX_EMAIL, email)
        }

        /**
         * 校验汉字
         *
         * @param chinese
         * @return 校验通过返回true，否则返回false
         */
        fun isChinese(chinese: String): Boolean {
            return Pattern.matches(REGEX_CHINESE, chinese)
        }

        /**
         * 校验身份证
         *
         * @param idCard
         * @return 校验通过返回true，否则返回false
         */
        fun isIDCard(idCard: String): Boolean {
            return Pattern.matches(REGEX_ID_CARD, idCard)
        }

        /**
         * 校验URL
         *
         * @param url
         * @return 校验通过返回true，否则返回false
         */
        fun isUrl(url: String): Boolean {
            return Pattern.matches(REGEX_URL, url)
        }

        /**
         * 校验IP地址
         *
         * @param ipAddr
         * @return
         */
        fun isIPAddr(ipAddr: String): Boolean {
            return Pattern.matches(REGEX_IP_ADDR, ipAddr)
        }

        /**
         * 校验IP地址
         *
         * @param register
         * @return
         */
        fun isregister(register: String): Boolean {
            return Pattern.matches(REGISTER_CODE, register)
        }

        /**
         * 校验军官
         *
         * @param register
         * @return
         */
        fun isJunGuan(register: String): Boolean {
            return Pattern.matches(JUNGUAN_CODE, register)
        }

        /**
         * 校验护照
         *
         * @param register
         * @return
         */
        fun isHuZhao(register: String): Boolean {
            return Pattern.matches(HUZHAO_CODE, register)
        }

        /**
         * 校验昵称
         *
         * @param register
         * @return
         */
        fun isNicheng(register: String): Boolean {
            return Pattern.matches(NICHENG_CODE, register)
        }
    }
}