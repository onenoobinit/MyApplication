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

import java.util.*

/**
 * class for maintaining a registry of all spring configs
 */
class SpringConfigRegistry
/**
 * constructor for the SpringConfigRegistry
 */
internal constructor(includeDefaultEntry: Boolean) {

    private val mSpringConfigMap: MutableMap<SpringConfig, String>

    /**
     * retrieve all SpringConfig in the registry
     * @return a list of all SpringConfig
     */
    val allSpringConfig: Map<SpringConfig, String>
        get() = Collections.unmodifiableMap(mSpringConfigMap)

    init {
        mSpringConfigMap = HashMap()
        if (includeDefaultEntry) {
            addSpringConfig(SpringConfig.defaultConfig, "default config")
        }
    }

    /**
     * add a SpringConfig to the registry
     *
     * @param springConfig SpringConfig to add to the registry
     * @param configName name to give the SpringConfig in the registry
     * @return true if the SpringConfig was added, false if a config with that name is already
     * present.
     */
    fun addSpringConfig(springConfig: SpringConfig?, configName: String?): Boolean {
        if (springConfig == null) {
            throw IllegalArgumentException("springConfig is required")
        }
        if (configName == null) {
            throw IllegalArgumentException("configName is required")
        }
        if (mSpringConfigMap.containsKey(springConfig)) {
            return false
        }
        mSpringConfigMap[springConfig] = configName
        return true
    }

    /**
     * remove a specific SpringConfig from the registry
     * @param springConfig the of the SpringConfig to remove
     * @return true if the SpringConfig was removed, false if it was not present.
     */
    fun removeSpringConfig(springConfig: SpringConfig?): Boolean {
        if (springConfig == null) {
            throw IllegalArgumentException("springConfig is required")
        }
        return mSpringConfigMap.remove(springConfig) != null
    }

    /**
     * clear all SpringConfig in the registry
     */
    fun removeAllSpringConfig() {
        mSpringConfigMap.clear()
    }

    companion object {

        val instance = SpringConfigRegistry(true)
    }
}

