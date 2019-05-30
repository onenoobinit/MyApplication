package com.ityzp.something.moudle

import com.contrarywind.interfaces.IPickerViewData

/**
 * Created by wangqiang on 2019/5/30.
 */
class SanjiInfo : IPickerViewData {
    /**
     * children : [{"children":[{"name":"东城区","parent":"110100","value":"110101"},{"name":"西城区","parent":"110100","value":"110102"},{"name":"朝阳区","parent":"110100","value":"110105"},{"name":"丰台区","parent":"110100","value":"110106"},{"name":"石景山区","parent":"110100","value":"110107"},{"name":"海淀区","parent":"110100","value":"110108"},{"name":"门头沟区","parent":"110100","value":"110109"},{"name":"房山区","parent":"110100","value":"110111"},{"name":"通州区","parent":"110100","value":"110112"},{"name":"顺义区","parent":"110100","value":"110113"},{"name":"昌平区","parent":"110100","value":"110114"},{"name":"大兴区","parent":"110100","value":"110115"},{"name":"怀柔区","parent":"110100","value":"110116"},{"name":"平谷区","parent":"110100","value":"110117"},{"name":"密云区","parent":"110100","value":"110118"},{"name":"延庆区","parent":"110100","value":"110119"}],"name":"市辖区","parent":"110000","value":"110100"}]
     * name : 北京市
     * value : 110000
     */

    var name: String? = null
    var value: String? = null
    var children: List<ChildrenBeanX>? = null

    override fun getPickerViewText(): String? {
        return this.name
    }

    class ChildrenBeanX {
        /**
         * children : [{"name":"东城区","parent":"110100","value":"110101"},{"name":"西城区","parent":"110100","value":"110102"},{"name":"朝阳区","parent":"110100","value":"110105"},{"name":"丰台区","parent":"110100","value":"110106"},{"name":"石景山区","parent":"110100","value":"110107"},{"name":"海淀区","parent":"110100","value":"110108"},{"name":"门头沟区","parent":"110100","value":"110109"},{"name":"房山区","parent":"110100","value":"110111"},{"name":"通州区","parent":"110100","value":"110112"},{"name":"顺义区","parent":"110100","value":"110113"},{"name":"昌平区","parent":"110100","value":"110114"},{"name":"大兴区","parent":"110100","value":"110115"},{"name":"怀柔区","parent":"110100","value":"110116"},{"name":"平谷区","parent":"110100","value":"110117"},{"name":"密云区","parent":"110100","value":"110118"},{"name":"延庆区","parent":"110100","value":"110119"}]
         * name : 市辖区
         * parent : 110000
         * value : 110100
         */

        var name: String? = null
        var parent: String? = null
        var value: String? = null
        var children: List<ChildrenBean>? = null

        class ChildrenBean {
            /**
             * name : 东城区
             * parent : 110100
             * value : 110101
             */

            var name: String? = null
            var parent: String? = null
            var value: String? = null
        }
    }
}
