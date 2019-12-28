package com.kiliansteenman.beancounter

import kotlin.reflect.KClass

class TypeFactory {

    private val mapping: MutableMap<String, AnalyticsAdapter<*>> = mutableMapOf()

    fun <T : Any> addMapping(bean: KClass<T>, adapter: AnalyticsAdapter<*>) {
        mapping[bean.toString()] = adapter
    }

    fun <T : Any> getAdapterForType(bean: KClass<T>): AnalyticsAdapter<T> {
        return mapping[bean.toString()] as AnalyticsAdapter<T>
    }
}