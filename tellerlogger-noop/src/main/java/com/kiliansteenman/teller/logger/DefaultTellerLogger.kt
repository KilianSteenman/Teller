package com.kiliansteenman.teller.logger

import com.kiliansteenman.teller.logging.TellerLogger
import kotlin.reflect.KClass

class DefaultTellerLogger : TellerLogger {

    @Suppress("Unused")
    inline fun <reified T : Any> addMapping(adapter: LoggingAdapter<T>) {
        // NOOP
    }

    @Suppress("Unused")
    fun <T : Any> addMapping(type: KClass<T>, adapter: LoggingAdapter<T>) {
        // NOOP
    }

    override fun <T : Any> log(event: T) {
        // NOOP
    }
}