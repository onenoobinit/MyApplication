package com.example.baseklibrary.utils

import android.graphics.Color

/**
 * Created by wangqiang on 2019/5/20.
 */
class ColorsUtil {
    companion object {
        /**
         * 白色
         */
        val WHITE = -0x1

        /**
         * 白色 - 半透明
         */
        val WHITE_TRANSLUCENT = -0x7f000001

        /**
         * 黑色
         */
        val BLACK = -0x1000000

        /**
         * 黑色 - 半透明
         */
        val BLACK_TRANSLUCENT = -0x80000000

        /**
         * 透明
         */
        val TRANSPARENT = 0x00000000

        /**
         * 红色
         */
        val RED = -0x10000

        /**
         * 红色 - 半透明
         */
        val RED_TRANSLUCENT = -0x7f010000

        /**
         * 红色 - 深的
         */
        val RED_DARK = -0x750000

        /**
         * 红色 - 深的 - 半透明
         */
        val RED_DARK_TRANSLUCENT = -0x7f750000

        /**
         * 绿色
         */
        val GREEN = -0xff0100

        /**
         * 绿色 - 半透明
         */
        val GREEN_TRANSLUCENT = -0x7fff0100

        /**
         * 绿色 - 深的
         */
        val GREEN_DARK = -0xffcd00

        /**
         * 绿色 - 深的 - 半透明
         */
        val GREEN_DARK_TRANSLUCENT = -0x7fffcd00

        /**
         * 绿色 - 浅的
         */
        val GREEN_LIGHT = -0x330034

        /**
         * 绿色 - 浅的 - 半透明
         */
        val GREEN_LIGHT_TRANSLUCENT = -0x7f330034

        /**
         * 蓝色
         */
        val BLUE = -0xffff01

        /**
         * 蓝色 - 半透明
         */
        val BLUE_TRANSLUCENT = -0x7fffff01

        /**
         * 蓝色 - 深的
         */
        val BLUE_DARK = -0xffff75

        /**
         * 蓝色 - 深的 - 半透明
         */
        val BLUE_DARK_TRANSLUCENT = -0x7fffff75

        /**
         * 蓝色 - 浅的
         */
        val BLUE_LIGHT = -0xc95a1d

        /**
         * 蓝色 - 浅的 - 半透明
         */
        val BLUE_LIGHT_TRANSLUCENT = -0x7fc95a1d

        /**
         * 天蓝
         */
        val SKYBLUE = -0x783115

        /**
         * 天蓝 - 半透明
         */
        val SKYBLUE_TRANSLUCENT = -0x7f783115

        /**
         * 天蓝 - 深的
         */
        val SKYBLUE_DARK = -0xff4001

        /**
         * 天蓝 - 深的 - 半透明
         */
        val SKYBLUE_DARK_TRANSLUCENT = -0x7fff4001

        /**
         * 天蓝 - 浅的
         */
        val SKYBLUE_LIGHT = -0x783106

        /**
         * 天蓝 - 浅的 - 半透明
         */
        val SKYBLUE_LIGHT_TRANSLUCENT = -0x7f783106

        /**
         * 灰色
         */
        val GRAY = -0x69696a

        /**
         * 灰色 - 半透明
         */
        val GRAY_TRANSLUCENT = -0x7f69696a

        /**
         * 灰色 - 深的
         */
        val GRAY_DARK = -0x565657

        /**
         * 灰色 - 深的 - 半透明
         */
        val GRAY_DARK_TRANSLUCENT = -0x7f565657

        /**
         * 灰色 - 暗的
         */
        val GRAY_DIM = -0x969697

        /**
         * 灰色 - 暗的 - 半透明
         */
        val GRAY_DIM_TRANSLUCENT = -0x7f969697

        /**
         * 灰色 - 浅的
         */
        val GRAY_LIGHT = -0x2c2c2d

        /**
         * 灰色 - 浅的 - 半透明
         */
        val GRAY_LIGHT_TRANSLUCENT = -0x7f2c2c2d

        /**
         * 橙色
         */
        val ORANGE = -0x5b00

        /**
         * 橙色 - 半透明
         */
        val ORANGE_TRANSLUCENT = -0x7f005b00

        /**
         * 橙色 - 深的
         */
        val ORANGE_DARK = -0x7800

        /**
         * 橙色 - 深的 - 半透明
         */
        val ORANGE_DARK_TRANSLUCENT = -0x7f007800

        /**
         * 橙色 - 浅的
         */
        val ORANGE_LIGHT = -0x44cd

        /**
         * 橙色 - 浅的 - 半透明
         */
        val ORANGE_LIGHT_TRANSLUCENT = -0x7f0044cd

        /**
         * 金色
         */
        val GOLD = -0x2900

        /**
         * 金色 - 半透明
         */
        val GOLD_TRANSLUCENT = -0x7f002900

        /**
         * 粉色
         */
        val PINK = -0x3f35

        /**
         * 粉色 - 半透明
         */
        val PINK_TRANSLUCENT = -0x7f003f35

        /**
         * 紫红色
         */
        val FUCHSIA = -0xff01

        /**
         * 紫红色 - 半透明
         */
        val FUCHSIA_TRANSLUCENT = -0x7f00ff01

        /**
         * 灰白色
         */
        val GRAYWHITE = -0xd0d0e

        /**
         * 灰白色 - 半透明
         */
        val GRAYWHITE_TRANSLUCENT = -0x7f0d0d0e

        /**
         * 紫色
         */
        val PURPLE = -0x7fff80

        /**
         * 紫色 - 半透明
         */
        val PURPLE_TRANSLUCENT = -0x7f7fff80

        /**
         * 青色
         */
        val CYAN = -0xff0001

        /**
         * 青色 - 半透明
         */
        val CYAN_TRANSLUCENT = -0x7fff0001

        /**
         * 青色 - 深的
         */
        val CYAN_DARK = -0xff7475

        /**
         * 青色 - 深的 - 半透明
         */
        val CYAN_DARK_TRANSLUCENT = -0x7fff7475

        /**
         * 黄色
         */
        val YELLOW = -0x100

        /**
         * 黄色 - 半透明
         */
        val YELLOW_TRANSLUCENT = -0x7f000100

        /**
         * 黄色 - 浅的
         */
        val YELLOW_LIGHT = -0x20

        /**
         * 黄色 - 浅的 - 半透明
         */
        val YELLOW_LIGHT_TRANSLUCENT = -0x7f000020

        /**
         * 巧克力色
         */
        val CHOCOLATE = -0x2d96e2

        /**
         * 巧克力色 - 半透明
         */
        val CHOCOLATE_TRANSLUCENT = -0x7f2d96e2

        /**
         * 番茄色
         */
        val TOMATO = -0x9cb9

        /**
         * 番茄色 - 半透明
         */
        val TOMATO_TRANSLUCENT = -0x7f009cb9

        /**
         * 橙红色
         */
        val ORANGERED = -0xbb00

        /**
         * 橙红色 - 半透明
         */
        val ORANGERED_TRANSLUCENT = -0x7f00bb00

        /**
         * 银白色
         */
        val SILVER = -0x3f3f40

        /**
         * 银白色 - 半透明
         */
        val SILVER_TRANSLUCENT = -0x7f3f3f40

        /**
         * 高光
         */
        val HIGHLIGHT = 0x33ffffff

        /**
         * 低光
         */
        val LOWLIGHT = 0x33000000

        /**
         * 颜色加深处理
         *
         * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
         * Android中我们一般使用它的16进制，
         * 例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
         * red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
         * 所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
         * @return
         */
        fun colorBurn(RGBValues: Int): Int {
            val alpha = RGBValues shr 24
            var red = RGBValues shr 16 and 0xFF
            var green = RGBValues shr 8 and 0xFF
            var blue = RGBValues and 0xFF
            red = Math.floor(red * (1 - 0.1)).toInt()
            green = Math.floor(green * (1 - 0.1)).toInt()
            blue = Math.floor(blue * (1 - 0.1)).toInt()
            return Color.rgb(red, green, blue)
        }
    }
}