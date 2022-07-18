package com.kiliansteenman.teller.framework

import android.util.Log
import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.MeasurementInterceptor

class LoggingInterceptor(
    private val tag: String = "Teller",
    private val logLevel: Int = Log.VERBOSE
) : MeasurementInterceptor {

    override fun process(measurement: Measurement): Measurement {
        val log = StringBuilder("${measurement.framework} - ${measurement.type}")
            .appendLine("Name ${measurement.name}").apply {
                if (measurement.params.isNotEmpty()) {
                    appendLine("Params:")
                    measurement.params.forEach { (key, value) ->
                        appendLine("$key: $value")
                    }
                }
            }
            .toString()

        Log.println(logLevel, tag, log)
        return measurement
    }

    private companion object {
        private const val TAG = "Teller"
    }
}