package com.example.baseklibrary.base

import java.io.Serializable

/**
 * Created by wangqiang on 2019/5/22.
 * 实体类
 */
class BaseEntity : Serializable {

    var message: String? = null
    var codeText: String? = null
    var code: String? = null
    var success: String? = null
    var data: String? = null
    var pageInfo: String? = null

    override fun toString(): String {
        return "BaseEntity{" +
                "success='" + success + '\''.toString() +
                ",message='" + message + '\''.toString() +
                ",data='" + data + '\''.toString() +
                ", codeText='" + codeText + '\''.toString() +
                ", code='" + code + '\''.toString() +
                ", pageInfo='" + pageInfo + '\''.toString() +
                '}'.toString()
    }
}