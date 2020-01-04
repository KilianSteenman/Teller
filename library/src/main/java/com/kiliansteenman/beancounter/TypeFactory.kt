package com.kiliansteenman.beancounter

internal class TypeFactory {

    private val mapping: MutableMap<String, AnalyticsAdapter<*>> = mutableMapOf()

    fun addMapping(name: String, adapter: AnalyticsAdapter<*>) {
        mapping[name] = adapter
    }

    fun getAdapterForType(name: String?): AnalyticsAdapter<*>? {
        return mapping[name]
    }
}