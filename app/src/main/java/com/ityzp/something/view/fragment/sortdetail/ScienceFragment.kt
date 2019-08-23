package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.ScienceContract
import com.ityzp.something.presenter.SciencePresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class ScienceFragment : MvpFragment<ScienceContract.scienceView, SciencePresenter>(), ScienceContract.scienceView {
    override fun initPresenter(): SciencePresenter {
        return SciencePresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_science

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getScience() {
    }

}