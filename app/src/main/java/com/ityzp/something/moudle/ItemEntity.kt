package com.ityzp.something.moudle

import com.ityzp.something.contract.IAZItem
import com.ityzp.something.contract.IFuzzySearchItem

/**
 * Created by wangqiang on 2019/6/12.
 */
class ItemEntity(val value: String, override val sortLetters: String, override val fuzzyKey: List<String>) : IAZItem,
    IFuzzySearchItem {
    override val sourceKey: String
        get() = value

}

