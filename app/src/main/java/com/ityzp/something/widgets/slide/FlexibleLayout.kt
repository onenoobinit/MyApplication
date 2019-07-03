package com.ityzp.something.widgets.slide

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/7/3.
 */
abstract class FlexibleLayout(context: Context?) : LinearLayout(context) {
    private var mLoadingView: View? = null
    private var mNetworkErrorView: View? = null
    private var mEmptyView: View? = null
    private val mSuccessView: ViewGroup
    private var mLoadingProgress: ProgressBar? = null
    private var mLoadingText: TextView? = null
    private val title: View?


    init {
        orientation = LinearLayout.VERTICAL
        clipToPadding = true
        fitsSystemWindows = true
        View.inflate(context, R.layout.layout_all, this)
        mSuccessView = initNormalView()
        title = mSuccessView.findViewWithTag("title")
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(mSuccessView, lp)
        val childCount = mSuccessView.childCount
        Log.d("count---", childCount.toString())
    }

    abstract fun initNormalView(): ViewGroup

    fun loadData() {
        showPageWithState(State.Loading)
        onLoad()
    }

    abstract fun onLoad()

    fun showPageWithState(status: State) {
        if (status != State.Normal && title != null) {
            val tag = getChildAt(0).tag as String
            if (TextUtils.equals(tag, "title")) {
                //已经有标题栏
            } else {
                mSuccessView.removeView(title)
                addView(title, 0)
            }

        }
        when (status) {
            FlexibleLayout.State.Normal -> {
                mSuccessView.visibility = View.VISIBLE
                val childCount = childCount
                Log.d("count--->", childCount.toString())
                val childAt = mSuccessView.getChildAt(0)//有可能为空
                if (childAt != null) {
                    val tag = childAt.tag as? String
                    if (!TextUtils.equals(tag, "title") && null != title) {
                        removeView(title)
                        mSuccessView.addView(title, 0)
                    }
                }
                if (mLoadingView != null) {
                    mLoadingView!!.visibility = View.GONE
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView!!.visibility = View.GONE
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView!!.visibility = View.GONE
                }
                invalidate()
            }
            FlexibleLayout.State.Loading -> {
                mSuccessView.visibility = View.GONE
                if (mEmptyView != null) {
                    mEmptyView!!.visibility = View.GONE
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView!!.visibility = View.GONE
                }
                if (mLoadingView == null) {
                    val viewStub = findViewById<View>(R.id.vs_loading) as ViewStub
                    mLoadingView = viewStub.inflate()
                    mLoadingProgress = mLoadingView!!.findViewById<View>(R.id.loading_progress) as ProgressBar
                    mLoadingText = mLoadingView!!.findViewById<View>(R.id.loading_text) as TextView
                } else {
                    mLoadingView!!.visibility = View.VISIBLE
                }
                mLoadingProgress!!.visibility = View.VISIBLE
                mLoadingText!!.text = "正在加载"
            }
            FlexibleLayout.State.Empty -> {
                mSuccessView.visibility = View.GONE
                if (mLoadingView != null) {
                    mLoadingView!!.visibility = View.GONE
                }

                if (mNetworkErrorView != null) {
                    mNetworkErrorView!!.visibility = View.GONE
                }

                if (mEmptyView == null) {
                    val viewStub = findViewById<View>(R.id.vs_end) as ViewStub
                    mEmptyView = viewStub.inflate()
                } else {
                    mEmptyView!!.visibility = View.VISIBLE
                }
            }
            FlexibleLayout.State.NetWorkError -> {
                mSuccessView.visibility = View.GONE
                if (mLoadingView != null) {
                    mLoadingView!!.visibility = View.GONE
                }

                if (mEmptyView != null) {
                    mEmptyView!!.visibility = View.GONE
                }

                if (mNetworkErrorView == null) {
                    val viewStub = findViewById<View>(R.id.vs_error) as ViewStub
                    mNetworkErrorView = viewStub.inflate()
                    val btnRetry = mNetworkErrorView!!.findViewById<View>(R.id.btn_retry)
                    btnRetry.setOnClickListener { onLoad() }

                } else {
                    mNetworkErrorView!!.visibility = View.VISIBLE
                }
            }
            else -> {
            }
        }
    }

    enum class State {
        Normal, Empty, Loading, NetWorkError
    }

}