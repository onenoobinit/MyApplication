package com.ityzp.something.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.contract.SearchContract
import com.ityzp.something.presenter.SearchPresenter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_top.*

class SearchActivity : MvpActivity<SearchContract.searchView, SearchPresenter>(), SearchContract.searchView,
    View.OnClickListener {

    override fun initPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        initData()

        iv_search_back.setOnClickListener(this)
        tv_search.setOnClickListener(this)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//                arlX.setVisibility(View.VISIBLE)
                rl_search_result.setVisibility(View.VISIBLE)
//                ContentAdapter.canClick = 1
//                contentAdapter.notifyDataSetChanged()
//                rvContent.setVisibility(View.GONE)
//                mFuzzySearchAdapter.getFilter().filter(charSequence.toString().trim { it <= ' ' })
            }

            override fun afterTextChanged(editable: Editable) {
                val s = editable.toString()
                if (TextUtils.isEmpty(s)) {
//                    arlX.setVisibility(View.GONE)
                    rl_search_result.setVisibility(View.GONE)
//                    rvContent.setVisibility(View.VISIBLE)
//                    ContentAdapter.canClick = 0
//                    contentAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun initData() {
        var hotdatas = ArrayList<String>()
        hotdatas.clear()
        hotdatas.add("锦江乐园")
        hotdatas.add("外滩")
        hotdatas.add("上海海昌海洋公园")
        hotdatas.add("YVR")
        hotdatas.add("驴小样驴肉火烧")
        hotdatas.add("五角场")
        hotdatas.add("小龙虾")

        val layoutParams =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (fl_hot != null) {
            fl_hot.removeAllViews()
        }
        for (i in hotdatas.indices) {
            var tv = TextView(this)
            tv.setPadding(28, 10, 28, 10)
            layoutParams.setMargins(10, 5, 10, 5)
            tv.setText(hotdatas.get(i))
            tv.maxEms = 7
            tv.setSingleLine()
            tv.setBackgroundResource(R.drawable.tv_search_hot)
            tv.layoutParams = layoutParams
            fl_hot.addView(tv, layoutParams)
        }


        var historydatas = ArrayList<String>()
        historydatas.clear()
        historydatas.add("蜘蛛侠")
        historydatas.add("火锅")
        historydatas.add("欢乐谷")
        historydatas.add("拔罐")
        historydatas.add("玩")
        historydatas.add("许记龙虾烧烤")
        historydatas.add("XCAPE异时刻秘密")
        historydatas.add("美容养生")
        historydatas.add("魔都HB CLUB")

        val lp =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (fl_history != null) {
            fl_history.removeAllViews()
        }
        for (i in historydatas.indices) {
            var tv = TextView(this)
            tv.setPadding(28, 10, 28, 10)
            layoutParams.setMargins(10, 5, 10, 5)
            tv.setText(historydatas.get(i))
            tv.maxEms = 7
            tv.setSingleLine()
            tv.setBackgroundResource(R.drawable.tv_search_hot)
            tv.layoutParams = layoutParams
            fl_history.addView(tv, layoutParams)
        }

    }

    override fun initToolBar() {
    }

    override fun getSearch() {
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_search_back -> finish()

            R.id.tv_search -> {

            }
        }
    }
}
