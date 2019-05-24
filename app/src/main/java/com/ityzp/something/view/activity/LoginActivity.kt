package com.ityzp.something.view.activity

import android.os.Bundle
import android.view.View
import com.example.baseklibrary.mvp.MvpActivity
import com.ityzp.something.R
import com.ityzp.something.contract.LoginContract
import com.ityzp.something.moudle.User
import com.ityzp.something.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MvpActivity<LoginContract.loginView, LoginPresenter>(), LoginContract.loginView,
    View.OnClickListener {
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun initViews(savedInstanceState: Bundle?) {
        tv_login.setOnClickListener(this)
    }

    override fun initToolBar() {
    }

    override fun login(user: User) {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_login -> {
                setResult(1)
                finish()
            }
        }
    }

}
