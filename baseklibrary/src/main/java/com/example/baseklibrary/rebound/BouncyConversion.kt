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

/**
 * This class converts values from the Quartz Composer Bouncy patch into Bouncy QC tension and
 * friction values.
 */
class BouncyConversion(val speed: Double, val bounciness: Double) {

    val bouncyTension: Double
    val bouncyFriction: Double

    init {
        var b = normalize(bounciness / 1.7, 0.0, 20.0)
        b = project_normal(b, 0.0, 0.8)
        val s = normalize(speed / 1.7, 0.0, 20.0)
        bouncyTension = project_normal(s, 0.5, 200.0)
        bouncyFriction = quadratic_out_interpolation(b, b3_nobounce(bouncyTension), 0.01)
    }

    private fun normalize(value: Double, startValue: Double, endValue: Double): Double {
        return (value - startValue) / (endValue - startValue)
    }

    private fun project_normal(n: Double, start: Double, end: Double): Double {
        return start + n * (end - start)
    }

    private fun linear_interpolation(t: Double, start: Double, end: Double): Double {
        return t * end + (1f - t) * start
    }

    private fun quadratic_out_interpolation(t: Double, start: Double, end: Double): Double {
        return linear_interpolation(2 * t - t * t, start, end)
    }

    private fun b3_friction1(x: Double): Double {
        return 0.0007 * Math.pow(x, 3.0) - 0.031 * Math.pow(x, 2.0) + 0.64 * x + 1.28
    }

    private fun b3_friction2(x: Double): Double {
        return 0.000044 * Math.pow(x, 3.0) - 0.006 * Math.pow(x, 2.0) + 0.36 * x + 2.0
    }

    private fun b3_friction3(x: Double): Double {
        return 0.00000045 * Math.pow(x, 3.0) - 0.000332 * Math.pow(x, 2.0) + 0.1078 * x + 5.84
    }

    private fun b3_nobounce(tension: Double): Double {
        var friction = 0.0
        if (tension <= 18) {
            friction = b3_friction1(tension)
        } else if (tension > 18 && tension <= 44) {
            friction = b3_friction2(tension)
        } else if (tension > 44) {
            friction = b3_friction3(tension)
        } else {
            assert(false)
        }
        return friction
    }

}
