package com.kiliansteenman.teller.logger

import android.content.Context
import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.MeasurementInterceptor
import com.kiliansteenman.teller.logger.data.RepositoryProvider
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.TellerLogRepository
import com.kiliansteenman.teller.logger.ui.notification.TellerLogNotification
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class LoggingMeasurementInterceptor(
    context: Context
) : MeasurementInterceptor {

    private val tellerLogRepository = RepositoryProvider.getRepository(context)
    private val notification = TellerLogNotification(context)

    private val scope = MainScope()

    override fun process(measurement: Measurement): Measurement {
        val log = TellerLog(
            framework = measurement.framework,
            type = measurement.type,
            title = measurement.name ?: "",
            content = measurement.params.map { entry -> "${entry.key}: ${entry.value}" }
                .joinToString(separator = "\n")
        )

        scope.launch {
            val insertedEvent = tellerLogRepository.addLog(log)
            notification.show(insertedEvent)
        }

        return measurement
    }
}
