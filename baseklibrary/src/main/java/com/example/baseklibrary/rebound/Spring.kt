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

import java.util.concurrent.CopyOnWriteArraySet

/**
 * Classical spring implementing Hooke's law with configurable friction and tension.
 */
class Spring
/**
 * create a new spring
 */
internal constructor(private val mSpringSystem: BaseSpringSystem?) {
    private var mSpringConfig: SpringConfig? = null
    private var mOvershootClampingEnabled: Boolean = false

    // unique id for the spring in the system
    /**
     * get the unique id for this spring
     * @return the unique id
     */
    val id: String
    // all physics simulation objects are final and reused in each processing pass
    private val mCurrentState = PhysicsState()
    private val mPreviousState = PhysicsState()
    private val mTempState = PhysicsState()
    /**
     * Get the displacement value from the last time setCurrentValue was called.
     * @return displacement value
     */
    var startValue: Double = 0.toDouble()
        private set
    private var mEndValue: Double = 0.toDouble()
    private var mWasAtRest = true
    // thresholds for determining when the spring is at rest
    private var mRestSpeedThreshold = 0.005
    private var mDisplacementFromRestThreshold = 0.005
    private var mTimeAccumulator = 0.0
    private val mListeners = CopyOnWriteArraySet<SpringListener>()

    /**
     * Get the current
     * @return current value
     */
    val currentValue: Double
        get() = mCurrentState.position

    /**
     * get the displacement of the springs current value from its rest value.
     * @return the distance displaced by
     */
    val currentDisplacementDistance: Double
        get() = getDisplacementDistanceForState(mCurrentState)

    /**
     * get the velocity of the spring
     * @return the current velocity
     */
    val velocity: Double
        get() = mCurrentState.velocity

    /**
     * Check if the spring is overshooting beyond its target.
     * @return true if the spring is overshooting its target
     */
    val isOvershooting: Boolean
        get() = mSpringConfig!!.tension > 0 && (startValue < mEndValue && currentValue > mEndValue || startValue > mEndValue && currentValue < mEndValue)

    /**
     * check if the current state is at rest
     * @return is the spring at rest
     */
    val isAtRest: Boolean
        get() = Math.abs(mCurrentState.velocity) <= mRestSpeedThreshold && (getDisplacementDistanceForState(
            mCurrentState
        ) <= mDisplacementFromRestThreshold || mSpringConfig!!.tension == 0.0)

    // storage for the current and prior physics state while integration is occurring
    private class PhysicsState {
        internal var position: Double = 0.toDouble()
        internal var velocity: Double = 0.toDouble()
    }

    init {
        if (mSpringSystem == null) {
            throw IllegalArgumentException("Spring cannot be created outside of a BaseSpringSystem")
        }
        id = "spring:" + ID++
        setSpringConfig(SpringConfig.defaultConfig)
    }

    /**
     * Destroys this Spring, meaning that it will be deregistered from its BaseSpringSystem so it won't be
     * iterated anymore and will clear its set of listeners. Do not use the Spring after calling this,
     * doing so may just cause an exception to be thrown.
     */
    fun destroy() {
        mListeners.clear()
        mSpringSystem!!.deregisterSpring(this)
    }

    /**
     * set the config class
     * @param springConfig config class for the spring
     * @return this Spring instance for chaining
     */
    fun setSpringConfig(springConfig: SpringConfig?): Spring {
        if (springConfig == null) {
            throw IllegalArgumentException("springConfig is required")
        }
        mSpringConfig = springConfig
        return this
    }

    /**
     * retrieve the spring config for this spring
     * @return the SpringConfig applied to this spring
     */
    fun getSpringConfig(): SpringConfig? {
        return mSpringConfig
    }

    /**
     * The full signature for setCurrentValue includes the option of not setting the spring at rest
     * after updating its currentValue. Passing setAtRest false means that if the endValue of the
     * spring is not equal to the currentValue, the physics system will start iterating to resolve
     * the spring to the end value. This is almost never the behavior that you want, so the default
     * setCurrentValue signature passes true.
     * @param currentValue the new start and current value for the spring
     * @param setAtRest optionally set the spring at rest after updating its current value.
     * see [com.facebook.rebound.Spring.setAtRest]
     * @return the spring for chaining
     */
    @JvmOverloads
    fun setCurrentValue(currentValue: Double, setAtRest: Boolean = true): Spring {
        startValue = currentValue
        mCurrentState.position = currentValue
        mSpringSystem!!.activateSpring(this.id)
        for (listener in mListeners) {
            listener.onSpringUpdate(this)
        }
        if (setAtRest) {
            setAtRest()
        }
        return this
    }

    /**
     * get the displacement from rest for a given physics state
     * @param state the state to measure from
     * @return the distance displaced by
     */
    private fun getDisplacementDistanceForState(state: PhysicsState): Double {
        return Math.abs(mEndValue - state.position)
    }

    /**
     * set the rest value to determine the displacement for the spring
     * @param endValue the endValue for the spring
     * @return the spring for chaining
     */
    fun setEndValue(endValue: Double): Spring {
        if (mEndValue == endValue && isAtRest) {
            return this
        }
        startValue = currentValue
        mEndValue = endValue
        mSpringSystem!!.activateSpring(this.id)
        for (listener in mListeners) {
            listener.onSpringEndStateChange(this)
        }
        return this
    }

    /**
     * get the rest value used for determining the displacement of the spring
     * @return the rest value for the spring
     */
    fun getEndValue(): Double {
        return mEndValue
    }

    /**
     * set the velocity on the spring in pixels per second
     * @param velocity velocity value
     * @return the spring for chaining
     */
    fun setVelocity(velocity: Double): Spring {
        if (velocity == mCurrentState.velocity) {
            return this
        }
        mCurrentState.velocity = velocity
        mSpringSystem!!.activateSpring(this.id)
        return this
    }

    /**
     * Sets the speed at which the spring should be considered at rest.
     * @param restSpeedThreshold speed pixels per second
     * @return the spring for chaining
     */
    fun setRestSpeedThreshold(restSpeedThreshold: Double): Spring {
        mRestSpeedThreshold = restSpeedThreshold
        return this
    }

    /**
     * Returns the speed at which the spring should be considered at rest in pixels per second
     * @return speed in pixels per second
     */
    fun getRestSpeedThreshold(): Double {
        return mRestSpeedThreshold
    }

    /**
     * set the threshold of displacement from rest below which the spring should be considered at rest
     * @param displacementFromRestThreshold displacement to consider resting below
     * @return the spring for chaining
     */
    fun setRestDisplacementThreshold(displacementFromRestThreshold: Double): Spring {
        mDisplacementFromRestThreshold = displacementFromRestThreshold
        return this
    }

    /**
     * get the threshold of displacement from rest below which the spring should be considered at rest
     * @return displacement to consider resting below
     */
    fun getRestDisplacementThreshold(): Double {
        return mDisplacementFromRestThreshold
    }

    /**
     * Force the spring to clamp at its end value to avoid overshooting the target value.
     * @param overshootClampingEnabled whether or not to enable overshoot clamping
     * @return the spring for chaining
     */
    fun setOvershootClampingEnabled(overshootClampingEnabled: Boolean): Spring {
        mOvershootClampingEnabled = overshootClampingEnabled
        return this
    }

    /**
     * Check if overshoot clamping is enabled.
     * @return is overshoot clamping enabled
     */
    fun isOvershootClampingEnabled(): Boolean {
        return mOvershootClampingEnabled
    }

    /**
     * advance the physics simulation in SOLVER_TIMESTEP_SEC sized chunks to fulfill the required
     * realTimeDelta.
     * The math is inlined inside the loop since it made a huge performance impact when there are
     * several springs being advanced.
     * @param realDeltaTime clock drift
     */
    internal fun advance(realDeltaTime: Double) {

        var isAtRest = isAtRest

        if (isAtRest && mWasAtRest) {
            /* begin debug
      Log.d(TAG, "bailing out because we are at rest:" + getName());
      end debug */
            return
        }

        // clamp the amount of realTime to simulate to avoid stuttering in the UI. We should be able
        // to catch up in a subsequent advance if necessary.
        var adjustedDeltaTime = realDeltaTime
        if (realDeltaTime > MAX_DELTA_TIME_SEC) {
            adjustedDeltaTime = MAX_DELTA_TIME_SEC
        }

        /* begin debug
    long startTime = System.currentTimeMillis();
    int iterations = 0;
    end debug */

        mTimeAccumulator += adjustedDeltaTime

        val tension = mSpringConfig!!.tension
        val friction = mSpringConfig!!.friction

        var position = mCurrentState.position
        var velocity = mCurrentState.velocity
        var tempPosition = mTempState.position
        var tempVelocity = mTempState.velocity

        var aVelocity: Double
        var aAcceleration: Double
        var bVelocity: Double
        var bAcceleration: Double
        var cVelocity: Double
        var cAcceleration: Double
        var dVelocity: Double
        var dAcceleration: Double

        var dxdt: Double
        var dvdt: Double

        // iterate over the true time
        while (mTimeAccumulator >= SOLVER_TIMESTEP_SEC) {
            /* begin debug
      iterations++;
      end debug */
            mTimeAccumulator -= SOLVER_TIMESTEP_SEC

            if (mTimeAccumulator < SOLVER_TIMESTEP_SEC) {
                // This will be the last iteration. Remember the previous state in case we need to
                // interpolate
                mPreviousState.position = position
                mPreviousState.velocity = velocity
            }

            // Perform an RK4 integration to provide better detection of the acceleration curve via
            // sampling of Euler integrations at 4 intervals feeding each derivative into the calculation
            // of the next and taking a weighted sum of the 4 derivatives as the final output.

            // This math was inlined since it made for big performance improvements when advancing several
            // springs in one pass of the BaseSpringSystem.

            // The initial derivative is based on the current velocity and the calculated acceleration
            aVelocity = velocity
            aAcceleration = tension * (mEndValue - tempPosition) - friction * velocity

            // Calculate the next derivatives starting with the last derivative and integrating over the
            // timestep
            tempPosition = position + aVelocity * SOLVER_TIMESTEP_SEC * 0.5
            tempVelocity = velocity + aAcceleration * SOLVER_TIMESTEP_SEC * 0.5
            bVelocity = tempVelocity
            bAcceleration = tension * (mEndValue - tempPosition) - friction * tempVelocity

            tempPosition = position + bVelocity * SOLVER_TIMESTEP_SEC * 0.5
            tempVelocity = velocity + bAcceleration * SOLVER_TIMESTEP_SEC * 0.5
            cVelocity = tempVelocity
            cAcceleration = tension * (mEndValue - tempPosition) - friction * tempVelocity

            tempPosition = position + cVelocity * SOLVER_TIMESTEP_SEC
            tempVelocity = velocity + cAcceleration * SOLVER_TIMESTEP_SEC
            dVelocity = tempVelocity
            dAcceleration = tension * (mEndValue - tempPosition) - friction * tempVelocity

            // Take the weighted sum of the 4 derivatives as the final output.
            dxdt = 1.0 / 6.0 * (aVelocity + 2.0 * (bVelocity + cVelocity) + dVelocity)
            dvdt = 1.0 / 6.0 * (aAcceleration + 2.0 * (bAcceleration + cAcceleration) + dAcceleration)

            position += dxdt * SOLVER_TIMESTEP_SEC
            velocity += dvdt * SOLVER_TIMESTEP_SEC
        }

        mTempState.position = tempPosition
        mTempState.velocity = tempVelocity

        mCurrentState.position = position
        mCurrentState.velocity = velocity

        if (mTimeAccumulator > 0) {
            interpolate(mTimeAccumulator / SOLVER_TIMESTEP_SEC)
        }

        // End the spring immediately if it is overshooting and overshoot clamping is enabled.
        // Also make sure that if the spring was considered within a resting threshold that it's now
        // snapped to its end value.
        if (isAtRest || mOvershootClampingEnabled && isOvershooting) {
            // Don't call setCurrentValue because that forces a call to onSpringUpdate
            if (tension > 0) {
                startValue = mEndValue
                mCurrentState.position = mEndValue
            } else {
                mEndValue = mCurrentState.position
                startValue = mEndValue
            }
            setVelocity(0.0)
            isAtRest = true
        }

        /* begin debug
    long endTime = System.currentTimeMillis();
    long elapsedMillis = endTime - startTime;
    Log.d(TAG,
        "iterations:" + iterations +
            " iterationTime:" + elapsedMillis +
            " position:" + mCurrentState.position +
            " velocity:" + mCurrentState.velocity +
            " realDeltaTime:" + realDeltaTime +
            " adjustedDeltaTime:" + adjustedDeltaTime +
            " isAtRest:" + isAtRest +
            " wasAtRest:" + mWasAtRest);
    end debug */

        // NB: do these checks outside the loop so all listeners are properly notified of the state
        //     transition
        var notifyActivate = false
        if (mWasAtRest) {
            mWasAtRest = false
            notifyActivate = true
        }
        var notifyAtRest = false
        if (isAtRest) {
            mWasAtRest = true
            notifyAtRest = true
        }
        for (listener in mListeners) {
            // starting to move
            if (notifyActivate) {
                listener.onSpringActivate(this)
            }

            // updated
            listener.onSpringUpdate(this)

            // coming to rest
            if (notifyAtRest) {
                listener.onSpringAtRest(this)
            }
        }
    }

    /**
     * Check if this spring should be advanced by the system.  * The rule is if the spring is
     * currently at rest and it was at rest in the previous advance, the system can skip this spring
     * @return should the system process this spring
     */
    fun systemShouldAdvance(): Boolean {
        return !isAtRest || !wasAtRest()
    }

    /**
     * Check if the spring was at rest in the prior iteration. This is used for ensuring the ending
     * callbacks are fired as the spring comes to a rest.
     * @return true if the spring was at rest in the prior iteration
     */
    fun wasAtRest(): Boolean {
        return mWasAtRest
    }

    /**
     * Set the spring to be at rest by making its end value equal to its current value and setting
     * velocity to 0.
     * @return this object
     */
    fun setAtRest(): Spring {
        mEndValue = mCurrentState.position
        mTempState.position = mCurrentState.position
        mCurrentState.velocity = 0.0
        return this
    }

    /**
     * linear interpolation between the previous and current physics state based on the amount of
     * timestep remaining after processing the rendering delta time in timestep sized chunks.
     * @param alpha from 0 to 1, where 0 is the previous state, 1 is the current state
     */
    private fun interpolate(alpha: Double) {
        mCurrentState.position = mCurrentState.position * alpha + mPreviousState.position * (1 - alpha)
        mCurrentState.velocity = mCurrentState.velocity * alpha + mPreviousState.velocity * (1 - alpha)
    }

    /** listeners  */

    /**
     * add a listener
     * @param newListener to add
     * @return the spring for chaining
     */
    fun addListener(newListener: SpringListener?): Spring {
        if (newListener == null) {
            throw IllegalArgumentException("newListener is required")
        }
        mListeners.add(newListener)
        return this
    }

    /**
     * remove a listener
     * @param listenerToRemove to remove
     * @return the spring for chaining
     */
    fun removeListener(listenerToRemove: SpringListener?): Spring {
        if (listenerToRemove == null) {
            throw IllegalArgumentException("listenerToRemove is required")
        }
        mListeners.remove(listenerToRemove)
        return this
    }

    /**
     * remove all of the listeners
     * @return the spring for chaining
     */
    fun removeAllListeners(): Spring {
        mListeners.clear()
        return this
    }

    /**
     * This method checks to see that the current spring displacement value is equal to the input,
     * accounting for the spring's rest displacement threshold.
     * @param value The value to compare the spring value to
     * @return Whether the displacement value from the spring is within the bounds of the compare
     * value, accounting for threshold
     */
    fun currentValueIsApproximately(value: Double): Boolean {
        return Math.abs(currentValue - value) <= getRestDisplacementThreshold()
    }

    companion object {

        // unique incrementer id for springs
        private var ID = 0

        // maximum amount of time to simulate per physics iteration in seconds (4 frames at 60 FPS)
        private val MAX_DELTA_TIME_SEC = 0.064
        // fixed timestep to use in the physics solver in seconds
        private val SOLVER_TIMESTEP_SEC = 0.001
    }

}
/**
 * Set the displaced value to determine the displacement for the spring from the rest value.
 * This value is retained and used to calculate the displacement ratio.
 * The default signature also sets the Spring at rest to facilitate the common behavior of moving
 * a spring to a new position.
 * @param currentValue the new start and current value for the spring
 * @return the spring for chaining
 */

