package com.kiliansteenman.beancounter.internal

import com.kiliansteenman.beancounter.internal.data.AnalyticsLogRepository
import com.kiliansteenman.beancounter.internal.ui.BeanCounterNotification
import com.kiliansteenman.beancounter.logging.LoggingAdapter
import kotlin.reflect.KClass

interface AnalyticsLogger {

    fun <T : Any> addMapping(type: KClass<T>, adapter: LoggingAdapter<T>)

    fun <T : Any> log(event: T)
}

class DefaultAnalyticsLogger(
    val analyticsLogRepository: AnalyticsLogRepository,
    val notification: BeanCounterNotification
) : AnalyticsLogger {

    private val loggingTypeFactory =
        LoggingTypeFactory()

    override fun <T : Any> addMapping(type: KClass<T>, adapter: LoggingAdapter<T>) {
        loggingTypeFactory.addMapping(type.qualifiedName!!, adapter)
    }

    override fun <T : Any> log(event: T) {
        val loggingAdapter = loggingTypeFactory.getAdapterForType<T>(event::class.qualifiedName)
        if (loggingAdapter != null) {
            val eventLog = loggingAdapter.toLogEvent(event)
            analyticsLogRepository.addLog(eventLog)
            notification.show(eventLog)
        }
    }
}