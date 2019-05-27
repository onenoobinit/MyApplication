package com.ityzp.something.view.activity

import android.os.Bundle
import android.view.View
import com.example.baseklibrary.base.BaseActivity
import com.ityzp.something.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity(), View.OnClickListener {

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initViews(savedInstanceState: Bundle?) {
        tv_out.setOnClickListener(this)
    }

    override fun initToolBar() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_out -> {
                setResult(2)
                finish()
            }
        }
    }
}
