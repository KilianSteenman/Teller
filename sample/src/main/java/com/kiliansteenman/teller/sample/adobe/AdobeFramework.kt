package com.kiliansteenman.teller.sample.adobe

import android.util.Log
import com.kiliansteenman.teller.Framework
import com.kiliansteenman.teller.Measurement

internal class AdobeFramework : Framework {

    override val name: String = NAME

    override fun count(measurement: Measurement) {
        Log.i(
            "Adobe",
            "Sending state event to Adobe ${measurement.name} with params ${measurement.params}"
        )
    }

    companion object {
        const val NAME = "Adobe"
    }
}
