package com.ityzp.something.retrofit

import com.example.baseklibrary.base.BaseEntity
import com.example.baseklibrary.okhttpcacheinterceptor.CacheInterceptor
import com.example.baseklibrary.utils.HttpsUtils
import com.google.gson.GsonBuilder
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

/**
 * retrofit管理类
 * Created by wangqiang on 2019/5/27.
 */
class RetrofitManager private constructor() {

    init {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(BaseEntity::class.java, ResponseBeanDeserializer())
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(builder.create()))
            //.addConverterFactory(new ToStringConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client!!)
            .build()
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

    companion object {
        private var instance: RetrofitManager? = null
        private lateinit var retrofit: Retrofit
        private var client: OkHttpClient? = null
        private val CONNECT_TIMEOUT = 60//超时
        private val READ_TIMEOUT = 100
        private val WRITE_TIMEOUT = 60

        /**
         * #0001
         * 初始化OKHttpClient     \
         * 设置缓存
         * 设置超时时间
         * 设置打印日志
         */
        private fun initOkHttpClient() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            //设置Http缓存
            val cache =
                Cache(File(SomeThingApp.instance.getCacheDir(), "HttpCache"), (1024 * 1024 * 100).toLong())

            val msslSocketFactory = HttpsUtils.getSSLSocketFactory_Certificate(
                SomeThingApp.instance,
                "BKS", R.raw.apphome
            )!!
            val x509TrustManager = object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            }
            client = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(CacheInterceptor(SomeThingApp.instance))
                .addInterceptor(object : Interceptor {
                    //全局添加头部信息
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val newRequest = chain.request().newBuilder()
                            .addHeader("User-Agent", "kzxw_android")
                            .build()
                        return chain.proceed(newRequest)
                    }
                })
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .sslSocketFactory(msslSocketFactory, x509TrustManager)
                .build()
        }

        fun getInstance(): RetrofitManager? {
            if (instance == null) {
                synchronized(RetrofitManager::class.java) {
                    initOkHttpClient()
                    instance = RetrofitManager()
                }
            }
            return instance
        }

        //---------------------------------------线上-------------------------------------------------
        var baseUrl = "https://app.uhealth-online.com.cn/"

        /*//     ---------------------------------------线下-------------------------------------------------
        var baseUrl = "http://192.168.8.20:8100/";*/
    }
}
