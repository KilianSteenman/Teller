package com.kiliansteenman.beancounter.internal

import android.content.Context
import androidx.room.Room
import com.kiliansteenman.beancounter.internal.data.room.AnalyticsLogEventDao
import com.kiliansteenman.beancounter.internal.data.room.BeanCounterDatabase
import com.kiliansteenman.beancounter.internal.data.room.RoomAnalyticsLogRepository
import com.kiliansteenman.beancounter.internal.ui.BeanCounterNotification

object LoggerProvider {

    fun createAnalyticsLogger(context: Context): AnalyticsLogger =
        DefaultAnalyticsLogger(
            RoomAnalyticsLogRepository(context),
            BeanCounterNotification(context)
        )
}