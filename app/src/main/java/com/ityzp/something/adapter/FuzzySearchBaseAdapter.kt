package com.ityzp.something.adapter

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.Filter
import android.widget.Filterable
import com.ityzp.something.contract.IFuzzySearchItem
import com.ityzp.something.contract.IFuzzySearchRule
import com.ityzp.something.utils.DefaultFuzzySearchRule
import java.util.*

/**
 * Created by wangqiang on 2019/6/12.
 */
abstract class FuzzySearchBaseAdapter<ITEM : IFuzzySearchItem, VH : RecyclerView.ViewHolder> @JvmOverloads constructor(
    rule: IFuzzySearchRule?,
    private var mBackDataList: List<ITEM>? = null
) :
    RecyclerView.Adapter<VH>(), Filterable {

    private var mFilter: FuzzySearchFilter? = null
    protected var mDataList: List<ITEM>? = null
    private var mIFuzzySearchRule: IFuzzySearchRule? = null

    init {
        if (rule == null) {
            mIFuzzySearchRule = DefaultFuzzySearchRule()
        }
        mDataList = this.mBackDataList
    }

    fun setDataList(dataList: MutableList<ITEM>) {
        mBackDataList = dataList
        mDataList = dataList
    }


    override fun getItemCount(): Int {
        return if (mDataList == null) 0 else mDataList!!.size
    }

    override fun getFilter(): Filter {
        if (mFilter == null) {
            mFilter = FuzzySearchFilter()
        }
        return mFilter as FuzzySearchFilter
    }

    private inner class FuzzySearchFilter : Filter() {

        /**
         * 执行过滤操作,如果搜索的关键字为空，默认所有结果
         */
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            val result = Filter.FilterResults()
            val filterList: MutableList<ITEM>?
            if (TextUtils.isEmpty(constraint)) {
                filterList = mBackDataList as MutableList<ITEM>?
            } else {
                filterList = ArrayList()
                for (item in mBackDataList!!) {
                    if (mIFuzzySearchRule!!.accept(constraint, item.sourceKey, item.fuzzyKey)) {
                        filterList.add(item)
                    }
                }
            }
            result.values = filterList
            result.count = filterList!!.size
            return result
        }

        /**
         * 得到过滤结果
         */
        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            mDataList = results.values as List<ITEM>
            notifyDataSetChanged()
        }
    }
}
