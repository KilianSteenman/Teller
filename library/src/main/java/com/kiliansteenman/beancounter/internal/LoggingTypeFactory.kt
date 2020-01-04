package com.kiliansteenman.beancounter.internal

import android.util.Log
import com.kiliansteenman.beancounter.logging.LoggingAdapter

class LoggingTypeFactory {

    private val mapping: MutableMap<String, LoggingAdapter<*>> = mutableMapOf()

    fun addMapping(type: String, adapter: LoggingAdapter<*>) {
        mapping[type] = adapter
    }

    fun <T> getAdapterForType(type: String?): LoggingAdapter<T>? {
        return if (mapping.containsKey(type)) {
            mapping[type] as LoggingAdapter<T>
        } else {
            Log.w("BeanCounter", "No logging adapter registered for $type")
            null
        }
    }
}