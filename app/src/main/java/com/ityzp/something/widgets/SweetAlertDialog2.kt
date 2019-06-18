package com.ityzp.something.widgets

import android.app.Dialog
import android.content.Context
import androidx.annotation.StringRes
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/5/27.
 */
class SweetAlertDialog2(builder: Builder) {
    private val context: Context
    private val titleIds: Int
    private val title: String?
    private val message: String?
    private val hasCancle: Boolean
    private var mContentTitle: TextView? = null
    private var mContentMessage: TextView? = null
    private var mDialog: Dialog? = null
    private val mNegativeButtonText: CharSequence?
    private val mPositiveButtonText: CharSequence?
    private val mNegativeButtonListener: OnDialogClickListener?
    private val mPositiveButtonListener: OnDialogClickListener?
    private var mLeftTxt: TextView? = null
    private var mRightTxt: TextView? = null
    private var mCenterLine: View? = null
    internal var mCancleableoutSide: Boolean = false
    private val dimiss: OnDialogClickListener?

    init {
        this.context = builder.mContext
        this.titleIds = builder.mTitleResId
        this.title = builder.mTitle
        this.message = builder.mMessage
        this.hasCancle = builder.mHasCancleable
        this.mCancleableoutSide = builder.mCancleableoutSide
        this.mNegativeButtonText = builder.mNegativeButtonText
        this.mPositiveButtonText = builder.mPositiveButtonText
        this.mNegativeButtonListener = builder.mNegativeButtonListener
        this.mPositiveButtonListener = builder.mPositiveButtonListener
        this.dimiss = builder.dissmiss
        this.initView()
    }

    /**
     * 初始化布局文件
     */
    private fun initView() {
        val rootView = LayoutInflater.from(context).inflate(R.layout.sweet_alert_dialog_view, null)
        mContentTitle = rootView.findViewById(R.id.tv_dialog_title)
        mContentMessage = rootView.findViewById(R.id.tv_dialog_message)
        mLeftTxt = rootView.findViewById(R.id.dialog_left_txt)
        mRightTxt = rootView.findViewById(R.id.dialog_right_txt)
        mCenterLine = rootView.findViewById(R.id.dialog_line)
        // 定义Dialog布局和参数
        mDialog = Dialog(context, R.style.Sweet_Alert_Dialog)
        mDialog!!.setContentView(rootView)
        mDialog!!.setCanceledOnTouchOutside(mCancleableoutSide)
        updateDialogUI()

        mDialog!!.show()
    }

    private fun updateDialogUI() {
        // title resId
        if (titleIds != 0) {
            mContentTitle!!.visibility = View.VISIBLE
            mContentTitle!!.setText(titleIds)
        }
        // title
        if (hasNull(title)) {
            mContentTitle!!.visibility = View.VISIBLE
            mContentTitle!!.text = title
        }

        // message
        if (hasNull(message)) {
            mContentMessage!!.text = message
        }

        // 默认显示取消按钮 自定义字体
        if (hasNull(mNegativeButtonText) || hasCancle) {
            mLeftTxt!!.visibility = View.VISIBLE
            mLeftTxt!!.text = if (hasCancle) "取消" else mNegativeButtonText
            mLeftTxt!!.setOnClickListener {
                if (mDialog != null)
                    mDialog!!.dismiss()
                if (!hasCancle)
                    mNegativeButtonListener!!.onClick(mDialog, 0)
            }
        }

        //左侧文字为空,
        if (!hasNull(mNegativeButtonText) && !hasCancle && hasNull(mPositiveButtonText)) {
            mLeftTxt!!.visibility = View.GONE
            mCenterLine!!.visibility = View.GONE
        }

        if (hasNull(mPositiveButtonText)) {
            mRightTxt!!.visibility = View.VISIBLE
            mRightTxt!!.text = mPositiveButtonText
            mRightTxt!!.setOnClickListener {
                if (mDialog != null)
                    mDialog!!.dismiss()
                mPositiveButtonListener!!.onClick(mDialog, 1)
            }
        }
    }


    fun hasNull(msg: CharSequence?): Boolean {
        return !TextUtils.isEmpty(msg)
    }

    class Builder(val mContext: Context) {
        var mTitleResId: Int = 0
        var mTitle: String? = null
        var mMessage: String? = null
        var mHasCancleable = true
        var mCancleableoutSide = true
        var mNegativeButtonText: CharSequence? = null
        var mPositiveButtonText: CharSequence? = null
        var mNegativeButtonListener: OnDialogClickListener? = null
        var mPositiveButtonListener: OnDialogClickListener? = null
        private var sweetAlertDialog: SweetAlertDialog2? = null
        var dissmiss: OnDialogClickListener? = null

        val isShow: Boolean?
            get() = if (sweetAlertDialog != null) {
                sweetAlertDialog!!.mDialog!!.isShowing
            } else false

        fun setTitle(@StringRes titleId: Int): Builder {
            this.mTitleResId = titleId
            return this
        }

        fun setTitle(title: String): Builder {
            this.mTitle = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.mMessage = message
            return this
        }

        fun setCancelable(hasCancleable: Boolean): Builder {
            this.mHasCancleable = hasCancleable
            return this
        }

        fun setCancelableoutSide(cancleableoutSide: Boolean): Builder {
            mCancleableoutSide = cancleableoutSide
            return this
        }

        fun setNegativeButton(text: CharSequence, listener: OnDialogClickListener): Builder {
            this.mNegativeButtonText = text
            mNegativeButtonListener = listener
            return this
        }

        fun onDIsmissListener(listener: OnDialogClickListener): Builder {
            dissmiss = listener
            return this
        }

        fun setPositiveButton(text: CharSequence, listener: OnDialogClickListener): Builder {
            this.mPositiveButtonText = text
            this.mPositiveButtonListener = listener
            return this
        }

        fun show(): SweetAlertDialog2 {
            sweetAlertDialog = SweetAlertDialog2(this)
            return sweetAlertDialog as SweetAlertDialog2
        }

        fun dismiss() {
            if (sweetAlertDialog != null && sweetAlertDialog!!.mDialog!!.isShowing) {
                sweetAlertDialog!!.mDialog!!.dismiss()
            }
        }


    }

    interface OnDialogClickListener {
        fun onClick(dialog: Dialog?, which: Int)
    }
}
