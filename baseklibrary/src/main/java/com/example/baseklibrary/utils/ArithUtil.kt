package com.example.baseklibrary.utils

import java.math.BigDecimal

/**
 * Created by wangqiang on 2019/5/20.
 */
class ArithUtil {
    companion object {
        private val DEF_DIV_SCALE = 10

        fun add(d1: Double, d2: Double): Double {
            val b1 = BigDecimal(java.lang.Double.toString(d1))
            val b2 = BigDecimal(java.lang.Double.toString(d2))
            return b1.add(b2).toDouble()

        }

        fun sub(d1: Double, d2: Double): Double {
            val b1 = BigDecimal(java.lang.Double.toString(d1))
            val b2 = BigDecimal(java.lang.Double.toString(d2))
            return b1.subtract(b2).toDouble()

        }

        fun mul(d1: Double, d2: Double): Double {
            val b1 = BigDecimal(java.lang.Double.toString(d1))
            val b2 = BigDecimal(java.lang.Double.toString(d2))
            return b1.multiply(b2).toDouble()

        }

        fun div(d1: Double, d2: Double): Double {

            return div(d1, d2, DEF_DIV_SCALE)

        }

        fun div(d1: Double, d2: Double, scale: Int): Double {
            if (scale < 0) {
                throw IllegalArgumentException("The scale must be a positive integer or zero")
            }
            val b1 = BigDecimal(java.lang.Double.toString(d1))
            val b2 = BigDecimal(java.lang.Double.toString(d2))
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toDouble()

        }
    }
}