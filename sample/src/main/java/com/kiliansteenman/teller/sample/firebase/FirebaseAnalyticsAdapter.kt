package com.kiliansteenman.teller.sample.firebase

import android.util.Log
import com.kiliansteenman.teller.AnalyticsAdapter

internal class FirebaseAnalyticsAdapter : AnalyticsAdapter<FirebaseEvent> {

    override fun count(event: FirebaseEvent) {
        Log.i("Firebase", "Sending event to firebase ${event.name} with params ${event.params}")

        // Send the firebase event to firebase.
        // Example code:
        // Firebase.analytics.logEvent(event.name) {
        //    addParams(event.params)
        // }
    }
}