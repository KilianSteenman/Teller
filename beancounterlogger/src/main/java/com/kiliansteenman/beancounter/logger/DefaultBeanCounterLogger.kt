package com.kiliansteenman.beancounter.logger

import com.kiliansteenman.beancounter.logger.data.AnalyticsLogRepository
import com.kiliansteenman.beancounter.logger.ui.BeanCounterNotification
import com.kiliansteenman.beancounter.logging.BeanCounterLogger
import kotlin.reflect.KClass

class DefaultBeanCounterLogger(
    private val analyticsLogRepository: AnalyticsLogRepository,
    private val notification: BeanCounterNotification
) : BeanCounterLogger {

    private val loggingTypeFactory =
        LoggingTypeFactory()

    fun <T : Any> addMapping(type: KClass<T>, adapter: LoggingAdapter<T>) {
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