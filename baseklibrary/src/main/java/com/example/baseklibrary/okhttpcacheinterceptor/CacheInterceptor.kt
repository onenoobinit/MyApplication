package com.example.baseklibrary.okhttpcacheinterceptor

import android.content.Context
import com.example.baseklibrary.okhttpcacheinterceptor.Catch.CacheManager
import com.example.baseklibrary.utils.L
import okhttp3.*
import java.io.IOException

/**
 * Created by wangqiang on 2019/5/22.
 */
class CacheInterceptor(private var context: Context?) : Interceptor {

    fun setContext(context: Context) {
        this.context = context
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val cacheHead = request.header("cache")
        val cache_control = request.header("Cache-Control")

        if ("true" == cacheHead ||                              // 意思是要缓存
            cache_control != null && !cache_control.isEmpty()
        )
        // 这里还支持WEB端协议的缓存头
        {
            val oldnow = System.currentTimeMillis()
            val url = request.url.toUrl().toString()
            var responStr: String? = null
            val reqBodyStr = getPostParams(request)
            try {
                val response = chain.proceed(request)
                if (response.isSuccessful)
                // 只有在网络请求返回成功之后，才进行缓存处理，否则，404存进缓存，岂不笑话
                {
                    val responseBody = response.body
                    if (responseBody != null) {
                        responStr = responseBody.string()
                        if (responStr == null) {
                            responStr = ""
                        }
                        CacheManager.getInstance(context)!!.setCache(CacheManager.encryptMD5(url + reqBodyStr), responStr)//存缓存，以链接+参数进行MD5编码为KEY存
                        L.d("HttpRetrofit", "--> Push Cache:$url :Success")
                    }
                    return getOnlineResponse(response, responStr)
                } else {
                    return chain.proceed(request)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val response = getCacheResponse(request, oldnow) // 发生异常了，我这里就开始去缓存，但是有可能没有缓存，那么久需要丢给下一轮处理了
                return response ?: chain.proceed(request)
            }

        } else {
            return chain.proceed(request)
        }
    }

    private fun getCacheResponse(request: Request, oldNow: Long): Response? {
        L.d("HttpRetrofit", "--> Try to Get Cache   --------")
        val url = request.url.toUrl().toString()
        val params = getPostParams(request)
        val cacheStr =
            CacheManager.getInstance(context)!!.getCache(CacheManager.encryptMD5(url + params))//取缓存，以链接+参数进行MD5编码为KEY取
        if (cacheStr == null) {
            L.d("HttpRetrofit", "<-- Get Cache Failure ---------")
            return null
        }
        val response = Response.Builder()
            .code(200)
            .body(ResponseBody.create(null, cacheStr!!))
            .request(request)
            .message(CacheType.DISK_CACHE)
            .protocol(Protocol.HTTP_1_0)
            .build()
        val useTime = System.currentTimeMillis() - oldNow
        L.i(
            "HttpRetrofit",
            "<-- Get Cache: " + response.code + " " + response.message + " " + url + " (" + useTime + "ms)"
        )
        L.i("HttpRetrofit", cacheStr!! + "")
        return response
    }

    private fun getOnlineResponse(response: Response, body: String?): Response {
        val responseBody = response.body
        return Response.Builder()
            .code(response.code)
            .body(ResponseBody.create(responseBody?.contentType(), body!!))
            .request(response.request)
            .message(response.message)
            .protocol(response.protocol)
            .build()
    }

    /**
     * 获取在Post方式下。向服务器发送的参数
     *
     * @param request
     * @return
     */
    private fun getPostParams(request: Request): String {
        var reqBodyStr = ""
        val method = request.method
        if ("POST" == method)
        // 如果是Post，则尽可能解析每个参数
        {
            val sb = StringBuilder()
            if (request.body is FormBody) {
                val body = request.body as FormBody?
                if (body != null) {
                    for (i in 0 until body.size) {
                        sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",")
                    }
                    sb.delete(sb.length - 1, sb.length)
                }
                reqBodyStr = sb.toString()
                sb.delete(0, sb.length)
            }
        }
        return reqBodyStr
    }

}