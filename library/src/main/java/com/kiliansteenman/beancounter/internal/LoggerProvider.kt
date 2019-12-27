package com.kiliansteenman.beancounter.internal

import android.content.Context
import androidx.room.Room
import com.kiliansteenman.beancounter.internal.data.room.AnalyticsLogEventDao
import com.kiliansteenman.beancounter.internal.data.room.BeanCounterDatabase
import com.kiliansteenman.beancounter.internal.data.room.RoomAnalyticsLogRepository
import com.kiliansteenman.beancounter.internal.ui.BeanCounterNotification

object LoggerProvider {

    private fun getAnalyticsLogEventDao(context: Context): AnalyticsLogEventDao =
        Room.databaseBuilder(context, BeanCounterDatabase::class.java, "database-name")
            .fallbackToDestructiveMigration()
            .build()
            .analyticsLogEventDao()

    fun createAnalyticsLogger(context: Context): AnalyticsLogger =
        AnalyticsLogger(
            RoomAnalyticsLogRepository(getAnalyticsLogEventDao(context)),
            BeanCounterNotification(context)
        )
}