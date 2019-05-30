package com.ityzp.something.presenter

import com.example.baseklibrary.base.BaseEntity
import com.example.baseklibrary.base.BaseObserver
import com.example.baseklibrary.mvp.BasePresenter
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.contract.MeInfoContract
import com.ityzp.something.retrofit.RetrofitManager
import com.ityzp.something.retrofit.RetryWhenNetworkException
import com.ityzp.something.retrofit.RxSchedulers
import com.ityzp.something.retrofit.api.CommonService
import com.ityzp.something.view.fragment.MeInfoActivity
import okhttp3.MultipartBody
import java.util.concurrent.TimeUnit

/**
 * Created by wangqiang on 2019/5/28.
 */
class MeInfoPresenter : BasePresenter<MeInfoContract.meInfoView>() {
    fun upLoadPicture(
        mBody: MultipartBody.Part,
        context: MeInfoActivity
    ) {
        RetrofitManager.getInstance()!!.create(CommonService::class.java)
            .upLoad(mBody)
            .compose(context.bindToLifecycle())
            .throttleFirst(1, TimeUnit.SECONDS)
            .retryWhen(RetryWhenNetworkException(2, 300, 300))
            .compose(RxSchedulers.io_main())
            .compose(context.newWorkAvailable())
            .subscribe(object : BaseObserver() {
                override fun onHandleSuccess(baseEntity: BaseEntity) {
                    if ("200" == baseEntity.code) {
                        val imgurl = baseEntity.data
                        ToastUtil.show(context, "头像上传成功！")
                        view!!.upLoadPicture(imgurl)
                    } else {
                        ToastUtil.show(context, baseEntity.message!!)
                    }
                }

            })
    }

    fun getBrithday(time: String) {

    }
}