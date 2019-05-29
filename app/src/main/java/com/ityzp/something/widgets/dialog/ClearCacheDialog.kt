package com.ityzp.something.widgets.dialog

import android.app.Dialog
import android.content.Context
import com.ityzp.something.R
import kotlinx.android.synthetic.main.dialog_clear_cache.*

/**
 * Created by wangqiang on 2019/5/29.
 */
class ClearCacheDialog(context: Context) : Dialog(context, R.style.MyDialogStyles) {
    init {
        setContentView(R.layout.dialog_clear_cache)
        setCanceledOnTouchOutside(true)
        tv_sure.setOnClickListener {
            if (::setonSureListener.isInitialized) {
                setonSureListener.invoke()
            }
        }
    }


    lateinit var setonSureListener: () -> Unit
}