package com.ityzp.something.widgets.dialog

import android.app.Dialog
import android.content.Context
import com.example.baseklibrary.utils.ToastUtil
import com.ityzp.something.R
import kotlinx.android.synthetic.main.dialog_update.*

/**
 * Created by wangqiang on 2019/5/29.
 */
class UpDateDiaolog(context: Context) :Dialog(context, R.style.MyDialogStyles){
    init {
        setContentView(R.layout.dialog_update)
        setCanceledOnTouchOutside(false)
        tv_cancel.setOnClickListener {
            dismiss()
        }

        tv_update.setOnClickListener {
            ToastUtil.show(context,"功能开发中！")
            dismiss()
        }
    }
}