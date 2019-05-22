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
 * Data structure for storing spring configuration.
 */
class SpringConfig
/**
 * constructor for the SpringConfig
 * @param tension tension value for the SpringConfig
 * @param friction friction value for the SpringConfig
 */
    (var tension: Double, var friction: Double) {
    companion object {

        var defaultConfig = SpringConfig.fromOrigamiTensionAndFriction(40.0, 7.0)

        /**
         * A helper to make creating a SpringConfig easier with values mapping to the Origami values.
         * @param qcTension tension as defined in the Quartz Composition
         * @param qcFriction friction as defined in the Quartz Composition
         * @return a SpringConfig that maps to these values
         */
        fun fromOrigamiTensionAndFriction(qcTension: Double, qcFriction: Double): SpringConfig {
            return SpringConfig(
                OrigamiValueConverter.tensionFromOrigamiValue(qcTension),
                OrigamiValueConverter.frictionFromOrigamiValue(qcFriction)
            )
        }

        /**
         * Map values from the Origami POP Animation patch, which are based on a bounciness and speed
         * value.
         * @param bounciness bounciness of the POP Animation
         * @param speed speed of the POP Animation
         * @return a SpringConfig mapping to the specified POP Animation values.
         */
        fun fromBouncinessAndSpeed(bounciness: Double, speed: Double): SpringConfig {
            val bouncyConversion = BouncyConversion(speed, bounciness)
            return fromOrigamiTensionAndFriction(
                bouncyConversion.bouncyTension,
                bouncyConversion.bouncyFriction
            )
        }
    }
}
