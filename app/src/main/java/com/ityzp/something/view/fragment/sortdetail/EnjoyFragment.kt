package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.EnjoyContract
import com.ityzp.something.presenter.EnjoyPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class EnjoyFragment : MvpFragment<EnjoyContract.enjoyView, EnjoyPresenter>(), EnjoyContract.enjoyView {
    override fun initPresenter(): EnjoyPresenter {
        return EnjoyPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_enjoy

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getEnjoy() {
    }

}