package com.ityzp.something.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.ToastUtil
import com.example.baseklibrary.utils.Validator
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.contract.LoginContract
import com.ityzp.something.moudle.User
import com.ityzp.something.presenter.LoginPresenter
import com.ityzp.something.utils.WXObserver
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * 登录页面
 * Created by wangqiang on 2019/5/23.
 */
class LoginActivity : MvpActivity<LoginContract.loginView, LoginPresenter>(), LoginContract.loginView,
    View.OnClickListener, Observer {
    var muser: User? = User()
    private var wxapi: IWXAPI? = null
    override val layoutId: Int
        get() = R.layout.activity_login

    override fun initPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun initViews(savedInstanceState: Bundle?) {
        WXObserver.INSTANCE.addObserver(this)
        wxapi = WXAPIFactory.createWXAPI(this, SomeThingApp.APP_ID, true)
        wxapi!!.registerApp(SomeThingApp.APP_ID)
        tv_login.setOnClickListener(this)
        iv_login_close.setOnClickListener(this)
        tv_login_help.setOnClickListener(this)
        tv_login_register.setOnClickListener(this)
        tv_login_user.setOnClickListener(this)
        tv_login_phone_change.setOnClickListener(this)
        tv_login_code.setOnClickListener(this)
        tv_login_forget_password.setOnClickListener(this)
        ll_login_wx.setOnClickListener(this)
        ll_login_qq.setOnClickListener(this)

        val textChange = TextChange()
        val textChangeUser = TextChangeUser()
        et_login_phone.addTextChangedListener(textChange)
        et_login_tellnumber.addTextChangedListener(textChangeUser)
        et_login_password.addTextChangedListener(textChangeUser)
    }

    override fun initToolBar() {
    }

    override fun login(user: User) {
        muser = user
        /*user!!.isLogined = true
        SomeThingApp.instance.setUser(user, true)
        setResult(SomeThingApp.RESULT_OK)
        finish()*/
    }

    override fun onClick(v: View?) {


        when (v!!.id) {
            R.id.tv_login -> {//账号密码登录
                if (TextUtils.isEmpty(et_login_tellnumber.text.toString().trim())) {
                    ToastUtil.show(this, "请输入手机号！")
                    return
                } else if (!Validator.isMobile(et_login_tellnumber.text.toString().trim())) {
                    ToastUtil.show(this, "手机号格式不正确！")
                    return
                } else if (TextUtils.isEmpty(et_login_password.text.toString().trim())) {
                    ToastUtil.show(this, "请输入密码！")
                    return
                } else if (!Validator.isPassword(et_login_password.text.toString().trim())) {
                    ToastUtil.show(this, "密码格式不正确！")
                    return
                } else {
//                mPresenter.login(et_login_tellnumber.text.toString().trim(),et_login_password.text.toString().trim())
                    muser!!.isLogined = true
                    muser!!.phone = et_login_tellnumber.text.toString().trim()
                    muser!!.id = "1"
                    SomeThingApp.instance.setUser(muser, true)
                    setResult(SomeThingApp.RESULT_OK)
                    finish()
                }
            }
            R.id.tv_login_register -> {//验证码注册
                if (TextUtils.isEmpty(et_login_phone.text.toString().trim())) {
                    ToastUtil.show(this, "请输入手机号！")
                    return
                } else if (!Validator.isMobile(et_login_phone.text.toString().trim())) {
                    ToastUtil.show(this, "手机号格式不正确！")
                    return
                } else {
                    val intent = Intent()
                    intent.setClass(this, RegisterActivity::class.java)
                    intent.putExtra("phone", et_login_phone.text.toString().trim())
                    startActivity(intent)
                }
            }
            R.id.iv_login_close -> finish()
            R.id.tv_login_help -> {//帮助
            }
            R.id.tv_login_user -> {
                setVisiable(1)
            }
            R.id.tv_login_code -> {
                setVisiable(0)
            }
            R.id.tv_login_phone_change -> {//更改手机号
            }
            R.id.tv_login_forget_password -> {//忘记密码

            }
            R.id.ll_login_qq -> {//qq登录
            }
            R.id.ll_login_wx -> {//微信登录
                wxlogin()
            }
        }
    }

    private fun wxlogin() {
        if (wxapi == null) {
            WXAPIFactory.createWXAPI(this, SomeThingApp.APP_ID, true)
        }

        if (!wxapi!!.isWXAppInstalled) {
            ToastUtil.show(this, "您的手机尚未安装微信，请安装后再登录")
            return
        }
        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "wechat_sdk_something"
        wxapi!!.sendReq(req)
    }

    private fun setVisiable(i: Int) {
        when (i) {
            0 -> {
                rl_login_user.visibility = RelativeLayout.GONE
                rl_register.visibility = RelativeLayout.VISIBLE
                tv_login_help.visibility = TextView.GONE
                tv_login_messgage.visibility = TextView.VISIBLE
            }

            1 -> {
                rl_login_user.visibility = RelativeLayout.VISIBLE
                rl_register.visibility = RelativeLayout.GONE
                tv_login_help.visibility = TextView.VISIBLE
                tv_login_messgage.visibility = TextView.INVISIBLE
            }
        }
    }

    private inner class TextChange : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {

            if (et_login_phone.text.toString().length > 0) {
                tv_login_register.setBackgroundResource(R.drawable.bg_login)
            } else {
                tv_login_register.setBackgroundResource(R.drawable.bg_login_gray)
            }
        }

    }

    private inner class TextChangeUser : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable) {
            if (et_login_tellnumber.text.toString().length > 0 && et_login_password.text.toString().length > 0) {
                tv_login.setBackgroundResource(R.drawable.bg_login)
            } else {
                tv_login.setBackgroundResource(R.drawable.bg_login_gray)
            }
        }
    }

    //wx登录回调
    override fun update(o: Observable?, arg: Any?) {
        if (o is WXObserver) {
            val res = arg as SendAuth.Resp
            val code = res.code
//            getData(code)
        }
    }

}
