package com.kiliansteenman.beancounter

class TypeFactory {

    private val mapping: MutableMap<String, AnalyticsAdapter<*>> = mutableMapOf()

    fun <T> addMapping(bean: Class<T>, adapter: AnalyticsAdapter<*>) {
        mapping[bean.name] = adapter
    }

    fun <T> getAdapterForType(bean: Class<T>): AnalyticsAdapter<T> {
        return mapping[bean.name] as AnalyticsAdapter<T>
    }
}