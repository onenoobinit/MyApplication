package com.ityzp.something.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.MainActivity
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.contract.RegisterChoseContract
import com.ityzp.something.moudle.User
import com.ityzp.something.presenter.RegisterChosePresenter
import com.ityzp.something.widgets.PickTimeView
import kotlinx.android.synthetic.main.activity_register_chose.*
import java.text.SimpleDateFormat

class RegisterChoseActivity : MvpActivity<RegisterChoseContract.registerChoseView, RegisterChosePresenter>(),
    RegisterChoseContract.registerChoseView,
    PickTimeView.onSelectedChangeListener, View.OnClickListener {
    private var sex: Int = -1
    private var date: String = ""
    private var sdfDate: SimpleDateFormat? = null

    override fun initPresenter(): RegisterChosePresenter {
        return RegisterChosePresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_register_chose

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        pt_date.setViewType(PickTimeView.TYPE_PICK_DATE)
        sdfDate = SimpleDateFormat("yyyy-MM-dd")
        pt_date.setOnSelectedChangeListener(this)
        ll_boy.setOnClickListener(this)
        ll_girl.setOnClickListener(this)
        tv_register.setOnClickListener(this)
    }

    override fun initToolBar() {
    }

    override fun getRegisterChose() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_boy -> setbackgroud(0)
            R.id.ll_girl -> setbackgroud(1)
            R.id.tv_register -> {
                if (sex == -1) {
                    ToastUtil.show(this, "请选择性别！")
                    return
                } else {
//                    mPresenter.getRegisterChose(sex,date)
                    val intent = Intent()
                    intent.setClass(this, MainActivity::class.java)
                    startActivity(intent)
                    val user = User()
                    user!!.isLogined = true
                    user.birthday = date
                    user.id = "1"
//                    user.gender!!.name = "man"
                    SomeThingApp.instance.setUser(user, true)
//                    setResult(SomeThingApp.RESULT_OK)
                    finish()
                }
            }
        }
    }

    private fun setbackgroud(i: Int) {
        when (i) {
            0 -> {
                iv_boy.setImageResource(R.mipmap.ic_boy_choose)
                tv_boy.setTextColor(Color.parseColor("#5ACDA5"))
                iv_girl.setImageResource(R.mipmap.ic_girl)
                tv_girl.setTextColor(Color.parseColor("#AAAAAA"))
                sex = 0
            }

            1 -> {
                iv_boy.setImageResource(R.mipmap.ic_boy)
                tv_boy.setTextColor(Color.parseColor("#AAAAAA"))
                iv_girl.setImageResource(R.mipmap.ic_girl_choose)
                tv_girl.setTextColor(Color.parseColor("#5ACDA5"))
                sex = 1
            }
        }
    }

    override fun onSelected(view: PickTimeView, timeMillis: Long) {
        date = sdfDate!!.format(timeMillis)
    }

}
