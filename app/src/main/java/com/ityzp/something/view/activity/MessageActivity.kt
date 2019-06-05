package com.ityzp.something.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.contract.MessageContract
import com.ityzp.something.presenter.MessagePresenter
import kotlinx.android.synthetic.main.activity_head.*

class MessageActivity : MvpActivity<MessageContract.messageView, MessagePresenter>(), MessageContract.messageView,
    View.OnClickListener {

    override fun initPresenter(): MessagePresenter {
        return MessagePresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_message

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
    }

    override fun initToolBar() {
        tb_title.setText("消息")
        toobar.setNavigationOnClickListener { finish() }
        iv_toorbar.visibility = ImageView.VISIBLE
        iv_toorbar.setOnClickListener(this)
    }

    override fun getMessage() {
    }

    override fun onClick(v: View?) {

    }
}
