package com.ityzp.something.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.adapter.GridImageAdapter
import com.ityzp.something.contract.FeedBackContract
import com.ityzp.something.presenter.FeedBackPresenter
import com.ityzp.something.widgets.FullyGridLayoutManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.android.synthetic.main.activity_feed_back.*
import kotlinx.android.synthetic.main.activity_head.*

/**
 * 意见反馈
 * Created by wangqiang on 2019/5/29.
 */
class FeedBackActivity : MvpActivity<FeedBackContract.feedBackView, FeedBackPresenter>(),
    FeedBackContract.feedBackView {
    private var mode: Boolean = true
    private var selectList: ArrayList<LocalMedia> = ArrayList()
    private var adapter: GridImageAdapter? = null
    override fun initPresenter(): FeedBackPresenter {
        return FeedBackPresenter()
    }

    private val onAddPicClickListener = object : GridImageAdapter.onAddPicClickListener {
        override fun onAddPicClick() {
//            val mode = cb_mode.isChecked()
            if (mode) {
                PictureSelector.create(this@FeedBackActivity)
                    .openGallery(PictureMimeType.ofImage())
                    .theme(R.style.picture_QQ_style)
                    .minSelectNum(1)
                    .maxSelectNum(9)
                    .imageSpanCount(4)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .previewImage(true)
                    .previewVideo(false)
                    .enablePreviewAudio(false)
                    .isCamera(true)
                    .isZoomAnim(true)
                    .enableCrop(false)
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
            } else {
                // 单独拍照
                /*PictureSelector.create(this@MainActivity)
                    .openCamera(chooseMode)// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                    .theme(themeId)// 主题样式设置 具体参考 values/styles
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .selectionMode(
                        if (cb_choose_mode.isChecked()) {
                            PictureConfig.MULTIPLE
                        } else {
                        }
                                PictureConfig . SINGLE
                    )// 多选 or 单选
                    .previewImage(cb_preview_img.isChecked())// 是否可预览图片
                    .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
                    .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                    .isCamera(cb_isCamera.isChecked())// 是否显示拍照按钮
                    .enableCrop(cb_crop.isChecked())// 是否裁剪
                    .compress(cb_compress.isChecked())// 是否压缩
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(if (cb_hide.isChecked()) false else true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(cb_isGif.isChecked())// 是否显示gif图片
                    .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
                    .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
                    .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled() // 裁剪是否可旋转图片
                    //.scaleEnabled()// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()////显示多少秒以内的视频or音频也可适用
                    .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code*/
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SomeThingApp.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                selectList = PictureSelector.obtainMultipleResult(data) as ArrayList<LocalMedia>
                for (media in selectList) {
                    Log.i("图片-----》", media.path)
                }
                adapter!!.setList(selectList)
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    override val layoutId: Int
        get() = R.layout.activity_feed_back

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)

        val fullyGridLayoutManager = FullyGridLayoutManager(this, 4, androidx.recyclerview.widget.GridLayoutManager.VERTICAL, false)
        rv_feedback.layoutManager = fullyGridLayoutManager
        adapter = GridImageAdapter(this, onAddPicClickListener)
        adapter!!.setList(selectList)
        adapter!!.setSelectMax(9)
        rv_feedback.adapter = adapter

        tv_submit.setOnClickListener {
            if (TextUtils.isEmpty(edit_feedback_content.text.toString().trim())) {
                ToastUtil.show(this, "请输入您的意见和问题！")
                return@setOnClickListener
            }else if (selectList.size == 0) {
                ToastUtil.show(this, "请选择图片！")
                return@setOnClickListener
            }else{
//                mPresenter.getFeedBack()
            }
        }
    }

    override fun initToolBar() {
        tb_title.setText("意见反馈")
        toobar.setNavigationOnClickListener { finish() }
        tv_submit.visibility = TextView.VISIBLE
    }

    override fun getFeedBack() {

    }

}
