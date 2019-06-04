package com.ityzp.something.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.ApplicationUtils
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.widgets.dialog.UpDateDiaolog
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_head.*

/**
 * 关于
 * Created by wangqiang on 2019/5/29.
 */
class AboutActivity : BaseActivity(), View.OnClickListener {
    private var updateDialog: UpDateDiaolog? = null
    override val layoutId: Int
        get() = R.layout.activity_about

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        rl_ahout_update.setOnClickListener(this)
        rl_ahout_feedback.setOnClickListener(this)
        rl_about_help.setOnClickListener(this)
        val verName = ApplicationUtils.getVerName(this)
        tv_versionname.setText("v " + verName)
    }

    override fun initToolBar() {
        tb_title.setText("关于")
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_ahout_update -> {
                if (updateDialog == null) {
                    updateDialog = UpDateDiaolog(this)
                }
                updateDialog!!.show()
            }
            R.id.rl_ahout_feedback -> {
                if (!SomeThingApp.instance.isNeedLogin) {
                    val intent = Intent()
                    intent.setClass(this@AboutActivity, FeedBackActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.rl_about_help -> {
                ToastUtil.show(this, "功能开发中！")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (updateDialog != null) {
            updateDialog!!.dismiss()
        }
    }
}
