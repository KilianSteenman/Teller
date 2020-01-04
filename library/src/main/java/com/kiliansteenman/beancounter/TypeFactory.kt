package com.kiliansteenman.beancounter

import kotlin.reflect.KClass

class TypeFactory {

    private val mapping: MutableMap<String, AnalyticsAdapter<*>> = mutableMapOf()

    fun addMapping(name: String, adapter: AnalyticsAdapter<*>) {
        mapping[name] = adapter
    }

    fun <T : Any> getAdapterForType(bean: KClass<T>): AnalyticsAdapter<T> {
        return mapping[bean.toString()] as AnalyticsAdapter<T>
    }

    fun getAdapterForType(name: String?): AnalyticsAdapter<*>? {
        return mapping[name]
    }
}