package com.kiliansteenman.teller.logger

import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.MeasurementInterceptor

class LoggingMeasurementInterceptor : MeasurementInterceptor {
    override fun process(measurement: Measurement): Measurement = measurement
}