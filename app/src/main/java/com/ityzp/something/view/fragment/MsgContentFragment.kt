package com.ityzp.something.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ityzp.something.R

/**
 * Created by wangqiang on 2019/7/12.
 */
class MsgContentFragment : Fragment() {
    private var name:String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = getArguments()
        name = bundle?.getString("name")
        if (name == null) {
            name = "参数非法"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_msg_content, container, false)
//        txt_content.text = "天气不错"
        return view
    }
}
