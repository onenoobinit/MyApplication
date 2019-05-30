package com.ityzp.something.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.example.baseklibrary.utils.Validator
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.contract.ChangeNameContract
import com.ityzp.something.presenter.ChangeNamePresenter
import kotlinx.android.synthetic.main.activity_change_name.*
import kotlinx.android.synthetic.main.activity_head.*

class ChangeNameActivity : MvpActivity<ChangeNameContract.chagneNameView, ChangeNamePresenter>(),
    ChangeNameContract.chagneNameView,
    View.OnClickListener {

    override fun initPresenter(): ChangeNamePresenter {
        return ChangeNamePresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_change_name

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
    }

    override fun initToolBar() {
        tb_title.setText("修改昵称")
        toobar.setNavigationOnClickListener { finish() }
        tv_submit.visibility = TextView.VISIBLE
        tv_submit.setText("保存")
        tv_submit.setOnClickListener(this)
    }

    override fun getChangeName() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_submit -> {
                if (TextUtils.isEmpty(et_change_name.text.toString().trim())) {
                    ToastUtil.show(this, "请输入您的昵称！")
                    return
                } else if (!Validator.isNicheng(et_change_name.text.toString().trim())) {
                    ToastUtil.show(this, "您输入的昵称格式不正确！")
                    return
                } else {
//                    mPresenter.getChangeName(et_change_name.text.toString().trim())
                    val user = SomeThingApp.instance.getUser()
                    user.nickName = et_change_name.text.toString().trim()
                    SomeThingApp.instance.setUser(user, true)
                    setResult(SomeThingApp.RESULT_OK)
                    finish()
                }
            }
        }
    }


}
