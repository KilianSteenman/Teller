package com.kiliansteenman.beancounter

import com.kiliansteenman.beancounter.internal.AnalyticsLogger
import com.kiliansteenman.beancounter.logging.LoggingAdapter

class BeanCounter {

    val typeFactory = TypeFactory()

    var logger: AnalyticsLogger? = null

    inline fun <reified T: Any> addAdapter(adapter: AnalyticsAdapter<T>) {
        typeFactory.addMapping(T::class, adapter)
    }

    inline fun <reified T> addLoggingAdapter(adapter: LoggingAdapter<T>) {
        logger?.addMapping(T::class.java, adapter)
    }

    inline fun <reified T: Any> count(event: T) {
        val adapter = typeFactory.getAdapterForType(T::class)
        adapter.count(event)
        logger?.log(event)
    }
}