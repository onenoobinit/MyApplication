/**
 * Copyright (c) 2013, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.example.baseklibrary.rebound

class SteppingLooper : SpringLooper() {

    private var mStarted: Boolean = false
    private var mLastTime: Long = 0

    override fun start() {
        mStarted = true
        mLastTime = 0
    }

    fun step(interval: Long): Boolean {
        if (mSpringSystem == null || !mStarted) {
            return false
        }
        val currentTime = mLastTime + interval
        mSpringSystem.loop(currentTime.toDouble())
        mLastTime = currentTime
        return mSpringSystem.isIdle
    }

    override fun stop() {
        mStarted = false
    }
}

