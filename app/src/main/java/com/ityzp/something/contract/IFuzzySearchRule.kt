package com.ityzp.something.contract

/**
 * Created by wangqiang on 2019/6/12.
 */
/**
 * 模糊搜索匹配规则接口
 */
interface IFuzzySearchRule {

    /**
     * 匹配规则
     *
     * @param constraint     匹配字符
     * @param itemSource     item 对应的原始字符
     * @param itemPinYinList item 原始字符对应的拼音列表
     * @return 是否匹配
     */
    fun accept(constraint: CharSequence, itemSource: String, itemPinYinList: List<String>): Boolean

}