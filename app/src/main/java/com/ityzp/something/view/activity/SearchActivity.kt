package com.ityzp.something.view.activity

import android.os.Bundle
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.contract.SearchContract
import com.ityzp.something.presenter.SearchPresenter

class SearchActivity : MvpActivity<SearchContract.searchView, SearchPresenter>(), SearchContract.searchView {
    override fun initPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
    }

    override fun initToolBar() {
    }

    override fun getSearch() {
    }


}
