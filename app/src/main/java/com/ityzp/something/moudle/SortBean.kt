package com.ityzp.something.moudle

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by wangqiang on 2019/7/3.
 */
class SortBean : Parcelable {
    var name: String? = null
    var tag: String? = null
    private var isTitle: Boolean = false
    var imgsrc: String? = null

    var titleName: String? = null

    var title: String? = null

    var categoryOneArray: ArrayList<CategoryOneArrayBean>? = null


    constructor(name: String) {
        this.name = name
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        tag = `in`.readString()
        isTitle = `in`.readByte().toInt() != 0
    }

    fun isTitle(): Boolean {
        return isTitle
    }

    fun setTitle(title: Boolean) {
        isTitle = title
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(tag)
        dest.writeByte((if (isTitle) 1 else 0).toByte())
        dest.writeString(imgsrc)
        dest.writeString(titleName)
        dest.writeString(title)
        dest.writeTypedList(categoryOneArray)
    }


    class CategoryOneArrayBean protected constructor(`in`: Parcel) : Parcelable {
        /**
         * categoryTwoArray : [{"name":"处方药(RX)","imgsrc":"https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_chufangyao.png","cacode":"chufangyao"},{"name":"非处方(OTC)","imgsrc":"https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_feichufang.png","cacode":"feichufang"},{"name":"抗生素","imgsrc":"https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_kangshengsu.png","cacode":"kangshengsu"}]
         * name : 通俗分类
         * imgsrc : https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_0.png
         * cacode : tongsufenlei
         */

        var name: String? = null
        var imgsrc: String? = null
        var cacode: String? = null
        var categoryTwoArray: List<CategoryTwoArrayBean>? = null

        init {
            name = `in`.readString()
            imgsrc = `in`.readString()
            cacode = `in`.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(name)
            dest.writeString(imgsrc)
            dest.writeString(cacode)
        }

        override fun describeContents(): Int {
            return 0
        }

        class CategoryTwoArrayBean protected constructor(`in`: Parcel) : Parcelable {
            /**
             * name : 处方药(RX)
             * imgsrc : https://121.10.217.171:9002/_ui/desktop/common/cmyy/image/app_tongsufenlei_chufangyao.png
             * cacode : chufangyao
             */

            var name: String? = null
            var imgsrc: String? = null
            var cacode: String? = null

            init {
                name = `in`.readString()
                imgsrc = `in`.readString()
                cacode = `in`.readString()
            }

            override fun describeContents(): Int {
                return 0
            }

            override fun writeToParcel(dest: Parcel, flags: Int) {
                dest.writeString(name)
                dest.writeString(imgsrc)
                dest.writeString(cacode)
            }

            companion object {

                @SuppressLint("ParcelCreator")
                val CREATOR: Parcelable.Creator<CategoryTwoArrayBean> =
                    object : Parcelable.Creator<CategoryTwoArrayBean> {
                        override fun createFromParcel(`in`: Parcel): CategoryTwoArrayBean {
                            return CategoryTwoArrayBean(`in`)
                        }

                        override fun newArray(size: Int): Array<CategoryTwoArrayBean?> {
                            return arrayOfNulls(size)
                        }
                    }
            }
        }

        companion object {

            @SuppressLint("ParcelCreator")
            val CREATOR: Parcelable.Creator<CategoryOneArrayBean> = object : Parcelable.Creator<CategoryOneArrayBean> {
                override fun createFromParcel(`in`: Parcel): CategoryOneArrayBean {
                    return CategoryOneArrayBean(`in`)
                }

                override fun newArray(size: Int): Array<CategoryOneArrayBean?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {


        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<SortBean> = object : Parcelable.Creator<SortBean> {
            override fun createFromParcel(`in`: Parcel): SortBean {
                return SortBean(`in`)
            }

            override fun newArray(size: Int): Array<SortBean?> {
                return arrayOfNulls(size)
            }
        }
    }


}
