package com.kiliansteenman.beancounter.logging

import android.util.Log

class LoggingTypeFactory {

    private val mapping: MutableMap<String, LoggingAdapter<*>> = mutableMapOf()

    fun <T> addMapping(bean: Class<T>, adapter: LoggingAdapter<*>) {
        mapping[bean.name] = adapter
    }

    fun <T> getAdapterForType(bean: Class<T>): LoggingAdapter<T>? {
        return if (mapping.containsKey(bean.name)) {
            mapping[bean.name] as LoggingAdapter<T>
        } else {
            Log.w("BeanCounter", "No logging adapter registered for ${bean.name}")
            null
        }
    }
}