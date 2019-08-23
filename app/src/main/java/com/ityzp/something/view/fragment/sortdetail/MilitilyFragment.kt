package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.MilitilyContract
import com.ityzp.something.presenter.MilitilyPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class MilitilyFragment : MvpFragment<MilitilyContract.militilyView, MilitilyPresenter>(),
    MilitilyContract.militilyView {
    override fun initPresenter(): MilitilyPresenter {
        return MilitilyPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_militily

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getMilitily() {
    }
}