package com.ityzp.something.retrofit

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by wangqiang on 2019/5/27.
 */
class RetryWhenNetworkException : Function<Observable<Throwable>, ObservableSource<*>> {

    private var count = 3
    private var delay: Long = 500
    private var increaseDelay: Long = 0

    constructor(count: Int, delay: Long) {
        this.count = count
        this.delay = delay
    }

    constructor(count: Int, delay: Long, increaseDelay: Long) {
        this.count = count
        this.delay = delay
        this.increaseDelay = increaseDelay
    }

    @Throws(Exception::class)
    override fun apply(@NonNull throwableObservable: Observable<Throwable>): ObservableSource<*> {
        return throwableObservable
            .zipWith(Observable.range(1, count + 1), object : BiFunction<Throwable, Int, Wrapper> {
                @Throws(Exception::class)
                override fun apply(throwable: Throwable, t2: Int): Wrapper {
                    return Wrapper(throwable, t2!!)
                }
            }).flatMap { wrapper ->
                if ((wrapper.throwable is ConnectException
                            || wrapper.throwable is SocketTimeoutException
                            || wrapper.throwable is UnknownHostException
                            || wrapper.throwable is TimeoutException) && wrapper.index < count + 1
                ) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                    Observable.timer(delay + (wrapper.index - 1) * increaseDelay, TimeUnit.MILLISECONDS)
                } else Observable.error<Any>(wrapper.throwable)
            }
    }

    private inner class Wrapper(val throwable: Throwable, val index: Int)

}