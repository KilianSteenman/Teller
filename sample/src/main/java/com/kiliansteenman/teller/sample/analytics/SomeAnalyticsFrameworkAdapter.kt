package com.kiliansteenman.teller.sample.analytics

import com.kiliansteenman.teller.AnalyticsAdapter

class SomeAnalyticsFrameworkAdapter(
    private val someAnalyticsFramework: SomeAnalyticsFramework
) : AnalyticsAdapter<SomeAnalyticsFrameworkEvent> {

    override fun count(event: SomeAnalyticsFrameworkEvent) {
        someAnalyticsFramework.sendEvent(event)
    }
}