package com.ityzp.something.view.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.example.baseklibrary.mvp.MvpActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.example.baseklibrary.utils.ToastUtil
import com.google.gson.Gson
import com.ityzp.something.R
import com.ityzp.something.contract.AddAddressContract
import com.ityzp.something.moudle.SanjiInfo
import com.ityzp.something.presenter.AddAddressPresenter
import com.ityzp.something.utils.GetJsonDataUtil
import com.ityzp.something.widgets.dialog.StubSelectDialog
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_head.*
import org.json.JSONArray
import java.util.*

class AddAddressActivity : MvpActivity<AddAddressContract.addAddressView, AddAddressPresenter>(),
    AddAddressContract.addAddressView, View.OnClickListener {
    private var stubSelectDialog: StubSelectDialog? = null
    private var options1Items: List<SanjiInfo> = ArrayList<SanjiInfo>()
    private val options2Items = ArrayList<ArrayList<String>>()
    private val options2Codes = ArrayList<ArrayList<String>>()
    private val options3Items = ArrayList<ArrayList<ArrayList<String>>>()
    private val options3Codes = ArrayList<ArrayList<ArrayList<String>>>()
    private var thread: Thread? = null
    private val MSG_LOAD_DATA: Int = 0x0001
    private val MSG_LOAD_SUCCESS: Int = 0x0002
    private val MSG_LOAD_FAILED: Int = 0x0003
    private var isLoaded = false
    private var tx: String? = null
    private var provinceId: String? = null
    private var cityId: String? = null
    private var counntryId: String? = null

    @SuppressLint("HandlerLeak")
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_LOAD_DATA -> if (thread == null) {//如果已创建就不再重新创建子线程了
//                    ToastUtil.show(this@AddAddressActivity, "Begin Parse Data")
                    thread = Thread(Runnable {
                        // 子线程中解析省市区数据
                        initJsonData()
                    })
                    thread!!.start()
                }


                MSG_LOAD_SUCCESS -> {
//                    ToastUtil.show(this@AddAddressActivity, "Parse Succeed")
                    isLoaded = true
                }

                MSG_LOAD_FAILED -> {
//                    ToastUtil.show(this@AddAddressActivity, "Parse Failed")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null)
        }

        if (stubSelectDialog != null) {
            stubSelectDialog!!.dismiss()
        }
    }

    override fun initPresenter(): AddAddressPresenter {
        return AddAddressPresenter()
    }

    override val layoutId: Int
        get() = R.layout.activity_add_address

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        ll_address.setOnClickListener(this)
        ll_address_stub.setOnClickListener(this)
        mHandler.sendEmptyMessage(MSG_LOAD_DATA)
    }

    override fun initToolBar() {
        tb_title.setText("添加收货地址")
        toobar.setNavigationOnClickListener { finish() }
        tv_submit.visibility = TextView.VISIBLE
        tv_submit.setText("保存")
        tv_submit.setOnClickListener(this)
    }

    override fun getAddAddress() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_submit -> {
                if (TextUtils.isEmpty(et_address_name.text.toString().trim())) {
                    ToastUtil.show(this, "请输入收货人姓名！")
                    return
                } else if (TextUtils.isEmpty(et_address_phone.text.toString().trim())) {
                    ToastUtil.show(this, "请输入联系方式！")
                    return
                } else if (TextUtils.isEmpty(tv_address_city.text.toString().trim())) {
                    ToastUtil.show(this, "请选择所在地区！")
                    return
                } else if (TextUtils.isEmpty(et_address_detail.text.toString().trim())) {
                    ToastUtil.show(this, "请输入所在地区详细地址！")
                    return
                } else if ("请选择".equals(tv_address_stub.text.toString().trim())) {
                    ToastUtil.show(this, "请选择标签！")
                    return
                } else {
//                    mPresenter.getAddAddress()
                }
            }

            R.id.ll_address -> {
                if (isLoaded) {
                    showPickerView()
                } else {
                    Toast.makeText(this@AddAddressActivity, "请点击重试", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.ll_address_stub -> {
                if (stubSelectDialog == null) {
                    stubSelectDialog = StubSelectDialog(this)
                }
                stubSelectDialog!!.setOnStubListener = { stubType, content ->
                    if (TextUtils.isEmpty(content)) {
                        when (stubType) {
                            0 -> tv_address_stub.setText("家")
                            1 -> tv_address_stub.setText("公司")
                            2 -> tv_address_stub.setText("学校")
                        }
                    } else {
                        tv_address_stub.setText(content)
                    }
                }
                stubSelectDialog!!.show()
            }
        }
    }

    private fun showPickerView() {

        val pvOptions = OptionsPickerBuilder(this,
            OnOptionsSelectListener { options1, options2, options3, v ->
                //返回的分别是三个级别的选中位置
                val opt1tx = if (options1Items.size > 0)
                    options1Items[options1].pickerViewText
                else
                    ""
                provinceId = options1Items[options1].value

                val opt2tx = if (options2Items.size > 0 && options2Items[options1].size > 0)
                    options2Items[options1][options2]
                else
                    ""
                cityId = options2Codes[options1][options2]

                val opt3tx = if (options2Items.size > 0
                    && options3Items[options1].size > 0
                    && options3Items[options1][options2].size > 0
                )
                    options3Items[options1][options2][options3]
                else
                    ""
                counntryId = options3Codes[options1][options2][options3]


                if (opt1tx == opt2tx) {
                    tx = "$opt1tx-$opt3tx"
                } else {
                    if ("--" == opt3tx) {
                        tx = "$opt1tx-$opt2tx"
                    } else {
                        tx = "$opt1tx-$opt2tx-$opt3tx"
                    }
                }
                tv_address_city.setText(tx)
            })

            .setTitleText("城市选择")
            .setDividerColor(Color.BLACK)
            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
            .setContentTextSize(20)
            .build<Any>()

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/

//        L.i("AAAA","options1Items="+options1Items.size+",options2Items ="+options2Items.size+",options3Items="+options3Items.size)
        pvOptions.setPicker(
            options1Items, options2Items as List<MutableList<Any>>?,
            options3Items as List<MutableList<MutableList<Any>>>?
        )//三级选择器
        pvOptions.show()
    }

    private fun initJsonData() {//解析数据
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
        val JsonData = GetJsonDataUtil().getJson(this, "area.json")//获取assets目录下的json文件数据
        val jsonBean = parseData(JsonData)//用Gson 转成实体
//        L.i("AAAA", "jsonBean=" + jsonBean.get(0).children!!.get(0).children!!.size)
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean

        for (i in jsonBean.indices) {//遍历省份
            val cityList = ArrayList<String>()//该省的城市列表（第二级）
            val cityListCode = ArrayList<String>()//该省的城市列表（第二级）
            val province_AreaList = ArrayList<ArrayList<String>>()//该省的所有地区列表（第三极）
            val province_AreaListCodes = ArrayList<ArrayList<String>>()//该省的所有地区列表（第三极）

            for (c in jsonBean.get(i).children!!.indices) {//遍历该省份的所有城市
                val cityName = jsonBean.get(i).children!!.get(c).name
                val cityCode = jsonBean.get(i).children!!.get(c).value
                cityList.add(cityName!!)//添加城市
                cityListCode.add(cityCode!!)//添加城市
                val city_AreaList = ArrayList<SanjiInfo.ChildrenBeanX.ChildrenBean>()//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).children!!.get(c).children!!)
                val newdatas = ArrayList<String>()
                val newdatasCodes = ArrayList<String>()
                for (x in city_AreaList.indices) {
                    newdatas.add(city_AreaList[x].name!!)
                    newdatasCodes.add(city_AreaList[x].value!!)
                }
                province_AreaList.add(newdatas)//添加该省所有地区数据
                province_AreaListCodes.add(newdatasCodes)//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList)
            options2Codes.add(cityListCode)

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList)
            options3Codes.add(province_AreaListCodes)
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS)

    }

    fun parseData(result: String): ArrayList<SanjiInfo> {//Gson 解析
        val detail = ArrayList<SanjiInfo>()
        try {
            val data = JSONArray(result)
            val gson = Gson()
            for (i in 0 until data.length()) {
                val entity = gson.fromJson<SanjiInfo>(data.optJSONObject(i).toString(), SanjiInfo::class.java)
                detail.add((entity as SanjiInfo?)!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED)
        }

        return detail
    }

}
