package com.ityzp.something.moudle

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by wangqiang on 2019/7/3.
 */
class RightBean : Parcelable {
    var name: String? = null
    var titleName: String? = null
    var tag: String? = null
    var isTitle: Boolean = false
    var imgsrc: String? = null

    constructor(name: String) {
        this.name = name
    }

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        titleName = `in`.readString()
        tag = `in`.readString()
        isTitle = `in`.readByte().toInt() != 0
        imgsrc = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(titleName)
        dest.writeString(tag)
        dest.writeByte((if (isTitle) 1 else 0).toByte())
        dest.writeString(imgsrc)
    }

    companion object {

        @SuppressLint("ParcelCreator")
        val CREATOR: Parcelable.Creator<RightBean> = object : Parcelable.Creator<RightBean> {
            override fun createFromParcel(`in`: Parcel): RightBean {
                return RightBean(`in`)
            }

            override fun newArray(size: Int): Array<RightBean?> {
                return arrayOfNulls(size)
            }
        }
    }
}
