package com.kiliansteenman.teller.sample.firebase

import android.util.Log
import com.kiliansteenman.teller.Framework
import com.kiliansteenman.teller.Measurement

internal class FirebaseFramework : Framework {

    override val name: String = NAME

    override fun count(measurement: Measurement) {
        Log.i(
            "Firebase",
            "Sending event to firebase ${measurement.name} with params ${measurement.params}"
        )

        // Send the firebase event to firebase.
        // Example code:
        // Firebase.analytics.logEvent(event.name) {
        //    addParams(event.params)
        // }
    }

    companion object {
        const val NAME = "Firebase"
    }
}