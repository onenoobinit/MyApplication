package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.HotSpotContract
import com.ityzp.something.presenter.HotSpotPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class HotspotFragment :MvpFragment<HotSpotContract.hotspotView,HotSpotPresenter>(),HotSpotContract.hotspotView{
    override fun initPresenter(): HotSpotPresenter {
        return HotSpotPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_hotspot

    override fun finishCreateView(state: Bundle?) {

    }

    override fun getHotSpot() {

    }
}