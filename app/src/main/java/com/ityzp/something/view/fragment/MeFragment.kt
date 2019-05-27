package com.ityzp.something.view.fragment

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
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
import com.example.baseklibrary.utils.L
import com.ityzp.something.R
import com.ityzp.something.adapter.MeVipAdapter
import com.ityzp.something.contract.MeContract
import com.ityzp.something.moudle.MeInfo
import com.ityzp.something.presenter.MePresenter
import com.ityzp.something.view.activity.LoginActivity
import com.ityzp.something.view.activity.SettingActivity
import com.ityzp.something.widgets.GlideCircleTransform
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.fragment_me.*
import me.everything.android.ui.overscroll.IOverScrollUpdateListener
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * Created by wangqiang on 2019/5/23.
 */

class MeFragment : MvpFragment<MeContract.meView, MePresenter>(), MeContract.meView, View.OnClickListener {
    private var viewHeight: Int? = 0
    private var old_offset: Int? = 0
    private var mevips: ArrayList<String>? = ArrayList()
    val animatorSet = AnimatorSet()
    var animatorlist: ArrayList<Animator>? = arrayListOf()
    private var requestBody: RequestBody? = null
    private var mBody: MultipartBody.Part? = null
    private val TAKE_PHOTO_REQUEST_CODE: Int = 1
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_me_login -> {//登录
                val intent = Intent()
                intent.setClass(mContext, LoginActivity::class.java)
                startActivityForResult(intent, 1001)
            }
            R.id.iv_user -> {//选择头像
                openGallery()
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
            }
        }
    }


    private fun openGallery() {
        PictureSelector.create(mContext as Activity)
            .openGallery(PictureMimeType.ofImage())
            .theme(R.style.picture_QQ_style)
            .imageSpanCount(4)
            .selectionMode(PictureConfig.SINGLE)
            .previewImage(true)
            .previewVideo(false)
            .enablePreviewAudio(false)
            .isCamera(true)
            .isZoomAnim(true)
            .enableCrop(true)
            .compress(true)
            .synOrAsy(true)
            .glideOverride(160, 160)
            .withAspectRatio(1, 1)
            .hideBottomControls(true)
            .isGif(false)
            .freeStyleCropEnabled(true)
            .circleDimmedLayer(false)
            .showCropFrame(true)
            .showCropGrid(true)
            .openClickSound(false)
            .isDragFrame(false)
            .minimumCompressSize(100)
            .rotateEnabled(false)
            .scaleEnabled(true)
            //结果回调onActivityResult code
            .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == 1) {
            tv_me_login.visibility = TextView.GONE
            ll_user.visibility = LinearLayout.VISIBLE
            startAnimatorSet()
        }

        if (requestCode == 1002 && resultCode == 2) {
            tv_me_login.visibility = TextView.VISIBLE
            ll_user.visibility = LinearLayout.GONE
            startOutAnimator()
        }
        L.i("AAAA", "我走了re" + resultCode)
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == -1) {
            L.i("AAAA", "我走了" + requestCode)
            val medialist = PictureSelector.obtainMultipleResult(data)
            for (i in medialist.indices) {
                val localMedia = medialist.get(i)
                var filePath = ""
                if (localMedia.isCompressed()) {
                    filePath = localMedia.getCompressPath()
                }
                L.i("AAAA", filePath)
                Glide.with(this).load(filePath).apply(mRequestOptions).into(iv_user)
                val file = File(filePath)
                val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
                val mBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
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