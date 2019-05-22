package com.ityzp.something.moudle

import android.text.TextUtils
import java.io.Serializable

/**
 * Created by wangqiang on 2019/5/22.
 */
class User : Serializable {

    var token: String? = null
    var msg: String? = null
    var isLogined: Boolean? = null
        get() = if (field == null) {
            false
        } else field
    /**
     * id : 2165
     * phone : null
     * portrait : https://uhealth-app-img.oss-cn-beijing.aliyuncs.com/7d458580-ef5c-47cd-86f0-13ffc3b2f4c2.jpeg?height=1200&width=1200
     * nickName : 小黑
     * gender : {"code":1010,"name":"MALE","text":"男"}
     * birthday : 1990-11-23
     * companyName : null
     * projectNameDisplay : null
     * welcomeMessage : null
     */

    var id: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var phone: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var portrait: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var nickName: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var gender: GenderBean? = null
    private val customerType: CustomerTypeBean? = null
    var birthday: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var companyName: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var projectNameDisplay: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var welcomeMessage: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }

    var projectId: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }
    var unionid: String? = null
        get() {
            return if (TextUtils.isEmpty(field)) {
                ""
            } else field
        }

    class GenderBean : Serializable {
        /**
         * code : 1010
         * name : MALE
         * text : 男
         */

        var code: String? = null
            get() {
                return if (TextUtils.isEmpty(field)) {
                    ""
                } else field
            }
        var name: String? = null
            get() {
                return if (TextUtils.isEmpty(field)) {
                    ""
                } else field
            }
        var text: String? = null
            get() {
                return if (TextUtils.isEmpty(field)) {
                    ""
                } else field
            }
    }

    class CustomerTypeBean : Serializable {
        /**
         * code : 1010
         * name : MALE
         * text : 男
         */

        var code: String? = null
            get() {
                return if (TextUtils.isEmpty(field)) {
                    ""
                } else field
            }
        var name: String? = null
            get() {
                return if (TextUtils.isEmpty(field)) {
                    ""
                } else field
            }
        var text: String? = null
            get() {
                return if (TextUtils.isEmpty(field)) {
                    ""
                } else field
            }
    }
}
