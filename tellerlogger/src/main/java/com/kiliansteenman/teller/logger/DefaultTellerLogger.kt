package com.kiliansteenman.teller.logger

import com.kiliansteenman.teller.logger.data.TellerLogRepository
import com.kiliansteenman.teller.logger.ui.notification.TellerLogNotification
import com.kiliansteenman.teller.logging.TellerLogger
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class DefaultTellerLogger(
    private val tellerLogRepository: TellerLogRepository,
    private val notification: TellerLogNotification
) : TellerLogger {

    private val scope = MainScope()

    private val loggingTypeFactory =
        LoggingTypeFactory()

    inline fun <reified T : Any> addMapping(adapter: LoggingAdapter<T>) {
        addMapping(T::class, adapter)
    }

    fun <T : Any> addMapping(type: KClass<T>, adapter: LoggingAdapter<T>) {
        loggingTypeFactory.addMapping(type.qualifiedName!!, adapter)
    }

    override fun <T : Any> log(event: T) {
        val loggingAdapter = loggingTypeFactory.getAdapterForType<T>(event::class.qualifiedName)
        if (loggingAdapter != null) {
            val eventLog = loggingAdapter.toLogEvent(event)

            scope.launch {
                val insertedEvent = tellerLogRepository.addLog(eventLog)
                notification.show(insertedEvent)
            }
        }
    }
}
