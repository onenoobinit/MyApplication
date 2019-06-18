package com.ityzp.something.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import android.view.View
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.presenter.MyAddressActivity
import com.ityzp.something.widgets.DataCleanManager
import com.ityzp.something.widgets.dialog.ClearCacheDialog
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 设置
 * Created by wangqiang on 2019/5/23.
 */
class SettingActivity : BaseActivity(), View.OnClickListener {
    private var clearCacheDialog: ClearCacheDialog? = null
    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        try {
            val totalCacheSize = DataCleanManager.getTotalCacheSize(this)
            tv_set_cache.setText(totalCacheSize)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        tv_out.setOnClickListener(this)
        rl_set_change.setOnClickListener(this)
        rl_set_common.setOnClickListener(this)
        rl_set_clear.setOnClickListener(this)
        rl_set_address.setOnClickListener(this)
        st_mode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(0)
            } else {
                AppCompatDelegate.setDefaultNightMode(5)
            }
        }
    }

    override fun initToolBar() {
        tb_title.setText("设置")
        toobar.setNavigationOnClickListener { finish() }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_out -> {
                SomeThingApp.instance.setUser(null, false)
                setResult(SomeThingApp.RESULT_OK)
                finish()
            }

            R.id.rl_set_change -> {
                ToastUtil.show(this, "功能开发中！")
            }

            R.id.rl_set_common -> {
                ToastUtil.show(this, "功能开发中！")
            }

            R.id.rl_set_address ->{
                val intent = Intent()
                intent.setClass(this, MyAddressActivity::class.java)
                startActivity(intent)
            }

            R.id.rl_set_clear -> {
                if (clearCacheDialog == null) {
                    clearCacheDialog = ClearCacheDialog(this)
                }
                clearCacheDialog!!.setonSureListener = {
                    clearcache()
                }
                clearCacheDialog!!.show()
            }
        }
    }

    private fun clearcache() {
        DataCleanManager.clearAllCache(this)
        tv_set_cache.setText("0.0MB")
        clearCacheDialog!!.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (clearCacheDialog != null) {
            clearCacheDialog!!.dismiss()
        }
    }
}
