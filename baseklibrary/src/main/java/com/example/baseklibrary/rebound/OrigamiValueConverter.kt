package com.example.baseklibrary.rebound

/**
 * Helper math util to convert tension &amp; friction values from the Origami design tool to values
 * that the spring system needs.
 */
object OrigamiValueConverter {

    fun tensionFromOrigamiValue(oValue: Double): Double {
        return if (oValue == 0.0) 0.0 else (oValue - 30.0) * 3.62 + 194.0
    }

    fun origamiValueFromTension(tension: Double): Double {
        return if (tension == 0.0) 0.0 else (tension - 194.0) / 3.62 + 30.0
    }

    fun frictionFromOrigamiValue(oValue: Double): Double {
        return if (oValue == 0.0) 0.0 else (oValue - 8.0) * 3.0 + 25.0
    }

    fun origamiValueFromFriction(friction: Double): Double {
        return if (friction == 0.0) 0.0 else (friction - 25.0) / 3.0 + 8.0
    }

}
