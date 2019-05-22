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

class SimpleSpringListener : SpringListener {
    override fun onSpringUpdate(spring: Spring) {}

    override fun onSpringAtRest(spring: Spring) {}

    override fun onSpringActivate(spring: Spring) {}

    override fun onSpringEndStateChange(spring: Spring) {}
}
