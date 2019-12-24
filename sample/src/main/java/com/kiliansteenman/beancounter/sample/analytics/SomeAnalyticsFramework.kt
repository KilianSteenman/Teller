package com.kiliansteenman.beancounter.sample.analytics

class SomeAnalyticsFramework {

    fun sendEvent(event: SomeAnalyticsFrameworkEvent) {
        // Send the event
    }
}

data class SomeAnalyticsFrameworkEvent(val name: String)