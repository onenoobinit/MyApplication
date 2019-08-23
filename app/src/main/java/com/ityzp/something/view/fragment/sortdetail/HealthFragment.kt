package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.HealthContract
import com.ityzp.something.presenter.HealthPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class HealthFragment : MvpFragment<HealthContract.healthView, HealthPresenter>(), HealthContract.healthView {
    override fun initPresenter(): HealthPresenter {
        return HealthPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_health

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getHealth() {
    }

}