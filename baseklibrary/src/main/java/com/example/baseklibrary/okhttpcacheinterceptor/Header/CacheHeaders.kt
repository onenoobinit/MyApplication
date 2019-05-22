package com.example.baseklibrary.okhttpcacheinterceptor.Header

/**
 * Created by wangqiang on 2019/5/22.
 */
class CacheHeaders {
    companion object {
        // 自己设置的一个标签
        val NORMAL = "cache:true"
        // 客户端可以缓存
        val PRIVATE = "Cache-Control:private"
        // 客户端和代理服务器都可缓存（前端的同学，可以认为public和private是一样的）
        val MAX_AGE = "Cache-Control:max-age=xxx"
        // 缓存的内容将在 xxx 秒后失效
        val NO_CACHE = "Cache-Control:no-cache"
        // 需要使用对比缓存来验证缓存数据（后面介绍）
        val PUBLIC = "Cache-Control:public"
        // 所有内容都不会缓存，强制缓存，对比缓存都不会触发（对于前端开发来说，缓存越多越好，so...基本上和它说886）
        val NO_STORE = "Cache-Control:no-store"
    }
}