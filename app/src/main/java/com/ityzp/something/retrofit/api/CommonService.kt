package com.ityzp.something.retrofit.api

import com.example.baseklibrary.base.BaseEntity
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * 设置请求类型参数
 * Created by wangqiang on 2019/5/27.
 */
interface CommonService {
    /**
     * post多参 添加头部token
     */
    @FormUrlEncoded
    @POST("url")
    abstract fun me(@Header("token") token: String, @FieldMap params: Map<String, Any>): Observable<BaseEntity>

    /**
     * post请求 json实体
     */
    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("url")
    abstract fun login(@Body body: RequestBody): Observable<BaseEntity>

    /**
     * 修改头像
     */
    @Multipart
    @POST("url")
    abstract fun upLoad(@Part file: MultipartBody.Part): Observable<BaseEntity>

    /**
     * get无参请求
     */
    @GET("url")
    abstract fun getSupluser(): Observable<BaseEntity>

    /**
     * get有参 拼接方式
     */
    @GET("url/{id}")
    abstract fun checkUpdate(@Path("id") id: String): Observable<BaseEntity>

    /**
     * get多参请求
     */
    @GET("url")
    abstract fun getUserSteps(@QueryMap map: Map<String, Any>): Observable<BaseEntity>

    /**
     * put请求 参数json实体
     */
    @Headers("Content-Type: application/json")
    @PUT("url")
    abstract fun put(@Body body: RequestBody): Observable<BaseEntity>

    /**
     * delete请求 参数json实体
     */
    @Headers("Content-Type:application/json;charset=UTF-8")
    @HTTP(method = "DELETE", path = "url", hasBody = true)
    abstract fun delete(@Body body: RequestBody): Observable<BaseEntity>
}