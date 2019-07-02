package com.ityzp.something.view.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.gelitenight.waveview.library.WaveView
import com.ityzp.something.R
import com.ityzp.something.widgets.WaveHelper
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_specisal_basic.*

class SpecisalBasicActivity : BaseActivity(), View.OnClickListener {
    var waveFloat = 1.0f
    var waveHelper: WaveHelper? = null
    override val layoutId: Int
        get() = R.layout.activity_specisal_basic

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        waveview.setShapeType(WaveView.ShapeType.CIRCLE)
        waveview.setWaveColor(Color.parseColor("#D7FCFF"), Color.parseColor("#B3DEFF"))
        waveview.setBorder(2, Color.parseColor("#4EB2FF"))
        waveHelper = WaveHelper(waveview, waveFloat)
        waveHelper!!.start()
        iv_add.setOnClickListener(this)
        iv_del.setOnClickListener(this)
    }

    override fun initToolBar() {
        tb_title.setText("特殊控件")
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_add -> {
                if (waveFloat == 1.0f) {
                    waveHelper = WaveHelper(waveview, waveFloat)
                    waveHelper!!.start()
                    ToastUtil.show(this,"已经是最大值了！")
                } else {
                    waveFloat = waveFloat + 0.1f
                    waveHelper = WaveHelper(waveview, waveFloat)
                    waveHelper!!.start()
                }
            }

            R.id.iv_del -> {
                if (waveFloat == 0.0f) {
                    waveHelper = WaveHelper(waveview, waveFloat)
                    waveHelper!!.start()
                    ToastUtil.show(this,"已经是最小值了！")
                } else {
                    waveFloat = waveFloat - 0.1f
                    waveHelper = WaveHelper(waveview, waveFloat)
                    waveHelper!!.start()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        waveHelper!!.start()
    }

    override fun onPause() {
        super.onPause()
        waveHelper!!.cancel()
    }
}
