package com.kiliansteenman.teller.sample

import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.MeasurementInterceptor

internal class UserStateInterceptor : MeasurementInterceptor {

    override fun process(measurement: Measurement): Measurement {
        return measurement.newBuilder()
            .addParam("state" to "logged_in")
            .build()
    }
}