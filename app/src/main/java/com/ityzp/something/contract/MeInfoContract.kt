package com.ityzp.something.contract

/**
 * Created by wangqiang on 2019/5/28.
 */
interface MeInfoContract {
    interface meInfoView {
        //上传头像
        fun upLoadPicture(imgurl: String?)

    }
}