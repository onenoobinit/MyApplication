package com.ityzp.something.view.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.adapter.MeVipAdapter
import com.ityzp.something.contract.MeContract
import com.ityzp.something.moudle.MeInfo
import com.ityzp.something.presenter.MePresenter
import com.ityzp.something.view.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_me.*
import me.everything.android.ui.overscroll.IOverScrollUpdateListener
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper


/**
 * Created by wangqiang on 2019/5/23.
 */

class MeFragment : MvpFragment<MeContract.meView, MePresenter>(), MeContract.meView, View.OnClickListener {
    private var viewHeight: Int? = 0
    private var old_offset: Int? = 0
    private var mevips: ArrayList<String>? = ArrayList()

    override val layoutResId: Int
        get() = com.ityzp.something.R.layout.fragment_me

    override fun finishCreateView(state: Bundle?) {
        initanimation()//设置下拉动画效果
        mevips!!.add("")
        mevips!!.add("")
        mevips!!.add("")
        rv_vip.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        rv_vip.adapter = MeVipAdapter(mevips!!, mContext)

        tv_me_login.setOnClickListener(this)
    }

    private fun initanimation() {
        val upOverScroll = OverScrollDecoratorHelper.setUpOverScroll(sv_me)
        upOverScroll.setOverScrollUpdateListener(IOverScrollUpdateListener { decor, state, offset ->
            val lp = iv_head_view.layoutParams as RelativeLayout.LayoutParams
            if (viewHeight == 0) {
                viewHeight = iv_head_view.height
            }
            lp.height = offset.toInt() + viewHeight!!
            old_offset = offset.toInt()
            if (offset > 0) {
                rl_root.setPadding(0, -old_offset!!, 0, 0)
            }
            if (lp.height > viewHeight!!) {
                iv_head_view.layoutParams = lp
            }
            if (state == 3) {
                lp.height = -1
                old_offset = 0
                iv_head_view.layoutParams = lp
            }
        })
    }

    override fun getMeInfo(meInfo: MeInfo) {

    }

    override fun initPresenter(): MePresenter {
        return MePresenter()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_me_login -> {
                val intent = Intent()
                intent.setClass(mContext, LoginActivity::class.java)
                startActivityForResult(intent, 1001)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == 1) {
            tv_me_login.visibility = TextView.GONE
            ll_user.visibility = LinearLayout.VISIBLE
        }
    }
}