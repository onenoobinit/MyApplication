package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.CarContract
import com.ityzp.something.presenter.CarPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class CarFragment : MvpFragment<CarContract.carView, CarPresenter>(), CarContract.carView {
    override fun initPresenter(): CarPresenter {
        return CarPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_car

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getCar() {
    }

}