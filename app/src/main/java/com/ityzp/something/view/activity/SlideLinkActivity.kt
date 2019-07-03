package com.ityzp.something.view.activity

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.L
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.google.gson.Gson
import com.ityzp.something.R
import com.ityzp.something.moudle.SortBean
import com.ityzp.something.view.fragment.SortDetailFragment
import com.ityzp.something.widgets.slide.CheckListener
import com.ityzp.something.widgets.slide.ItemHeaderDecoration
import com.ityzp.something.widgets.slide.RvListener
import com.ityzp.something.widgets.slide.SortAdapter
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_slide_link.*
import java.io.IOException

class SlideLinkActivity : BaseActivity(), CheckListener {
    override fun check(position: Int, isScroll: Boolean) {
        check(position, isScroll)
    }

    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mSortBean: SortBean? = null
    private var isMoved: Boolean = false
    private var targetPosition = 0
    private var mSortAdapter: SortAdapter? = null
    private var mSortDetailFragment: SortDetailFragment? = null
    override val layoutId: Int
        get() = R.layout.activity_slide_link

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        initView()
        initData()
    }

    private fun initData() {
        val assetsData = getAssetsData("sort.json")
        var gson = Gson()
        mSortBean = gson.fromJson<Any>(assetsData, SortBean::class.java) as SortBean?
        val categoryOneArray = mSortBean!!.categoryOneArray
        var list = ArrayList<String>()
        for (i in categoryOneArray!!.indices) {
            list.add(categoryOneArray.get(i).name!!)
        }
        mSortAdapter = SortAdapter(this, list, object : RvListener {
            override fun onItemClick(id: Int, position: Int) {
                if (mSortDetailFragment != null) {
                    isMoved = true
                    targetPosition = position
                    setChecked(position, true)
                }

                ToastUtil.show(this@SlideLinkActivity, list.get(position))
            }
        })
        rv_sort.setAdapter(mSortAdapter)
        createFragment()
    }

    private fun createFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        mSortDetailFragment = SortDetailFragment()
        val bundle = Bundle()
        bundle.putParcelableArrayList("right", mSortBean!!.categoryOneArray)
        mSortDetailFragment!!.setArguments(bundle)
        mSortDetailFragment!!.setListener(this)
        fragmentTransaction.add(R.id.fl_content, mSortDetailFragment!!)
        fragmentTransaction.commit()
    }

    private fun setChecked(position: Int, isLeft: Boolean) {
        if (isLeft) {
            mSortAdapter!!.setCheckedPosition(position)
            //此处的位置需要根据每个分类的集合来进行计算
            var count = 0
            for (i in 0 until position) {
                count += mSortBean!!.categoryOneArray!!.get(i).categoryTwoArray!!.size
            }
            count += position
            mSortDetailFragment!!.setData(count)
            ItemHeaderDecoration.currentTag = targetPosition.toString()//凡是点击左边，将左边点击的位置作为当前的tag
        } else {
            if (isMoved) {
                isMoved = false
            } else
                mSortAdapter!!.setCheckedPosition(position)
            ItemHeaderDecoration.currentTag = position.toString()//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position)
    }

    private fun moveToCenter(position: Int) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        val childAt = rv_sort.getChildAt(position - mLinearLayoutManager!!.findFirstVisibleItemPosition())
        if (childAt != null) {
            val y = childAt!!.getTop() - rv_sort.getHeight() / 2
            rv_sort.smoothScrollBy(0, y)
        }
    }

    private fun getAssetsData(s: String): String {
        var result: String = ""
        try {
            val open = assets.open(s)
            val available = open.available()
            val buffer = ByteArray(available)
            open.read(buffer)
            open.close()
            result = String(buffer)
            return result
        } catch (e: IOException) {
            e.printStackTrace()
            e.message?.let { L.i("解析json错误", it) }
            return result
        }
    }

    private fun initView() {
        mLinearLayoutManager = LinearLayoutManager(this)
        rv_sort.layoutManager = mLinearLayoutManager
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rv_sort.addItemDecoration(decoration)
    }

    override fun initToolBar() {
        tb_title.setText("滑动联动")
        toobar.setNavigationOnClickListener { finish() }
    }

}
