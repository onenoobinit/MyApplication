package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.RecommentContract
import com.ityzp.something.presenter.RecommentPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class RecommentFragment :MvpFragment<RecommentContract.recommentView,RecommentPresenter>(),RecommentContract.recommentView{
    override fun initPresenter(): RecommentPresenter {
        return RecommentPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_recomment

    override fun finishCreateView(state: Bundle?) {
//        tv_content.text = "推荐"
    }

    override fun getRecomment() {
    }
}