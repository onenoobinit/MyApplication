package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.MsgContentContract
import com.ityzp.something.presenter.MsgContentPresenter
import kotlinx.android.synthetic.main.home_marquee.*

/**
 * Created by wangqiang on 2019/7/12.
 */
class MsgContentFragment:MvpFragment<MsgContentContract.msgContentView,MsgContentPresenter>(),MsgContentContract.msgContentView{
    override fun initPresenter(): MsgContentPresenter {
        return MsgContentPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_msg_content

    override fun finishCreateView(state: Bundle?) {
        tv_content.text = "关注"
    }

    override fun getMsgContent() {
    }

}
