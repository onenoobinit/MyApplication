package com.ityzp.something.view.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.adapter.MessageAdapter
import com.ityzp.something.contract.MessageContract
import com.ityzp.something.moudle.MessageInfo
import com.ityzp.something.presenter.MessagePresenter
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : MvpActivity<MessageContract.messageView, MessagePresenter>(), MessageContract.messageView,
    View.OnClickListener {
    private var newList: ArrayList<MessageInfo> = ArrayList()
    private var outList: ArrayList<MessageInfo> = ArrayList()
    private var totalList: ArrayList<MessageInfo> = ArrayList()
    private var datas: ArrayList<MessageInfo> = ArrayList()

    override fun initPresenter(): MessagePresenter {
        return MessagePresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_message

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        for (i in 0..2) {
            if (i == 0) {
                var messageInfo: MessageInfo = MessageInfo()
                messageInfo.viewType = 0
                messageInfo.title = "优惠促销"
                messageInfo.content = "iPhone XR低至4499，iPad Air低至3200，欢迎购买！"
                messageInfo.time = "10:00"
                datas.add(messageInfo)
            } else if (i == 1) {
                var messageInfo: MessageInfo = MessageInfo()
                messageInfo.viewType = 0
                messageInfo.title = "武极电脑DIY旗舰店"
                messageInfo.content = "订单号：7889581948013467796634"
                messageInfo.time = "2018/08/29"
                datas.add(messageInfo)
            } else if (i == 2) {
                var messageInfo: MessageInfo = MessageInfo()
                messageInfo.viewType = 0
                messageInfo.title = "账户通知"
                messageInfo.content = "你有一张神秘优惠券待领取，满129减30，快来购买！"
                messageInfo.time = "昨天"
                datas.add(messageInfo)
            }
        }
        initData()
//        mPresenter.getMessage()
    }

    private fun initData() {
        totalList.clear()
        newList.clear()
        outList.clear()
        for (i in datas.indices) {
            if (!!datas.get(i).content!!.contains("订单号")) {
                outList.add(datas.get(i))
            } else {
                newList.add(datas.get(i))
            }
        }

        if (newList != null && newList.size > 0) {
            val messageInfo1 = MessageInfo()
            messageInfo1.viewType = 1
            totalList.add(messageInfo1)
            totalList.addAll(newList)
        }

        if (outList != null && outList.size > 0) {
            val messageInfo1 = MessageInfo()
            messageInfo1.viewType = 1
            totalList.add(messageInfo1)
            totalList.addAll(outList)
        }
        val manager = GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false)
        rv_message.layoutManager = manager
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(i: Int): Int {
                return 1
            }
        }
        val messageAdapter = MessageAdapter(this, totalList)
        rv_message.adapter = messageAdapter
        messageAdapter.setOnItemListener = {
            ToastUtil.show(this, it)
        }
    }

    override fun initToolBar() {
        tb_title.setText("消息")
        toobar.setNavigationOnClickListener { finish() }
        iv_toorbar.visibility = ImageView.VISIBLE
        iv_toorbar.setOnClickListener(this)
    }

    override fun getMessage() {

    }

    override fun removeMessage() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_toorbar -> {
                ToastUtil.show(this, "清空消息！")
//                mPresenter.getMessage()
            }


        }
    }
}
