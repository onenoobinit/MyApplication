package com.ityzp.something.view.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.adapter.MeVipAdapter
import com.ityzp.something.contract.MeContract
import com.ityzp.something.moudle.MeInfo
import com.ityzp.something.presenter.MePresenter
import com.ityzp.something.view.activity.AboutActivity
import com.ityzp.something.view.activity.LoginActivity
import com.ityzp.something.view.activity.SettingActivity
import com.ityzp.something.widgets.GlideCircleTransform
import kotlinx.android.synthetic.main.fragment_me.*
import me.everything.android.ui.overscroll.IOverScrollUpdateListener
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper


/**
 * 我的
 * Created by wangqiang on 2019/5/23.
 */

class MeFragment : MvpFragment<MeContract.meView, MePresenter>(), MeContract.meView, View.OnClickListener {
    private var viewHeight: Int? = 0
    private var old_offset: Int? = 0
    private var mevips: ArrayList<String>? = ArrayList()
    val animatorSet = AnimatorSet()
    var animatorlist: ArrayList<Animator>? = arrayListOf()
    private val mRequestOptions = RequestOptions.placeholderOf(R.drawable.ic_app).transform(GlideCircleTransform())
    override val layoutResId: Int
        get() = R.layout.fragment_me

    override fun finishCreateView(state: Bundle?) {
        initanimation()//设置下拉动画效果 recycleview回弹
        mevips!!.add("")
        mevips!!.add("")
        mevips!!.add("")
        tv_me_login.setOnClickListener(this)
        iv_set.setOnClickListener(this)
        tv_feed.setOnClickListener(this)
        tv_favor.setOnClickListener(this)
        tv_skin.setOnClickListener(this)
        tv_service.setOnClickListener(this)
        tv_order.setOnClickListener(this)
        tv_gy.setOnClickListener(this)
        tv_card.setOnClickListener(this)
        tv_money.setOnClickListener(this)
        tv_gift.setOnClickListener(this)
        tv_about.setOnClickListener(this)
        iv_user.setOnClickListener(this)
        tv_name.setOnClickListener(this)
        tv_meinfo.setOnClickListener(this)
    }

    private fun initanimation() {
        rv_vip.adapter = MeVipAdapter(mevips!!, mContext)
        rv_vip.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        OverScrollDecoratorHelper.setUpOverScroll(rv_vip, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
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

    override fun onResume() {
        super.onResume()
        if (!SomeThingApp.instance.isNeedLogin) {
            Glide.with(this).load(SomeThingApp.instance.getUser().portrait).apply(mRequestOptions).into(iv_user)
            tv_me_login.visibility = TextView.GONE
            ll_user.visibility = LinearLayout.VISIBLE
            startAnimatorSet()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_me_login -> {//登录
                val intent = Intent()
                intent.setClass(mContext, LoginActivity::class.java)
                startActivityForResult(intent, 1001)
            }
            R.id.tv_name, R.id.tv_meinfo, R.id.iv_user -> {//个人信息
                if (!SomeThingApp.instance.isNeedLogin) {
                    val intent = Intent()
                    intent.setClass(mContext, MeInfoActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent()
                    intent.setClass(mContext, LoginActivity::class.java)
                    startActivityForResult(intent, 1003)
                }
            }
            R.id.iv_set -> {//设置
                val intent = Intent()
                intent.setClass(mContext, SettingActivity::class.java)
                startActivityForResult(intent, 1002)
            }
            R.id.tv_feed -> {//反馈
            }
            R.id.tv_favor -> {//收藏
            }
            R.id.tv_skin -> {//皮肤
            }
            R.id.tv_service -> {//服务
            }
            R.id.tv_order -> {//订单
            }
            R.id.tv_gy -> {//公益
            }
            R.id.tv_card -> {//卡券
            }
            R.id.tv_money -> {//钱包
            }
            R.id.tv_gift -> {//礼物
            }
            R.id.tv_about -> {//关于
                val intent = Intent()
                intent.setClass(mContext, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SomeThingApp.RESULT_OK) {
            when (requestCode) {
                1001 -> {
                    tv_me_login.visibility = TextView.GONE
                    ll_user.visibility = LinearLayout.VISIBLE
                    startAnimatorSet()
                }

                1002 -> {
                    iv_user.setImageResource(R.drawable.ic_app)
                    tv_me_login.visibility = TextView.VISIBLE
                    ll_user.visibility = LinearLayout.GONE
                    startOutAnimator()
                }

                1003 -> {
                    val intent = Intent()
                    intent.setClass(mContext, LoginActivity::class.java)
                    startActivityForResult(intent, 1001)
                }
            }
        }
    }

    private fun startOutAnimator() {
        val currentX = rl_user.translationX
        val currentY = rl_user.translationY
        val ofFloatX = ObjectAnimator.ofFloat(rl_user, "translationX", currentX, 0f)
        val ofFloatY = ObjectAnimator.ofFloat(rl_user, "translationY", currentY, 0f)
        animatorlist!!.clear()
        animatorlist!!.add(ofFloatX)
        animatorlist!!.add(ofFloatY)
        animatorSet.playTogether(animatorlist)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    private fun startAnimatorSet() {
        val currentX = rl_user.translationX
        val currentY = rl_user.translationY
        val ofFloatX = ObjectAnimator.ofFloat(rl_user, "translationX", currentX, -240f)
        val ofFloatY = ObjectAnimator.ofFloat(rl_user, "translationY", currentY, -50f)
        animatorlist!!.clear()
        animatorlist!!.add(ofFloatX)
        animatorlist!!.add(ofFloatY)
        animatorSet.playTogether(animatorlist)
        animatorSet.duration = 1000
        animatorSet.start()
    }
}