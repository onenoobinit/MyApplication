package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.InternationContract
import com.ityzp.something.presenter.InternationPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class InternationFragment : MvpFragment<InternationContract.internationView, InternationPresenter>(),
    InternationContract.internationView {
    override fun initPresenter(): InternationPresenter {
        return InternationPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_internation

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getInternaiton() {
    }

}