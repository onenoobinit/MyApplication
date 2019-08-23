package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.SportContract
import com.ityzp.something.presenter.SportPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class SportsFragment : MvpFragment<SportContract.sportView, SportPresenter>(), SportContract.sportView {
    override fun initPresenter(): SportPresenter {
        return SportPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_sport

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getSport() {
    }

}