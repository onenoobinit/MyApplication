package com.ityzp.something.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ityzp.something.R
import com.ityzp.something.widgets.slide.FlexibleLayout
import com.ityzp.something.widgets.slide.SlideBasePresenter
import com.ityzp.something.widgets.slide.ViewCallBack

/**
 * Created by wangqiang on 2019/7/3.
 */
abstract class SlideBaseFragment<T : SlideBasePresenter, V> : Fragment(), View.OnClickListener, ViewCallBack<V> {
    var presenter: T? = null
    protected var isVisible: Boolean? = false
    protected var mContext: Context? = null
    protected var isPrepared: Boolean = false
    protected lateinit var mFlexibleLayout: FlexibleLayout
    protected var tvTitle: TextView? = null
    private var mLinearLayout: LinearLayout? = null
    protected var tvRight: TextView? = null

    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = getActivity()
        val view = container?.let { initView(inflater, it) }
        initListener()
        mFlexibleLayout.loadData()
        isPrepared = true
        return view
    }

    fun showRightPage(code: Int) {
        when (code) {
            0 -> mFlexibleLayout.showPageWithState(FlexibleLayout.State.Empty)
            1 -> mFlexibleLayout.showPageWithState(FlexibleLayout.State.Normal)
            2 -> mFlexibleLayout.showPageWithState(FlexibleLayout.State.NetWorkError)
        }
    }

    protected fun setTitle(title: String) {
        if (null != tvTitle)
            tvTitle!!.text = title
    }

    protected fun setBackColor(color: Int) {
        if (null != mLinearLayout)
            mLinearLayout!!.setBackgroundColor(ContextCompat.getColor(mContext!!, color))
    }

    protected fun onRightClick() {

    }

    private fun initView(inflater: LayoutInflater, parent: ViewGroup): ViewGroup {
        mFlexibleLayout = object : FlexibleLayout(mContext) {

            override fun initNormalView(): ViewGroup {
                return initViewGroup(inflater, parent)

            }

            override fun onLoad() {
                if (presenter == null)
                    presenter = initPresenter()
                else
                    getData()
            }
        }
        return mFlexibleLayout
    }


    private fun initViewGroup(inflater: LayoutInflater, parent: ViewGroup): ViewGroup {
        val view = inflater.inflate(layoutId, parent, false) as ViewGroup
        tvTitle = view.findViewById<View>(R.id.tv_title) as? TextView
        tvRight = view.findViewById<View>(R.id.tv_right) as? TextView
        if (tvRight != null)
            tvRight!!.setOnClickListener { onRightClick() }
        mLinearLayout = view.findViewById<View>(R.id.top_layout) as? LinearLayout
        initCustomView(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter!!.add(this as ViewCallBack<*>)

    }


    override fun onDestroy() {
        super.onDestroy()
        presenter!!.remove()

    }

    protected abstract fun initCustomView(view: View) //初始化界面

    protected abstract fun initListener() //初始化监听事件

    protected abstract fun initPresenter(): T //初始化数据以及请求参数

    protected abstract fun getData()


    override fun onDestroyView() {
        super.onDestroyView()
        if (mContext != null)
            mContext = null
    }

}