package com.kiliansteenman.teller.framework

import android.util.Log
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.kiliansteenman.teller.Framework
import com.kiliansteenman.teller.Measurement

class FirebaseFramework : Framework {

    override val name: String = NAME

    override fun count(measurement: Measurement) {
        Firebase.analytics.logEvent(measurement.name) {
            measurement.params.forEach { parameter ->
                when (val value = parameter.value) {
                    is Float -> param(parameter.key, value.toDouble())
                    is Double -> param(parameter.key, value)
                    is String -> param(parameter.key, value)
                    is Int -> param(parameter.key, value.toLong())
                    is Long -> param(parameter.key, value)
                    else -> Log.w(LOG_TAG, "Unknown parameter type ${value::class}")
                }
            }
        }
    }

    companion object {
        private const val LOG_TAG = "Teller"
        const val NAME = "Firebase"
    }
}