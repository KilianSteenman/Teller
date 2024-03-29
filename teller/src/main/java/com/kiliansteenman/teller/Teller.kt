package com.kiliansteenman.teller

import android.util.Log
import com.kiliansteenman.teller.logging.TellerLogger
import kotlin.reflect.KClass

class Teller private constructor() {

    private val typeFactory = TypeFactory()

    var logger: TellerLogger? = null

    inline fun <reified T : Any> addAdapter(adapter: AnalyticsAdapter<T>) {
        addAdapter(T::class, adapter)
    }

    fun <T : Any> addAdapter(type: KClass<T>, adapter: AnalyticsAdapter<T>) {
        val className =
            type.qualifiedName ?: throw IllegalArgumentException("Type requires a valid class name")

        typeFactory.addMapping(className, adapter)
    }

    fun clearAdapters() {
        typeFactory.clearMapping()
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
            val exception = IllegalStateException("No adapter registered for type ${event::class.qualifiedName}")
            if (BuildConfig.DEBUG) {
                throw exception
            } else {
                Log.w("Teller", exception)
            }
        }
    }

    private fun <T : Any> performLogging(event: T) {
        logger?.log(event)
    }

    companion object {

        val instance: Teller by lazy { Teller() }
    }
}
