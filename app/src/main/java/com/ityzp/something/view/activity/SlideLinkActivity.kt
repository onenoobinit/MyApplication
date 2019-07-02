package com.ityzp.something.view.activity

import android.os.Bundle
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import kotlinx.android.synthetic.main.activity_head.*

class SlideLinkActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_slide_link

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
    }

    override fun initToolBar() {
        tb_title.setText("滑动联动")
        toobar.setNavigationOnClickListener { finish() }
    }

}
