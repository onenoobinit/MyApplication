package com.ityzp.something.widgets.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import com.ityzp.something.R
import kotlinx.android.synthetic.main.dilog_address_stub.*

/**
 * Created by wangqiang on 2019/5/30.
 */
class StubSelectDialog(context: Context) : Dialog(context, R.style.MyDialogStyles) {
    private var stubType: Int = -1

    init {
        setContentView(R.layout.dilog_address_stub)
        setCanceledOnTouchOutside(true)
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val window = this.window
        window.setGravity(Gravity.BOTTOM)
        window.setWindowAnimations(R.style.dialogStyle)

        iv_stub_close.setOnClickListener {
            dismiss()
        }

        rl_home.setOnClickListener {
            iv_stub_select.visibility = ImageView.VISIBLE
            iv_stub_select_company.visibility = ImageView.GONE
            iv_stub_select_school.visibility = ImageView.GONE
            stubType = 0
        }

        rl_company.setOnClickListener {
            iv_stub_select.visibility = ImageView.GONE
            iv_stub_select_company.visibility = ImageView.VISIBLE
            iv_stub_select_school.visibility = ImageView.GONE
            stubType = 1
        }

        rl_scholl.setOnClickListener {
            iv_stub_select.visibility = ImageView.GONE
            iv_stub_select_company.visibility = ImageView.GONE
            iv_stub_select_school.visibility = ImageView.VISIBLE
            stubType = 2
        }

        tv_stub_complete.setOnClickListener {
            if (::setOnStubListener.isInitialized) {
                setOnStubListener.invoke(stubType, et_stub.text.toString().trim())
            }
            dismiss()
        }
    }

    lateinit var setOnStubListener: (stubType: Int, content: String) -> Unit
}