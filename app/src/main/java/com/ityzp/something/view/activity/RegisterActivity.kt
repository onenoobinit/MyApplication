package com.ityzp.something.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.contract.RegisterContract
import com.ityzp.something.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : MvpActivity<RegisterContract.registerView, RegisterPresenter>(), RegisterContract.registerView,
    View.OnClickListener {
    private var phone: String? = null
    private var timer: CountDownTimer? = null

    override fun initPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_register

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        phone = intent.getStringExtra("phone")
        tv_verify.setOnClickListener(this)
        tv_register_next.setOnClickListener(this)
    }

    override fun initToolBar() {
        tb_title.setText("注册")
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun getRegister() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_verify -> {
//                mPresenter.getRegister(phone)
                VerifyUtils()//验证计时
            }
            R.id.tv_register_next -> {
                if (TextUtils.isEmpty(et_register_code.text.toString().trim())) {
                    ToastUtil.show(this, "请输入验证码！")
                    return
                } else {
                    val intent = Intent()
                    intent.setClass(this, RegisterChoseActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun VerifyUtils() {
        timer = object : CountDownTimer(60000, 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(l: Long) {
                tv_verify.setClickable(false) //设置不可点击
                tv_verify.setText("重新发送(" + l / 1000 + ")")  //设置倒计时时间
                tv_verify.setBackgroundResource(R.drawable.bg_code_gray)
            }

            override fun onFinish() {
                tv_verify.setText("重新发送")
                tv_verify.setClickable(true)//重新获得点击
                tv_verify.setBackgroundResource(R.drawable.tv_register_code)
            }
        }.start()
    }
}
