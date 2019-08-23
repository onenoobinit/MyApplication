package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.PictureContract
import com.ityzp.something.presenter.PicturePresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class PictureFragment : MvpFragment<PictureContract.pictureView, PicturePresenter>(), PictureContract.pictureView {
    override fun initPresenter(): PicturePresenter {
        return PicturePresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_picture

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getPicture() {
    }

}