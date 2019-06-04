package com.ityzp.something.view.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.example.baseklibrary.mvp.MvpFragment
import com.example.baseklibrary.utils.DensityUtil
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import com.ityzp.something.adapter.IndexBannerAdapter
import com.ityzp.something.adapter.IndexRvTitleAdapter
import com.ityzp.something.contract.IndexContract
import com.ityzp.something.presenter.IndexPresenter
import com.ityzp.something.utils.WXObserver
import com.ityzp.something.utils.WxShareUtils
import com.ityzp.something.widgets.ViewPagerIndicator
import com.ityzp.something.widgets.dialog.WxShareDialog
import com.ityzp.something.widgets.popouwindow.IndexPopupWindow
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tmall.ultraviewpager.UltraViewPager
import kotlinx.android.synthetic.main.layout_index_center.*
import kotlinx.android.synthetic.main.layout_index_icon.*
import kotlinx.android.synthetic.main.layout_index_levitate.*
import kotlinx.android.synthetic.main.layout_inex_top.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*

/**
 * Created by wangqiang on 2019/5/23.
 */
class IndexFragment : MvpFragment<IndexContract.indexView, IndexPresenter>(), IndexContract.indexView,
    View.OnClickListener, Observer {
    private var indexBanners: ArrayList<Int> = ArrayList()
    private var indexTitles: ArrayList<String> = ArrayList()
    private var indexPopupWindow: IndexPopupWindow? = null
    private var wxShareDialog: WxShareDialog? = null
    private var wxUrl: String = "https://www.baidu.com/"
    private var wxTitle: String = "死亡如黎明中的花朵！"
    private var wxIntrodution: String = "死亡如风，常伴吾身，欢迎来到英雄联盟"

    override fun initPresenter(): IndexPresenter {
        return IndexPresenter()
    }

    override val layoutResId: Int
        get() = R.layout.fragment_index

    override fun finishCreateView(state: Bundle?) {
        WXObserver.INSTANCE.addObserver(this)
        initBanner()
        initRv()
        initPopup()
        rl_index_top_more.setOnClickListener(this)
        iv_index_pic.setOnClickListener(this)
        ll_index_address.setOnClickListener(this)
        ll_index_center_one.setOnClickListener(this)
        ll_index_center_two.setOnClickListener(this)
        ll_index_center_three.setOnClickListener(this)
        ll_index_center_four.setOnClickListener(this)
        ll_index_center_five.setOnClickListener(this)
        ll_index_center_six.setOnClickListener(this)
        ll_index_center_seven.setOnClickListener(this)
        ll_index_center_eight.setOnClickListener(this)
        ll_index_center_nine.setOnClickListener(this)
        ll_index_center_ten.setOnClickListener(this)

//        mPresenter.getIndex()
    }

    private fun initPopup() {
        indexPopupWindow = IndexPopupWindow(mContext)
        indexPopupWindow!!.setOnDismissListener(PopupWindow.OnDismissListener { setBackgroundAlpha(1f) })

        indexPopupWindow!!.setOnSaoListener = {//扫一扫
            ToastUtil.show(mContext, "我是扫一扫")
        }

        indexPopupWindow!!.setOnMessageListener = {//消息
            ToastUtil.show(mContext, "消息")
        }

        indexPopupWindow!!.setOnShareListener = {//分享到微信或朋友圈
            indexPopupWindow!!.dismiss()
            if (wxShareDialog == null) {
                wxShareDialog = WxShareDialog(mContext)
            }
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_two, null)
            wxShareDialog!!.setOnWxListener = {
                WxShareUtils.shareWeb(mContext, wxUrl, wxTitle, wxIntrodution, bitmap, false)
            }

            wxShareDialog!!.setOnFriendListener = {
                WxShareUtils.shareWeb(mContext, wxUrl, wxTitle, wxIntrodution, bitmap, true)
            }

            wxShareDialog!!.show()
        }

        indexPopupWindow!!.setOnCollectionListener = {//收藏
            ToastUtil.show(mContext, "收藏")
        }
    }

    private fun initRv() {
        indexTitles.add("推荐")
        indexTitles.add("销量")
        indexTitles.add("价格")
        indexTitles.add("其他")
        rv_index_title.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        val indexRvTitleAdapter = IndexRvTitleAdapter(mContext, indexTitles)
        rv_index_title.adapter = indexRvTitleAdapter
        OverScrollDecoratorHelper.setUpOverScroll(rv_index_title, OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL)
    }

    private fun initBanner() {
        indexBanners.clear()
        indexBanners.add(R.mipmap.ic_index_x1)
        indexBanners.add(R.mipmap.ic_index_x2)
        indexBanners.add(R.mipmap.ic_index_x3)
        val indexBannerAdapter = IndexBannerAdapter(mContext, indexBanners)
        uvp_index.adapter = indexBannerAdapter
        uvp_index.setOffscreenPageLimit(1)
        uvp_index.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL)
        uvp_index.setInfiniteLoop(true)
        uvp_index.setAutoScroll(4000)
        uvp_index.viewPager.pageMargin = DensityUtil.px2dp(mContext, 60F).toInt()
        val viewPagerIndicator = ViewPagerIndicator(mContext, uvp_index.viewPager, ll_indicator, indexBanners.size)
        uvp_index.viewPager.addOnPageChangeListener(viewPagerIndicator)
    }

    override fun getIndex() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rl_index_top_more -> {//加号
                indexPopupWindow!!.showPopupWindow(rl_index_top_more, indexTitles)
                setBackgroundAlpha(0.55f)
            }

            R.id.iv_index_pic -> {//头像
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_address -> {//地区
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_search -> {//搜索
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_one -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_two -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_three -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_four -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_five -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_six -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_seven -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_eight -> {
                ToastUtil.show(mContext, "暂未开发")
            }

            R.id.ll_index_center_ten -> {
                ToastUtil.show(mContext, "暂未开发")
            }


        }
    }

    private fun setBackgroundAlpha(bgAlpha: Float) {
        val lp = getActivity()!!.window.attributes
        lp.alpha = bgAlpha
        lp.dimAmount = bgAlpha
        if (bgAlpha == 1f) {
            getActivity()!!.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        } else {
            getActivity()!!.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        getActivity()!!.window.attributes = lp
    }

    //微信分享回调 取消和成功返回同样数值
    override fun update(o: Observable?, arg: Any?) {
        if (o is WXObserver) {
            val res = arg as BaseResp
            when (res.errCode) {
                0 -> {
                }
                -2 -> {
                }
                -4 -> {
                }
            }
            wxShareDialog!!.dismiss()
        }
    }
}