package com.kiliansteenman.beancounter.internal

import com.kiliansteenman.beancounter.internal.data.AnalyticsLogRepository
import com.kiliansteenman.beancounter.internal.ui.BeanCounterNotification
import com.kiliansteenman.beancounter.logging.LoggingAdapter
import com.kiliansteenman.beancounter.logging.LoggingTypeFactory

class AnalyticsLogger(
    val analyticsLogRepository: AnalyticsLogRepository,
    val notification: BeanCounterNotification
) {

    val loggingTypeFactory = LoggingTypeFactory()

    fun <T> addMapping(bean: Class<T>, adapter: LoggingAdapter<T>) {
        loggingTypeFactory.addMapping(bean, adapter)
    }

    inline fun <reified T> log(event: T) {
        val loggingAdapter = loggingTypeFactory.getAdapterForType(T::class.java)
        if (loggingAdapter != null) {
            val eventLog = loggingAdapter.toLogEvent(event)
            analyticsLogRepository.addLog(eventLog)
            notification.show(eventLog)
        }
    }
}