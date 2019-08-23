package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.VideoContract
import com.ityzp.something.presenter.ViedeoPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class ViedeoFragment :MvpFragment<VideoContract.videoView,ViedeoPresenter>(),VideoContract.videoView{
    override fun initPresenter(): ViedeoPresenter {
        return ViedeoPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_video

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getVideo() {
    }
}