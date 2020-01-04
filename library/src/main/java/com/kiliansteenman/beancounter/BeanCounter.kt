package com.kiliansteenman.beancounter

import com.kiliansteenman.beancounter.internal.AnalyticsLogger
import com.kiliansteenman.beancounter.logging.LoggingAdapter
import kotlin.reflect.KClass

class BeanCounter {

    private val typeFactory = TypeFactory()

    var logger: AnalyticsLogger? = null

    fun <T : Any> addAdapter(adapter: AnalyticsAdapter<T>, clazz: KClass<T>) {
        typeFactory.addMapping(clazz.simpleName!!, adapter)
    }

    inline fun <reified T> addLoggingAdapter(adapter: LoggingAdapter<T>) {
        logger?.addMapping(T::class.java, adapter)
    }

    fun <T : Any> count(event: T) {
        val adapter = typeFactory.getAdapterForType(event::class.simpleName) as? AnalyticsAdapter<T>
        if (adapter != null) {
            adapter.count(event)
        } else {
            throw IllegalStateException("No adapter registered for type ${event::class.simpleName}")
        }
//        logger?.log(event)
    }
}