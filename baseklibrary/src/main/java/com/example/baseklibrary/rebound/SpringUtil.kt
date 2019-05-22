/*
 *  Copyright (c) 2013, Facebook, Inc.
 *  All rights reserved.
 *
 *  This source code is licensed under the BSD-style license found in the
 *  LICENSE file in the root directory of this source tree. An additional grant
 *  of patent rights can be found in the PATENTS file in the same directory.
 *
 */

package com.example.baseklibrary.rebound

object SpringUtil {

    /**
     * Map a value within a given range to another range.
     * @param value the value to map
     * @param fromLow the low end of the range the value is within
     * @param fromHigh the high end of the range the value is within
     * @param toLow the low end of the range to map to
     * @param toHigh the high end of the range to map to
     * @return the mapped value
     */
    fun mapValueFromRangeToRange(
        value: Double,
        fromLow: Double,
        fromHigh: Double,
        toLow: Double,
        toHigh: Double
    ): Double {
        val fromRangeSize = fromHigh - fromLow
        val toRangeSize = toHigh - toLow
        val valueScale = (value - fromLow) / fromRangeSize
        return toLow + valueScale * toRangeSize
    }

    /**
     * Clamp a value to be within the provided range.
     * @param value the value to clamp
     * @param low the low end of the range
     * @param high the high end of the range
     * @return the clamped value
     */
    fun clamp(value: Double, low: Double, high: Double): Double {
        return Math.min(Math.max(value, low), high)
    }
}

