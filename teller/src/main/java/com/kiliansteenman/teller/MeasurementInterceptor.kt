package com.kiliansteenman.teller

interface MeasurementInterceptor {

    fun process(measurement: Measurement): Measurement
}