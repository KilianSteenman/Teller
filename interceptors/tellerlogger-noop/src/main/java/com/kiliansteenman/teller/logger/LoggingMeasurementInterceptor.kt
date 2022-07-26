package com.kiliansteenman.teller.logger

import android.content.Context
import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.MeasurementInterceptor

@Suppress("UnusedParameter")
class LoggingMeasurementInterceptor(context: Context) : MeasurementInterceptor {
    override fun process(measurement: Measurement): Measurement = measurement
}