package com.ityzp.something

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.view.fragment.CenterFragment
import com.ityzp.something.view.fragment.IndexFragment
import com.ityzp.something.view.fragment.MeFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    private var fragments: Array<Fragment>? = null
    private var checkFragmentPosition: Int? = 0
    private var trx: FragmentTransaction? = null
    private var currentTabIndex: Int? = 0
    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        ll_index.setOnClickListener(this)
        ll_center.setOnClickListener(this)
        ll_me.setOnClickListener(this)
        initFragments()
        checkPermisss()
    }

    private fun checkPermisss() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
            .request(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .subscribe { granted ->
                if (granted) {
                    // All requested permissions are granted
                } else {
                    // At least one permission is denied
                }
            }
    }

    private fun initFragments() {
        val indexFragment = IndexFragment()
        val centerFragment = CenterFragment()
        val meFragment = MeFragment()
        fragments = arrayOf<Fragment>(indexFragment, centerFragment, meFragment)
        checkFragmentPosition = intent.getIntExtra("checkFragmentPosition", 0)
        if (checkFragmentPosition!! > 0) {
            setShowingFragment(checkFragmentPosition!!, true)
            intent.removeExtra("checkFragmentPosition")
            return
        }
        trx = supportFragmentManager.beginTransaction()
        trx!!.add(R.id.fl_content, indexFragment)
            .add(R.id.fl_content, centerFragment)
            .add(R.id.fl_content, meFragment)
            .hide(centerFragment)
            .hide(meFragment)
            .show(indexFragment).commitAllowingStateLoss()
    }

    //fragment切换
    private fun setShowingFragment(position: Int, b: Boolean) {
        if (currentTabIndex != position) {
            trx = supportFragmentManager.beginTransaction()
            if (b) {
                if (position > currentTabIndex!!) {
                    trx!!.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                } else {
                    trx!!.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out)
                }
            }

            trx!!.hide(fragments!![currentTabIndex!!])

            if (!fragments!![position].isAdded) {
                trx!!.add(R.id.fl_content, fragments!![position])
            }

            when (position) {
                0 -> {
                    iv_index.setImageResource(R.mipmap.ic_index_click)
                    iv_center.setImageResource(R.mipmap.ic_center)
                    iv_me.setImageResource(R.mipmap.ic_me)
                    tv_index.setTextColor(Color.parseColor("#4a4a4a"))
                    tv_center.setTextColor(Color.parseColor("#999999"))
                    tv_me.setTextColor(Color.parseColor("#999999"))
                }

                1 -> {
                    iv_index.setImageResource(R.mipmap.ic_index)
                    iv_center.setImageResource(R.mipmap.ic_center_click)
                    iv_me.setImageResource(R.mipmap.ic_me)
                    tv_index.setTextColor(Color.parseColor("#999999"))
                    tv_center.setTextColor(Color.parseColor("#4a4a4a"))
                    tv_me.setTextColor(Color.parseColor("#999999"))
                }
                2 -> {
                    iv_index.setImageResource(R.mipmap.ic_index)
                    iv_center.setImageResource(R.mipmap.ic_center)
                    iv_me.setImageResource(R.mipmap.ic_me_click)
                    tv_index.setTextColor(Color.parseColor("#999999"))
                    tv_center.setTextColor(Color.parseColor("#999999"))
                    tv_me.setTextColor(Color.parseColor("#4a4a4a"))
                }
            }
            trx!!.show(fragments!![position]).commitAllowingStateLoss()
            currentTabIndex = position
        }

    }

    override fun initToolBar() {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_index -> {
                setShowingFragment(0, true)
            }
            R.id.ll_center -> {
                setShowingFragment(1, true)
            }
            R.id.ll_me -> {
                setShowingFragment(2, true)
            }
        }
    }
}
