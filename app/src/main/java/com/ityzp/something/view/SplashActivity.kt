package com.ityzp.something.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.baseklibrary.base.BaseActivity
import com.ityzp.something.MainActivity
import com.ityzp.something.R

class SplashActivity : BaseActivity() {
    private val mHandler = Handler()
    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun initViews(savedInstanceState: Bundle?) {
        goActivity()
    }

    private fun goActivity() {
        //是否登录
//        val isFrist = SPUtil.get(this, "isFrist", false) as Boolean
        mHandler.postDelayed({
            /*if (isFrist == true) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
            } else if (isFrist == false) {
                startActivity(Intent(this@SplashActivity, LeadActivity::class.java))
                SPUtil.put(this, "isFrist", true)
            }*/
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
            finish()
        }, 1000)
    }

    override fun initToolBar() {
    }

}
