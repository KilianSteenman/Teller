package com.kiliansteenman.beancounter.logger.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent

@Database(entities = [AnalyticsLogEvent::class], version = 3)
abstract class BeanCounterDatabase : RoomDatabase() {
    abstract fun analyticsLogEventDao(): AnalyticsLogEventDao
}