package com.kiliansteenman.teller

interface AnalyticsAdapter<T> {

    fun count(event: T)
}