package com.kiliansteenman.beancounter.sample.analytics

import com.kiliansteenman.beancounter.AnalyticsAdapter

class SomeAnalyticsFrameworkAdapter(
    private val someAnalyticsFramework: SomeAnalyticsFramework
) : AnalyticsAdapter<SomeAnalyticsFrameworkEvent> {

    override fun count(event: SomeAnalyticsFrameworkEvent) {
        someAnalyticsFramework.sendEvent(event)
    }
}