package com.ityzp.something.view.fragment.sortdetail

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpFragment
import com.ityzp.something.R
import com.ityzp.something.contract.NovelContract
import com.ityzp.something.presenter.NovelPresenter

/**
 * Created by wangqiang on 2019/8/20.
 */
class NovelFrament : MvpFragment<NovelContract.novelView, NovelPresenter>(), NovelContract.novelView {
    override fun initPresenter(): NovelPresenter {
        return NovelPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_novel

    override fun finishCreateView(state: Bundle?) {
    }

    override fun getNovel() {
    }

}