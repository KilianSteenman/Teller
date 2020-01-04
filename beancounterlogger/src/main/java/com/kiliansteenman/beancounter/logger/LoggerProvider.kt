package com.kiliansteenman.beancounter.logger

import android.content.Context
import com.kiliansteenman.beancounter.logger.data.room.RoomAnalyticsLogRepository
import com.kiliansteenman.beancounter.logger.ui.BeanCounterNotification

object LoggerProvider {

    fun createAnalyticsLogger(context: Context): DefaultBeanCounterLogger =
        DefaultBeanCounterLogger(
            RoomAnalyticsLogRepository(context),
            BeanCounterNotification(context)
        )
}