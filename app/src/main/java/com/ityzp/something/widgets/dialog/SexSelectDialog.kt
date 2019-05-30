package com.ityzp.something.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.ityzp.something.R
import kotlinx.android.synthetic.main.dialog_sex_select.*

/**
 * Created by wangqiang on 2019/5/30.
 */
class SexSelectDialog(context: Context) : Dialog(context, R.style.MyDialogStyles) {
    init {
        setContentView(R.layout.dialog_sex_select)
        setCanceledOnTouchOutside(true)
        val window = this.window
        window.setGravity(Gravity.BOTTOM)
        window.setWindowAnimations(R.style.dialogStyle)

        val lp = this.window.attributes as WindowManager.LayoutParams
        this.window.attributes = lp

        tv_man.setOnClickListener {
            if (::setOnsexListener.isInitialized) {
                setOnsexListener.invoke("男")
            }
            dismiss()
        }

        tv_women.setOnClickListener {
            if (::setOnsexListener.isInitialized) {
                setOnsexListener.invoke("女")
            }
            dismiss()
        }

        tv_cancel.setOnClickListener {
            dismiss()
        }
    }

    lateinit var setOnsexListener: (sex: String) -> Unit
}