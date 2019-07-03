package com.ityzp.something.view.fragment

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.moudle.RightBean
import com.ityzp.something.moudle.SortBean
import com.ityzp.something.view.activity.SlideLinkActivity
import com.ityzp.something.widgets.slide.*
import java.util.*

/**
 * Created by wangqiang on 2019/7/3.
 */
class SortDetailFragment : SlideBaseFragment<SortDetailPresenter, String>(), CheckListener {
    private var mRv: RecyclerView? = null
    private var mAdapter: ClassifyDetailAdapter? = null
    private var mManager: GridLayoutManager? = null
    private val mDatas = ArrayList<RightBean>()
    private var mDecoration: ItemHeaderDecoration? = null
    private var move = false
    private var mIndex = 0
    private var checkListener: CheckListener? = null


    protected override val layoutId: Int
        get() = R.layout.fragment_sort_detail

    protected override fun initCustomView(view: View) {
        mRv = view.findViewById<View>(R.id.rv) as RecyclerView

    }

    protected override fun initListener() {
        mRv!!.addOnScrollListener(RecyclerViewListener())
    }

    protected override fun initPresenter(): SortDetailPresenter {
        showRightPage(1)
        mManager = GridLayoutManager(mContext, 3)
        //通过isTitle的标志来判断是否是title
        mManager!!.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mDatas[position].isTitle) 3 else 1
            }
        }
        mRv!!.setLayoutManager(mManager)
        mAdapter = ClassifyDetailAdapter(mContext!!, mDatas, object : RvListener {
            override fun onItemClick(id: Int, position: Int) {
                var content = ""
                when (id) {
                    R.id.root -> content = "title"
                    R.id.content -> content = "content"
                }

                mDatas[position].name?.let { ToastUtil.show(mContext!!, it) }
            }
        })

        mRv!!.setAdapter(mAdapter)
        mDecoration = ItemHeaderDecoration(mContext!!, mDatas)
        mRv!!.addItemDecoration(mDecoration!!)
        checkListener?.let { mDecoration!!.setCheckListener(it) }
        initData()
        return SortDetailPresenter()
    }


    private fun initData() {
        val rightList: ArrayList<SortBean.CategoryOneArrayBean> = getArguments()!!.getParcelableArrayList("right")

        for (i in rightList.indices) {
            val head = RightBean(rightList.get(i).name!!)
            //头部设置为true
            head.isTitle = true
            head.titleName = rightList.get(i).name
            head.tag = i.toString()
            mDatas.add(head)
            val categoryTwoArray = rightList.get(i).categoryTwoArray

            for (j in categoryTwoArray!!.indices) {
                val body = RightBean(categoryTwoArray.get(j).name!!)
                body.tag = i.toString()
                val name = rightList.get(i).name
                body.titleName = name
                mDatas.add(body)
            }

        }

        mAdapter!!.notifyDataSetChanged()
        mDecoration!!.setData(mDatas)
    }

    override fun onClick(v: View) {

    }

    override fun refreshView(code: Int, data: String) {

    }

    fun setData(n: Int) {
        mIndex = n
        mRv!!.stopScroll()
        smoothMoveToPosition(n)
    }

    protected override fun getData() {

    }

    fun setListener(listener: SlideLinkActivity) {
        this.checkListener = listener
    }

    private fun smoothMoveToPosition(n: Int) {
        val firstItem = mManager!!.findFirstVisibleItemPosition()
        val lastItem = mManager!!.findLastVisibleItemPosition()
        Log.d("first--->", firstItem.toString())
        Log.d("last--->", lastItem.toString())
        if (n <= firstItem) {
            mRv!!.scrollToPosition(n)
        } else if (n <= lastItem) {
            Log.d("pos---->", n.toString() + "VS" + firstItem)
            val top = mRv!!.getChildAt(n - firstItem).getTop()
            Log.d("top---->", top.toString())
            mRv!!.scrollBy(0, top)
        } else {
            mRv!!.scrollToPosition(n)
            move = true
        }
    }


    override fun check(position: Int, isScroll: Boolean) {
        checkListener?.let { check(position, isScroll) }
    }


    private inner class RecyclerViewListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false
                val n = mIndex - mManager!!.findFirstVisibleItemPosition()
                Log.d("n---->", n.toString())
                if (0 <= n && n < mRv!!.getChildCount()) {
                    val top = mRv!!.getChildAt(n).getTop()
                    Log.d("top--->", top.toString())
                    mRv!!.smoothScrollBy(0, top)
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (move) {
                move = false
                val n = mIndex - mManager!!.findFirstVisibleItemPosition()
                if (0 <= n && n < mRv!!.getChildCount()) {
                    val top = mRv!!.getChildAt(n).getTop()
                    mRv!!.scrollBy(0, top)
                }
            }
        }
    }


}
