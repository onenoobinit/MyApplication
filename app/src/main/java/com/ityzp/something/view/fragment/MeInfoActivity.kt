package com.ityzp.something.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.contract.MeInfoContract
import com.ityzp.something.presenter.MeInfoPresenter
import com.ityzp.something.view.activity.ChangeNameActivity
import com.ityzp.something.widgets.GlideCircleTransform
import com.ityzp.something.widgets.dialog.SexSelectDialog
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
import java.text.SimpleDateFormat
import java.util.*

/**
 * 个人信息
 * Created by wangqiang on 2019/5/28.
 */
class MeInfoActivity : MvpActivity<MeInfoContract.meInfoView, MeInfoPresenter>(), MeInfoContract.meInfoView,
    View.OnClickListener {

    private val mRequestOptions = RequestOptions.placeholderOf(R.drawable.ic_app).transform(GlideCircleTransform())
    private var sexSelectDialog: SexSelectDialog? = null

    override fun initPresenter(): MeInfoPresenter {
        return MeInfoPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_me_info

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        OverScrollDecoratorHelper.setUpOverScroll(sv_meinfo)
        tv_meinfo_name.setText(SomeThingApp.instance.getUser().nickName)
        tv_meinfo_sex.setText(SomeThingApp.instance.getUser().sex)
        tv_meinfo_brithday.setText(SomeThingApp.instance.getUser().birthday)
        Glide.with(this).load(SomeThingApp.instance.getUser().portrait).apply(mRequestOptions).into(iv_pic)
        rl_meinfo_pic.setOnClickListener(this)
        rl_meinfo_name.setOnClickListener(this)
        rl_meinfo_sex.setOnClickListener(this)
        rl_meinfo_brithday.setOnClickListener(this)
    }

    override fun initToolBar() {
        toobar.setNavigationOnClickListener { finish() }
        tb_title.setText("个人中心")
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_meinfo_pic -> openGallery()
            R.id.rl_meinfo_name -> {//昵称
                val intent = Intent()
                intent.setClass(this, ChangeNameActivity::class.java)
                startActivityForResult(intent, 1004)
            }
            R.id.rl_meinfo_sex -> {//性别
                if (sexSelectDialog == null) {
                    sexSelectDialog = SexSelectDialog(this)
                }
                sexSelectDialog!!.setOnsexListener = { sex ->
                    tv_meinfo_sex.setText(sex)
                    val user = SomeThingApp.instance.getUser()
                    user.sex = sex
                    SomeThingApp.instance.setUser(user, true)
                }
                sexSelectDialog!!.show()
            }
            R.id.rl_meinfo_brithday -> {//出生日期
                showTimeDialog(tv_meinfo_brithday)
            }
        }
    }

    private fun showTimeDialog(tv_meinfo_brithday: TextView?) {
        val selectedDate = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.set(1920, 1, 1)
        val endDate = Calendar.getInstance()
        endDate.set(2030, 1, 1)
        val pvTime = TimePickerBuilder(this,
            OnTimeSelectListener { date, v -> getchageDate(getTime(date)) })
            .setRangDate(startDate, endDate)
            .setTitleText("请选择日期")
            .setDate(selectedDate)
            .setTitleColor(ContextCompat.getColor(this, R.color.tb_bg))
            .setCancelColor(ContextCompat.getColor(this, R.color.tb_bg))
            .setSubmitColor(ContextCompat.getColor(this, R.color.tb_bg))
            //默认设置为年月日时分秒
            .setLabel("年", "月", "日", "时", "分", "秒")
            // 默认全部显示
            .setType(booleanArrayOf(true, true, true, false, false, false))
            .build()
        pvTime.show()
    }

    private fun getTime(date: Date?): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    private fun getchageDate(time: String) {
//        mPresenter.getBrithday(time)
        val user = SomeThingApp.instance.getUser()
        user.birthday = time
        SomeThingApp.instance.setUser(user, true)
        tv_meinfo_brithday.setText(time)
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
        if (resultCode == SomeThingApp.RESULT_OK) {
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
                        val user = SomeThingApp.instance.getUser()
                        user.portrait = filePath
                        SomeThingApp.instance.setUser(user, true)

                        val file = File(filePath)
                        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
                        val mBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
                        //暂不请求 没有后台接口处理
//                        mPresenter.upLoadPicture(mBody, this@MeInfoActivity)
                    }
                }

                1004 -> {
                    tv_meinfo_name.setText(SomeThingApp.instance.getUser().nickName)
                }
            }
        }
    }

    override fun upLoadPicture(imgurl: String?) {
        /*SomeThingApp.instance.getUser().portrait = imgurl
        setResult(SomeThingApp.RESULT_OK)*/
    }

    override fun getBrithday() {

    }
}
