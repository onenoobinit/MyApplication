package com.ityzp.something.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.contract.MeInfoContract
import com.ityzp.something.presenter.MeInfoPresenter
import com.ityzp.something.widgets.GlideCircleTransform
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_me_info.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 个人信息
 * Created by wangqiang on 2019/5/28.
 */
class MeInfoActivity : MvpActivity<MeInfoContract.meInfoView, MeInfoPresenter>(), MeInfoContract.meInfoView,
    View.OnClickListener {
    private val mRequestOptions = RequestOptions.placeholderOf(R.drawable.ic_app).transform(GlideCircleTransform())


    override fun initPresenter(): MeInfoPresenter {
        return MeInfoPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_me_info

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        rl_meinfo_pic.setOnClickListener(this)
        OverScrollDecoratorHelper.setUpOverScroll(sv_meinfo)
    }

    override fun initToolBar() {
        toobar.setNavigationOnClickListener { finish() }
        tb_title.setText("个人中心")
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_meinfo_pic -> openGallery()
        }
    }

    private fun openGallery() {
        PictureSelector.create(this)
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
        if (resultCode == -1) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val medialist = PictureSelector.obtainMultipleResult(data)
                    for (i in medialist.indices) {
                        val localMedia = medialist.get(i)
                        var filePath = ""
                        if (localMedia.isCompressed()) {
                            filePath = localMedia.getCompressPath()
                        }
                        Glide.with(this).load(filePath).apply(mRequestOptions).into(iv_pic)
                        SomeThingApp.instance.getUser().portrait = filePath

                        val file = File(filePath)
                        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
                        val mBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
                        //暂不请求 没有后台接口处理
//                        mPresenter.upLoadPicture(mBody, this@MeInfoActivity)
                    }
                }
            }
        }
    }

    override fun upLoadPicture(imgurl: String?) {
        /*SomeThingApp.instance.getUser().portrait = imgurl
        setResult(SomeThingApp.RESULT_OK)*/
    }
}
