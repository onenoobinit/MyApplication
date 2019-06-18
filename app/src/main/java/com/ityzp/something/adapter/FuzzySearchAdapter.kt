package com.ityzp.something.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.ityzp.something.R
import com.ityzp.something.contract.IFuzzySearchRule
import com.ityzp.something.moudle.ItemEntity

/**
 * Created by wangqiang on 2019/6/12.
 */
class FuzzySearchAdapter : FuzzySearchBaseAdapter<ItemEntity, FuzzySearchAdapter.ItemHolder> {
    private var cityName = ""

    constructor() : super(null) {}

    constructor(rule: IFuzzySearchRule) : super(rule) {}

    constructor(dataList: List<ItemEntity>) : super(null, dataList) {}

    constructor(rule: IFuzzySearchRule, dataList: List<ItemEntity>) : super(rule, dataList) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return FuzzySearchAdapter.ItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_result,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        cityName = ""
        val value = mDataList!!.get(position).value
        val split = value.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        holder.tv_result_sx.setText(split[0])
        holder.tv_result_city.setText(split[2])

        if (split[1].contains(",")) {
            val split1 = split[1].split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            holder.tv_result_name.setText(split1[0])
            cityName = split1[0]
        } else if (split[1].contains("，")) {
            val split1 = split[1].split("，".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            holder.tv_result_name.setText(split1[0])
            cityName = split1[0]
        } else {
            holder.tv_result_name.setText(split[1])
            cityName = split[1]
        }
        holder.all_result_item.setOnClickListener {
            if (::setOnItemClickListener.isInitialized) {
                setOnItemClickListener.invoke(split[0] + "," + cityName)
            }
        }

    }

    class ItemHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        var tv_result_sx: TextView
        var tv_result_name: TextView
        var tv_result_city: TextView
        var all_result_item: LinearLayout

        init {
            tv_result_sx = itemView.findViewById(R.id.tv_result_sx)
            tv_result_name = itemView.findViewById(R.id.tv_result_name)
            tv_result_city = itemView.findViewById(R.id.tv_result_city)
            all_result_item = itemView.findViewById(R.id.all_result_item)
        }
    }

//    abstract fun setOnItemClickListener(position: String)

    lateinit var setOnItemClickListener: (position: String) -> Unit
}