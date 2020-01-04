package com.kiliansteenman.beancounter

import com.kiliansteenman.beancounter.internal.AnalyticsLogger
import kotlin.reflect.KClass

class BeanCounter {

    private val typeFactory = TypeFactory()

    var logger: AnalyticsLogger? = null

    fun <T : Any> addAdapter(adapter: AnalyticsAdapter<T>, type: KClass<T>) {
        val className =
            type.qualifiedName ?: throw IllegalArgumentException("Type requires a valid class name")

        typeFactory.addMapping(className, adapter)
    }

    fun <T : Any> count(event: T) {
        performCount(event)
        performLogging(event)
    }

    private fun <T : Any> performCount(event: T) {
        val adapter = typeFactory.getAdapterForType<T>(event::class.qualifiedName)
        if (adapter != null) {
            adapter.count(event)
        } else {
            throw IllegalStateException("No adapter registered for type ${event::class.qualifiedName}")
        }
    }

    private fun <T : Any> performLogging(event: T) {
        logger?.log(event)
    }
}