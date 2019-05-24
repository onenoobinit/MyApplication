package com.ityzp.something.contract

import com.ityzp.something.moudle.User

/**
 * Created by wangqiang on 2019/5/24.
 */
interface LoginContract {
    interface loginView{
        fun login(user: User)
    }
}