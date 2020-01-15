package com.kiliansteenman.teller.sample.analytics

import android.util.Log

class SomeAnalyticsFramework {

    fun sendEvent(event: SomeAnalyticsFrameworkEvent) {
        Log.v("SomeAnalyticsFramework", "Sending event ${event.name}")
    }
}

data class SomeAnalyticsFrameworkEvent(
    val name: String,
    val params: Map<String, String> = emptyMap()
)