package com.ityzp.something

import android.os.Bundle
import android.view.View
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.ll_index -> ToastUtil.show(this,"首页")
            R.id.ll_center -> ToastUtil.show(this,"商城")
            R.id.ll_me -> ToastUtil.show(this,"我的")
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?) {
        ll_index.setOnClickListener(this)
        ll_center.setOnClickListener(this)
        ll_me.setOnClickListener(this)
    }

    override fun initToolBar() {
    }

}
