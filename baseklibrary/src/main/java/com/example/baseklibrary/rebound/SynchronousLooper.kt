/**
 * Copyright (c) 2013, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.example.baseklibrary.rebound

class SynchronousLooper : SpringLooper() {
    var timeStep: Double = 0.toDouble()
    private var mRunning: Boolean = false

    init {
        timeStep = SIXTY_FPS
    }

    override fun start() {
        mRunning = true
        while (!mSpringSystem.isIdle) {
            if (mRunning == false) {
                break
            }
            mSpringSystem.loop(timeStep)
        }
    }

    override fun stop() {
        mRunning = false
    }

    companion object {

        val SIXTY_FPS = 16.6667
    }
}

