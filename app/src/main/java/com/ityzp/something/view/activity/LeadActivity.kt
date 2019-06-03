package com.ityzp.something.view.activity

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.baseklibrary.base.BaseActivity
import com.example.baseklibrary.utils.StatusBarCompat
import com.ityzp.something.MainActivity
import com.ityzp.something.R
import kotlinx.android.synthetic.main.activity_lead.*

class LeadActivity : BaseActivity() {
    private var mImageViewList: ArrayList<ImageView>? = null
    private val mImageIds =
        intArrayOf(R.drawable.ic_lead1, R.drawable.ic_lead2, R.drawable.ic_lead3)
    private var currentItem: Int? = 0
    var startX: Float? = 0.0f
    var endX: Float? = 0.0f
    var fl: Float? = null

    override val layoutId: Int
        get() = R.layout.activity_lead

    override fun initViews(savedInstanceState: Bundle?) {
        StatusBarCompat.setTranslucentForImageView(this, 0, null)
        mImageViewList = ArrayList()
        for (i in mImageIds.indices) {
            val view = ImageView(this)
            view.setBackgroundResource(mImageIds[i])
            mImageViewList!!.add(view)
        }

        val adapter = GuideAdapter()
        vp_lead.adapter = adapter

        vp_lead.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentItem = position
                if (position == mImageIds.size - 1) {
                    tv_start.visibility = TextView.VISIBLE
                } else {
                    tv_start.visibility = TextView.GONE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        vp_lead.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                    }
                    MotionEvent.ACTION_UP -> {
                        endX = event.x
                        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                        val size = Point()
                        windowManager.defaultDisplay.getSize(size)
                        val width = size.x
                        fl = startX!! - endX!!
                        if (currentItem == (mImageViewList!!.size - 1) && fl!! >= (width / 4)) {
                            val intent = Intent()
                            intent.setClass(this@LeadActivity, MainActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
                            finish()
                        }
                    }
                }

                return false
            }

        })

        tv_start.setOnClickListener {
            intent.setClass(this@LeadActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_left)
            finish()
        }
    }

    override fun initToolBar() {
    }


    internal inner class GuideAdapter : PagerAdapter() {

        //item的个数
        override fun getCount(): Int {
            return mImageViewList!!.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        //初始化item布局
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = mImageViewList!!.get(position)
            view.scaleType = ImageView.ScaleType.CENTER_CROP
            container.addView(view)
            return view
        }

        //销毁item
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
