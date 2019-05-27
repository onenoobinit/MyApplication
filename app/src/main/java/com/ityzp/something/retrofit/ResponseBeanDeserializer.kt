package com.ityzp.something.retrofit

import com.example.baseklibrary.base.BaseEntity
import com.example.baseklibrary.utils.L
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**
 * gson不解析data字段 直接返回字符串
 * Created by wangqiang on 2019/5/27.
 */
class ResponseBeanDeserializer : JsonDeserializer<BaseEntity> {
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): BaseEntity {
        val jsonObject = json.asJsonObject
        var jsono: JSONObject? = null
        val baseEntity = BaseEntity()
        try {
            val mjson = json.toString()
            mjson.substring(mjson.indexOf("{"), mjson.lastIndexOf("}"))
            L.d("获取的json$mjson")
            jsono = JSONObject(mjson)
            baseEntity.code = jsono.optString("code")
            baseEntity.codeText = jsono.optString("codeText")
            baseEntity.data = jsono.optString("data")
            baseEntity.message = jsono.optString("message")
            baseEntity.success = jsono.optString("success")
            baseEntity.pageInfo = jsono.optString("pageInfo")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return baseEntity
    }
}