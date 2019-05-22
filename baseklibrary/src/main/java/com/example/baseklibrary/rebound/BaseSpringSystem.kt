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
import java.util.concurrent.CopyOnWriteArraySet

/**
 * BaseSpringSystem maintains the set of springs within an Application context. It is responsible for
 * Running the spring integration loop and maintains a registry of all the Springs it solves for.
 * In addition to listening to physics events on the individual Springs in the system, listeners
 * can be added to the BaseSpringSystem itself to provide pre and post integration setup.
 */
class BaseSpringSystem
/**
 * create a new BaseSpringSystem
 * @param springLooper parameterized springLooper to allow testability of the
 * physics loop
 */
    (private val mSpringLooper: SpringLooper?) {

    private val mSpringRegistry = HashMap<String, Spring>()
    private val mActiveSprings = CopyOnWriteArraySet<Spring>()
    private val mListeners = CopyOnWriteArraySet<SpringSystemListener>()
    /**
     * check if the system is idle
     * @return is the system idle
     */
    var isIdle = true
        private set

    /**
     * return all the springs in the simulator
     * @return all the springs
     */
    val allSprings: List<Spring>
        get() {
            val collection = mSpringRegistry.values
            val list: List<Spring>
            if (collection is List<*>) {
                list = collection as List<Spring>
            } else {
                list = ArrayList(collection)
            }
            return Collections.unmodifiableList(list)
        }

    init {
        if (mSpringLooper == null) {
            throw IllegalArgumentException("springLooper is required")
        }
        mSpringLooper.setSpringSystem(this)
    }

    /**
     * create a spring with a random uuid for its name.
     * @return the spring
     */
    fun createSpring(): Spring {
        val spring = Spring(this)
        registerSpring(spring)
        return spring
    }

    /**
     * get a spring by name
     * @param id id of the spring to retrieve
     * @return Spring with the specified key
     */
    fun getSpringById(id: String?): Spring? {
        if (id == null) {
            throw IllegalArgumentException("id is required")
        }
        return mSpringRegistry[id]
    }

    /**
     * Registers a Spring to this BaseSpringSystem so it can be iterated if active.
     * @param spring the Spring to register
     */
    internal fun registerSpring(spring: Spring?) {
        if (spring == null) {
            throw IllegalArgumentException("spring is required")
        }
        if (mSpringRegistry.containsKey(spring.id)) {
            throw IllegalArgumentException("spring is already registered")
        }
        mSpringRegistry[spring.id] = spring
    }

    /**
     * Deregisters a Spring from this BaseSpringSystem, so it won't be iterated anymore. The Spring should
     * not be used anymore after doing this.
     *
     * @param spring the Spring to deregister
     */
    internal fun deregisterSpring(spring: Spring?) {
        if (spring == null) {
            throw IllegalArgumentException("spring is required")
        }
        mActiveSprings.remove(spring)
        mSpringRegistry.remove(spring.id)
    }

    /**
     * update the springs in the system
     * @param deltaTime delta since last update in millis
     */
    internal fun advance(deltaTime: Double) {
        for (spring in mActiveSprings) {
            // advance time in seconds
            if (spring.systemShouldAdvance()) {
                spring.advance(deltaTime / 1000.0)
            } else {
                mActiveSprings.remove(spring)
            }
        }
    }

    /**
     * loop the system until idle
     * @param elapsedMillis elapsed milliseconds
     */
    fun loop(elapsedMillis: Double) {
        for (listener in mListeners) {
            listener.onBeforeIntegrate(this)
        }
        advance(elapsedMillis)
        if (mActiveSprings.isEmpty()) {
            isIdle = true
        }
        for (listener in mListeners) {
            listener.onAfterIntegrate(this)
        }
        if (isIdle) {
            if (mSpringLooper != null) {
                mSpringLooper.stop()
            }
        }
    }

    /**
     * This is used internally by the [Spring]s created by this [BaseSpringSystem] to notify
     * it has reached a state where it needs to be iterated. This will add the spring to the list of
     * active springs on this system and start the iteration if the system was idle before this call.
     * @param springId the id of the Spring to be activated
     */
    internal fun activateSpring(springId: String) {
        val spring = mSpringRegistry[springId]
            ?: throw IllegalArgumentException("springId $springId does not reference a registered spring")
        mActiveSprings.add(spring)
        if (isIdle) {
            isIdle = false
            if (mSpringLooper != null) {
                mSpringLooper.start()
            }
        }
    }

    /** listeners  */

    /**
     * Add new listener object.
     * @param newListener listener
     */
    fun addListener(newListener: SpringSystemListener?) {
        if (newListener == null) {
            throw IllegalArgumentException("newListener is required")
        }
        mListeners.add(newListener)
    }

    /**
     * Remove listener object.
     * @param listenerToRemove listener
     */
    fun removeListener(listenerToRemove: SpringSystemListener?) {
        if (listenerToRemove == null) {
            throw IllegalArgumentException("listenerToRemove is required")
        }
        mListeners.remove(listenerToRemove)
    }

    /**
     * Remove all listeners.
     */
    fun removeAllListeners() {
        mListeners.clear()
    }
}


