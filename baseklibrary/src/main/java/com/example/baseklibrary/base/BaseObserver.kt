package com.example.baseklibrary.base

import android.content.Intent
import android.view.Gravity
import com.example.baseklibrary.utils.NetworkUtils
import com.example.baseklibrary.utils.ToastUtil
import com.google.gson.JsonSyntaxException
import io.reactivex.annotations.NonNull
import io.reactivex.observers.DisposableObserver
import java.net.UnknownHostException

/**
 * Created by wangqiang on 2019/5/22.
 */
abstract class BaseObserver : DisposableObserver<BaseEntity>() {
    private val SUCCESS_CODE = "200"
    private val OUTDATE_CODE = "401"//登录过期
    private val LOGINOUT_CODE = "402"//账号被顶


    override fun onNext(@NonNull tBaseEntity: BaseEntity) {
        /*if (SUCCESS_CODE.equals(tBaseEntity.getCode())) {
            onHandleSuccess(tBaseEntity);
        } else*/ if (OUTDATE_CODE == tBaseEntity.code) {
            ToastUtil.show(BaseApplication.instance, "您的账号已过期，请重新登录")
            BaseApplication.instance.sendBroadcast(Intent("android.content.BroadcastReceiver.ACTION_TO_LOGIN"))
        } else if (LOGINOUT_CODE == tBaseEntity.code) {
            ToastUtil.show(BaseApplication.instance, "您的账号在其他设备登录，为了安全，该设备已下线")
            BaseApplication.instance.sendBroadcast(Intent("android.content.BroadcastReceiver.ACTION_TO_LOGIN"))
        } else {
            //            ToastUtil.show(BaseApplication.getInstance(), "服务器异常，请稍后再试", Gravity.CENTER);
        }
        onHandleSuccess(tBaseEntity)
        onFinally()
    }

    override fun onError(@NonNull e: Throwable) {
        e.printStackTrace()
        //有数据和没有数据时接口返回格式有问题 TODO
        if (e is JsonSyntaxException) {
            ToastUtil.show(BaseApplication.instance, "数据异常，请稍后再试", Gravity.CENTER)
        } else if (e !is UnknownHostException) {
            ToastUtil.show(BaseApplication.instance, "服务器异常，请稍后再试", Gravity.CENTER)
        }
        if (!NetworkUtils.isConnected(BaseApplication.instance)) {
            ToastUtil.show(BaseApplication.instance, "网络连接不可用，检查你的网络设置", Gravity.CENTER)
        }
        onFinally()
    }

    override fun onComplete() {}

    protected abstract fun onHandleSuccess(baseEntity: BaseEntity)

    protected fun onFinally() {}

    /*protected void onHandleFailed(String message) {
        ToastUtil.show(BaseApplication.getInstance(), message, Gravity.CENTER);
    }*/

    protected fun onStatusNotSuccssed(baseEntity: BaseEntity) {

    }

}