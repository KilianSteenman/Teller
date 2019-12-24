package com.kiliansteenman.beancounter

interface AnalyticsAdapter<T> {

    fun count(event: T)
}