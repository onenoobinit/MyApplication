package com.ityzp.something.presenter

import com.example.baseklibrary.base.BaseEntity
import com.example.baseklibrary.base.BaseObserver
import com.example.baseklibrary.mvp.BasePresenter
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.SomeThingApp
import com.ityzp.something.contract.LoginContract
import com.ityzp.something.moudle.LoginPost
import com.ityzp.something.moudle.User
import com.ityzp.something.retrofit.RetrofitManager
import com.ityzp.something.retrofit.RetryWhenNetworkException
import com.ityzp.something.retrofit.RxSchedulers
import com.ityzp.something.retrofit.api.CommonService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import java.util.concurrent.TimeUnit

/**
 * Created by wangqiang on 2019/5/24.
 */
open class LoginPresenter : BasePresenter<LoginContract.loginView>() {

    fun login(tell: String, password: String) {
        val postInfoBean = LoginPost().PostInfoBean()
        postInfoBean.loginName = tell
        postInfoBean.password = password
        val toJson = gson!!.toJson(postInfoBean)
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), toJson)
        RetrofitManager.getInstance()!!.create(CommonService::class.java)
            .login(requestBody)
            .throttleFirst(1, TimeUnit.SECONDS)
            .retryWhen(RetryWhenNetworkException(2, 500, 500))
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver() {
                 override fun onHandleSuccess(baseEntity: BaseEntity) {

                    if ("200" == baseEntity.code) {
                        val user = gson!!.fromJson(baseEntity.data, User::class.java)
                        if (view != null) {
                            view!!.login(user)
                        }
                    } else {
                        ToastUtil.show(SomeThingApp.instance, baseEntity.message!!)
                    }
                }

                 override fun onFinally() {
                    super.onFinally()

                }
            })
    }
}