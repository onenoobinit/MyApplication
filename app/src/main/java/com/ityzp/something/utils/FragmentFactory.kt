package com.ityzp.something.utils

import androidx.fragment.app.Fragment
import com.ityzp.something.view.fragment.sortdetail.*

/**
 * Created by wangqiang on 2019/8/20.
 */
class FragmentFactory {
    companion object {
        fun createdById(resId: Int): Fragment? {
            var fragment: Fragment? = null
            when (resId) {
                FragmentID.FRIST_FRAGMENT -> {
                    fragment = MsgContentFragment()
                }
                FragmentID.SECOND_FRAGMENT -> {
                    fragment = RecommentFragment()
                }
                FragmentID.THREE_FRAGMENT -> {
                    fragment = HotspotFragment()
                }
                FragmentID.FOUR_FRAGMENT -> {
                    fragment = ViedeoFragment()
                }
                FragmentID.FIVE_FRAGMENT -> {
                    fragment = NovelFrament()
                }
                FragmentID.SIX_FRAGMENT -> {
                    fragment = EnjoyFragment()
                }
                FragmentID.SEVEN_FRAGMENT -> {
                    fragment = QuestionFragment()
                }
                FragmentID.ETGHT_FRAGMENT -> {
                    fragment = PictureFragment()
                }
                FragmentID.NINE_FRAGMENT -> {
                    fragment = ScienceFragment()
                }
                FragmentID.TEN_FRAGMENT -> {
                    fragment = CarFragment()
                }
                FragmentID.ELEVEN_FRAGMENT -> {
                    fragment = SportsFragment()
                }
                FragmentID.TWELEVE_FRAGMENT -> {
                    fragment = FinaceFragment()
                }
                FragmentID.THREETEN_FRAGMENT -> {
                    fragment = MilitilyFragment()
                }
                FragmentID.FOUTTEEN_FRAGMENT -> {
                    fragment = InternationFragment()
                }
                FragmentID.FIVETEEN_FRAGMENT -> {
                    fragment = HealthFragment()
                }
            }
            return fragment
        }
    }
}