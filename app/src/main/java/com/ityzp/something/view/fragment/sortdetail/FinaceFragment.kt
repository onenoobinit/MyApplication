package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.FinaceContract
import com.ityzp.something.presenter.FinacePresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class FinaceFragment : MvpFragment<FinaceContract.finaceView, FinacePresenter>(), FinaceContract.finaceView {
    override fun initPresenter(): FinacePresenter {
        return FinacePresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_finace

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getFinace() {
    }

}