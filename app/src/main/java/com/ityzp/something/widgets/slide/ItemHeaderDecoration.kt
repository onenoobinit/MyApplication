package com.ityzp.something.widgets.slide

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ityzp.something.R
import com.ityzp.something.moudle.RightBean

/**
 * Created by wangqiang on 2019/7/3.
 */
class ItemHeaderDecoration internal constructor(context: Context, private var mDatas: List<RightBean>?) :
    RecyclerView.ItemDecoration() {
    private val mTitleHeight: Int
    private val mInflater: LayoutInflater
    private var mCheckListener: CheckListener? = null

    internal fun setCheckListener(checkListener: CheckListener) {
        mCheckListener = checkListener
    }

    init {
        val paint = Paint()
        mTitleHeight =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, context.resources.displayMetrics).toInt()
        val titleFontSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, context.resources.displayMetrics).toInt()
        paint.textSize = titleFontSize.toFloat()
        paint.isAntiAlias = true
        mInflater = LayoutInflater.from(context)
    }


    fun setData(mDatas: List<RightBean>): ItemHeaderDecoration {
        this.mDatas = mDatas
        return this
    }


    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val manager = parent.getLayoutManager() as GridLayoutManager
        val spanSizeLookup = manager.getSpanSizeLookup()
        val pos = (parent.getLayoutManager() as LinearLayoutManager).findFirstVisibleItemPosition()
        val spanSize = spanSizeLookup.getSpanSize(pos)
        Log.d("pos--->", pos.toString())
        var tag = mDatas!![pos].tag
        val child = parent.findViewHolderForLayoutPosition(pos)?.itemView
        var isTranslate = false//canvas是否平移的标志
        if (!TextUtils.equals(mDatas!![pos].tag, mDatas!![pos + 1].tag)
            || !TextUtils.equals(mDatas!![pos].tag, mDatas!![pos + 2].tag)
            || !TextUtils.equals(mDatas!![pos].tag, mDatas!![pos + 3].tag)
        ) {
            tag = mDatas!![pos].tag
            val i = child!!.getHeight() + child.getTop()
            Log.d("i---->", i.toString())
            if (spanSize == 1) {
                //body 才平移
                if (child.getHeight() + child.getTop() < mTitleHeight) {
                    canvas.save()
                    isTranslate = true
                    val height = child.getHeight() + child.getTop() - mTitleHeight
                    canvas.translate(0f, height.toFloat())
                }
            }


        }
        drawHeader(parent, pos, canvas)
        if (isTranslate) {
            canvas.restore()
        }
        Log.d("tag--->", tag + "VS" + currentTag)
        if (!TextUtils.equals(tag, currentTag)) {
            currentTag = tag!!
            val integer = Integer.valueOf(tag)
            mCheckListener!!.check(integer, false)
        }
    }

    /**
     * @param parent
     * @param pos
     */
    private fun drawHeader(parent: RecyclerView, pos: Int, canvas: Canvas) {
        val topTitleView = mInflater.inflate(R.layout.item_title, parent, false)
        val tvTitle = topTitleView.findViewById(R.id.tv_title) as TextView
        tvTitle.setText(mDatas!![pos].titleName)
        //绘制title开始
        val toDrawWidthSpec: Int//用于测量的widthMeasureSpec
        val toDrawHeightSpec: Int//用于测量的heightMeasureSpec
        var lp: RecyclerView.LayoutParams? = topTitleView.getLayoutParams() as RecyclerView.LayoutParams
        if (lp == null) {
            lp = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )//这里是根据复杂布局layout的width height，new一个Lp
            topTitleView.setLayoutParams(lp)
        }
        topTitleView.setLayoutParams(lp)
        if (lp!!.width === ViewGroup.LayoutParams.MATCH_PARENT) {
            //如果是MATCH_PARENT，则用父控件能分配的最大宽度和EXACTLY构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(
                parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(),
                View.MeasureSpec.EXACTLY
            )
        } else if (lp!!.width === ViewGroup.LayoutParams.WRAP_CONTENT) {
            //如果是WRAP_CONTENT，则用父控件能分配的最大宽度和AT_MOST构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(
                parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(),
                View.MeasureSpec.AT_MOST
            )
        } else {
            //否则则是具体的宽度数值，则用这个宽度和EXACTLY构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(lp!!.width, View.MeasureSpec.EXACTLY)
        }
        //高度同理
        if (lp!!.height === ViewGroup.LayoutParams.MATCH_PARENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(
                parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(),
                View.MeasureSpec.EXACTLY
            )
        } else if (lp!!.height === ViewGroup.LayoutParams.WRAP_CONTENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(
                parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(),
                View.MeasureSpec.AT_MOST
            )
        } else {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(mTitleHeight, View.MeasureSpec.EXACTLY)
        }
        //依次调用 measure,layout,draw方法，将复杂头部显示在屏幕上
        topTitleView.measure(toDrawWidthSpec, toDrawHeightSpec)
        topTitleView.layout(
            parent.getPaddingLeft(),
            parent.getPaddingTop(),
            parent.getPaddingLeft() + topTitleView.getMeasuredWidth(),
            parent.getPaddingTop() + topTitleView.getMeasuredHeight()
        )
        topTitleView.draw(canvas)//Canvas默认在视图顶部，无需平移，直接绘制
        //绘制title结束

    }

    companion object {
         var currentTag = "0"//标记当前左侧选中的position，因为有可能选中的item，右侧不能置顶，所以强制替换掉当前的tag
    }
}
