package com.example.baseklibrary.utils

import android.text.TextUtils
import java.util.*

/**
 * Created by wangqiang on 2019/5/20.
 */
class CRequest {
    companion object {
        /**
         * 解析出url请求的路径，包括页面
         * @param strURL url地址
         * @return url路径
         */
        fun UrlPage(strURL: String): String? {
            var strURL = strURL
            var strPage: String? = null
            var arrSplit: Array<String>? = null

            strURL = strURL.trim { it <= ' ' }.toLowerCase()

            arrSplit = strURL.split("[?]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (strURL.length > 0) {
                if (arrSplit.size > 1) {
                    if (arrSplit[0] != null) {
                        strPage = arrSplit[0]
                    }
                }
            }

            return strPage
        }

        /**
         * 去掉url中的路径，留下请求参数部分
         * @param strURL url地址
         * @return url请求参数部分
         */
        private fun TruncateUrlPage(strURL: String): String? {
            var strURL = strURL
            var strAllParam: String? = null
            var arrSplit: Array<String>? = null

            strURL = strURL.trim { it <= ' ' }.toLowerCase()

            arrSplit = strURL.split("[?]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (strURL.length > 1) {
                if (arrSplit.size > 1) {
                    if (arrSplit[1] != null) {
                        strAllParam = arrSplit[1]
                    }
                }
            }

            return strAllParam
        }

        /**
         * 解析出url参数中的键值对
         * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
         * @param URL  url地址
         * @return  url请求参数部分
         */
        fun URLRequest(URL: String): Map<String, String> {
            val mapRequest = HashMap<String, String>()

            var arrSplit: Array<String>? = null

            val strUrlParam = TruncateUrlPage(URL) ?: return mapRequest
//每个键值为一组 www.2cto.com
            arrSplit = strUrlParam.split("[&]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (strSplit in arrSplit) {
                var arrSplitEqual: Array<String>? = null
                arrSplitEqual = strSplit.split("[=]".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                //解析出键值
                if (arrSplitEqual.size > 1) {
                    //正确解析
                    mapRequest[arrSplitEqual[0]] = arrSplitEqual[1]

                } else {
                    if (arrSplitEqual[0] !== "") {
                        //只有参数没有值，不加入
                        mapRequest[arrSplitEqual[0]] = ""
                    }
                }
            }
            return mapRequest
        }

        fun append(url: String, params: String): String {
            var url = url
            if (!TextUtils.isEmpty(params)) {
                if (!TextUtils.isEmpty(url) && !url.contains(params)) {
                    if (url.contains("?")) {
                        url = "$url&$params"
                    } else {
                        url = "$url?$params"
                    }
                }
            }
            return url
        }
    }
}