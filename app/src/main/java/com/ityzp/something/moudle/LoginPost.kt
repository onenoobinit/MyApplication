package com.ityzp.something.moudle

/**
 * Created by wangqiang on 2019/5/27.
 */
class LoginPost {
    var postInfoBean: PostInfoBean? = null

    inner class PostInfoBean {

        var loginName: String? = null
        var password: String? = null
        var authId: String? = null

    }
}