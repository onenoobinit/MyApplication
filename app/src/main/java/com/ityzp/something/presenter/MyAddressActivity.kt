package com.ityzp.something.presenter

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewStub
import android.widget.TextView
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.R
import com.ityzp.something.SomeThingApp
import com.ityzp.something.adapter.MyAddressAdapter
import com.ityzp.something.contract.MyAddressContract
import com.ityzp.something.view.activity.AddAddressActivity
import kotlinx.android.synthetic.main.activity_head.*
import kotlinx.android.synthetic.main.activity_my_address.*

class MyAddressActivity : MvpActivity<MyAddressContract.myAddressView, MyAddressPresenter>(),
    MyAddressContract.myAddressView,
    View.OnClickListener {
    private var myaddresslist: ArrayList<String>? = ArrayList()
    override fun initPresenter(): MyAddressPresenter {
        return MyAddressPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_my_address

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
//        mPresenter.getMyAddress()
        rv_my_address.visibility = androidx.recyclerview.widget.RecyclerView.VISIBLE
        vs_none.visibility = ViewStub.GONE
        val linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        rv_my_address.layoutManager = linearLayoutManager
        myaddresslist!!.add("")
        myaddresslist!!.add("")
        myaddresslist!!.add("")
        val myAddressAdapter = MyAddressAdapter(myaddresslist!!, this)
        rv_my_address.adapter = myAddressAdapter
    }

    override fun initToolBar() {
        tb_title.setText("我的收货地址")
        toobar.setNavigationOnClickListener { finish() }
        tv_submit.visibility = TextView.VISIBLE
        tv_submit.setText("添加")
        tv_submit.setOnClickListener(this)
    }

    override fun getMyAddress() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_submit -> {
                val intent = Intent()
                intent.setClass(this, AddAddressActivity::class.java)
                startActivityForResult(intent, 1005)
            }

            R.id.tv_address_edit -> {
                val intent = Intent()
                intent.setClass(this, AddAddressActivity::class.java)
                intent.putExtra("type", 1)
                startActivityForResult(intent, 1006)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == SomeThingApp.RESULT_OK) {
            when (requestCode) {
                1005 -> {//回调请求接口刷新数据
//                    mPresenter.getMyAddress()
                }

                1006 -> {
//                    mPresenter.getMyAddress()
                }
            }
        }
    }


}
