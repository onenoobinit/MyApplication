package com.ityzp.something.view.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.SPUtil
import com.example.baseklibrary.utils.StatusBarCompat
import com.google.gson.Gson
import com.ityzp.something.R
import com.ityzp.something.adapter.FuzzySearchAdapter
import com.ityzp.something.contract.SearchContract
import com.ityzp.something.moudle.ItemEntity
import com.ityzp.something.moudle.SearchInfo
import com.ityzp.something.presenter.SearchPresenter
import com.ityzp.something.utils.GetJsonDataUtil
import com.ityzp.something.utils.PinyinUtil
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_top.*
import org.json.JSONArray
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : MvpActivity<SearchContract.searchView, SearchPresenter>(), SearchContract.searchView,
    View.OnClickListener {
    private var history: ArrayList<String>? = null
    override fun initPresenter(): SearchPresenter {
        return SearchPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        history = (SPUtil.getObject(this, "history", ArrayList::class.java) as ArrayList<String>?)
        if (history == null || history!!.size == 0) {
            rl_history.visibility = RelativeLayout.GONE
            fl_history.visibility = FrameLayout.GONE
            history = ArrayList()
        } else {
            rl_history.visibility = RelativeLayout.VISIBLE
            fl_history.visibility = FrameLayout.VISIBLE
        }
        initData()
        iv_search_back.setOnClickListener(this)
        tv_search.setOnClickListener(this)
        iv_delete_search.setOnClickListener(this)

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

//        mPresenter.getSearch()
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


        history = removeDuplicate(history!!) as ArrayList<String>
        Collections.reverse(history)
        val lp =
            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (fl_history != null) {
            fl_history.removeAllViews()
        }
        for (i in history!!.indices) {
            var tv = TextView(this)
            tv.setPadding(28, 10, 28, 10)
            layoutParams.setMargins(10, 5, 10, 5)
            tv.setText(history!!.get(i))
            tv.maxEms = 7
            tv.setSingleLine()
            tv.setBackgroundResource(R.drawable.tv_search_hot)
            tv.layoutParams = layoutParams
            fl_history.addView(tv, layoutParams)
        }

        val jsonData = GetJsonDataUtil().getJson(this, "search.json")
        val parseData = parseData(jsonData)
        var tests = ArrayList<String>()
        tests.clear()
        for (i in parseData.indices) {
            tests.add(
                parseData.get(i).airport + "," + parseData.get(i).cityNameC + "," + parseData.get(
                    i
                ).countryNameC
            )
        }
//        val size = tests.size
        val strings = tests.toTypedArray()
        val fillData = fillData(strings)
        val manager = androidx.recyclerview.widget.LinearLayoutManager(this)
        rv_search_result.layoutManager = manager
        val fuzzySearchAdapter = FuzzySearchAdapter(fillData)
        rv_search_result.adapter = fuzzySearchAdapter
        fuzzySearchAdapter.setOnItemClickListener = {
            //将搜索存入list
            val split = it.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            history!!.add(split[0])
            SPUtil.setObject(this@SearchActivity, "history", history!!)
        }
    }

    private fun fillData(date: Array<String>): List<ItemEntity> {
        val sortList = java.util.ArrayList<ItemEntity>()
        for (item in date) {
            val letter: String
            //汉字转换成拼音
            val pinyinList = PinyinUtil.getPinYinList(item)
            if (pinyinList != null && !pinyinList!!.isEmpty()) {
                // A-Z导航
                val letters = pinyinList!!.get(0).substring(0, 1).toUpperCase()
                // 正则表达式，判断首字母是否是英文字母
                if (letters.matches("[A-Z]".toRegex())) {
                    letter = letters.toUpperCase()
                } else {
                    letter = "#"
                }
            } else {
                letter = "#"
            }
            sortList.add(ItemEntity(item, letter, pinyinList!!))
        }
        return sortList

    }

    fun parseData(result: String): ArrayList<SearchInfo> {//Gson 解析
        val detail = ArrayList<SearchInfo>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson<SearchInfo>(data.optJSONObject(i).toString(), SearchInfo::class.java)
                detail.add(entity)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return detail
    }

    override fun initToolBar() {
    }

    override fun getSearch() {
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_search_back -> finish()

            R.id.tv_search -> {//搜索
                history!!.add(et_search.text.toString().trim())
                SPUtil.setObject(this, "history", history!!)
            }

            R.id.iv_delete_search -> {//清除浏览记录
                rl_history.visibility = RelativeLayout.GONE
                fl_history.visibility = FrameLayout.GONE
                history!!.clear()
                SPUtil.setObject(this, "history", history!!)
            }
        }
    }

    fun removeDuplicate(list: MutableList<String>): List<String> {
        val set = LinkedHashSet<String>()
        set.addAll(list)
        list.clear()
        list.addAll(set)
        return list
    }
}
