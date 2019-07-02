package com.ityzp.something.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.contract.DrawerLayoutContract
import com.ityzp.something.presenter.DrawerLayoutPresenter
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.drawer_layout.*

/*
* 侧拉菜单
*/
class DrawerLayoutActivity : MvpActivity<DrawerLayoutContract.drawerlayoutView, DrawerLayoutPresenter>(),
    DrawerLayoutContract.drawerlayoutView,
    View.OnClickListener {
    var dayStaus: Boolean = false
    override fun initPresenter(): DrawerLayoutPresenter {
        return DrawerLayoutPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_drawer_layout

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        ll_drawer_my.setOnClickListener(this)
        ll_drawer_cart.setOnClickListener(this)
        ll_drawer_kinds.setOnClickListener(this)
        ll_drawer_find.setOnClickListener(this)
        ll_day.setOnClickListener(this)
    }

    override fun initToolBar() {
        tb_title.setText("侧拉菜单")
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun getdrawer() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_drawer_my -> {
                ToastUtil.show(this, "我的信息")
            }
            R.id.ll_drawer_cart -> {
                ToastUtil.show(this, "购物车")
            }
            R.id.ll_drawer_kinds -> {
                ToastUtil.show(this, "分类")
            }
            R.id.ll_drawer_find -> {
                ToastUtil.show(this, "发现")
            }
            R.id.ll_day->{
                if (dayStaus == false) {
                    iv_day.setImageResource(R.mipmap.ic_day_click)
                    tv_day.setTextColor(Color.parseColor("#5ACDA5"))
                    dayStaus = !dayStaus
                }
            }
        }
    }

}
