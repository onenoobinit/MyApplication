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
 * The spring looper is an interface for implementing platform-dependent run loops.
 */
abstract class SpringLooper {

    protected lateinit var mSpringSystem: BaseSpringSystem

    /**
     * Set the BaseSpringSystem that the SpringLooper will call back to.
     * @param springSystem the spring system to call loop on.
     */
    fun setSpringSystem(springSystem: BaseSpringSystem) {
        mSpringSystem = springSystem
    }

    /**
     * The BaseSpringSystem has requested that the looper begins running this [Runnable]
     * on every frame. The [Runnable] will continue running on every frame until
     * [.stop] is called.
     * If an existing [Runnable] had been started on this looper, it will be cancelled.
     */
    abstract fun start()

    /**
     * The looper will no longer run the [Runnable].
     */
    abstract fun stop()
}