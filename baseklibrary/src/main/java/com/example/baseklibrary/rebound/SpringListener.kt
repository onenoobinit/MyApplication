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

interface SpringListener {

    /**
     * called whenever the spring is updated
     * @param spring the Spring sending the update
     */
    fun onSpringUpdate(spring: Spring)

    /**
     * called whenever the spring achieves a resting state
     * @param spring the spring that's now resting
     */
    fun onSpringAtRest(spring: Spring)

    /**
     * called whenever the spring leaves its resting state
     * @param spring the spring that has left its resting state
     */
    fun onSpringActivate(spring: Spring)

    /**
     * called whenever the spring notifies of displacement state changes
     * @param spring the spring whose end state has changed
     */
    fun onSpringEndStateChange(spring: Spring)
}

