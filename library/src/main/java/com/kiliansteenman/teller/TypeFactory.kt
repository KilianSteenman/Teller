package com.kiliansteenman.teller

internal class TypeFactory {

    private val mapping: MutableMap<String, AnalyticsAdapter<*>> = mutableMapOf()

    fun addMapping(name: String, adapter: AnalyticsAdapter<*>) {
        mapping[name] = adapter
    }

    fun <T : Any> getAdapterForType(name: String?): AnalyticsAdapter<T>? {
        return mapping[name] as? AnalyticsAdapter<T>
    }
}