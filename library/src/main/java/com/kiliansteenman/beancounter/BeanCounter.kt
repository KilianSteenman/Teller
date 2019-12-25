package com.kiliansteenman.beancounter

import android.content.Context
import androidx.room.Room
import com.kiliansteenman.beancounter.internal.AnalyticsLogger
import com.kiliansteenman.beancounter.internal.data.room.BeanCounterDatabase
import com.kiliansteenman.beancounter.internal.data.room.RoomAnalyticsLogRepository
import com.kiliansteenman.beancounter.internal.ui.BeanCounterNotification
import com.kiliansteenman.beancounter.logging.LoggingAdapter

class BeanCounter(private val context: Context) {

    val typeFactory = TypeFactory()

    private val db: BeanCounterDatabase
        get() {
            return Room.databaseBuilder(context, BeanCounterDatabase::class.java, "database-name")
                .fallbackToDestructiveMigration()
                .build()
        }

    val logger = AnalyticsLogger(
        RoomAnalyticsLogRepository(db.analyticsLogEventDao()),
        BeanCounterNotification(context)
    )

    inline fun <reified T> addAdapter(adapter: AnalyticsAdapter<T>) {
        typeFactory.addMapping(T::class.java, adapter)
    }

    inline fun <reified T> addLoggingAdapter(adapter: LoggingAdapter<T>) {
        logger.addMapping(T::class.java, adapter)
    }

    inline fun <reified T> count(event: T) {
        val adapter = typeFactory.getAdapterForType(T::class.java)
        adapter.count(event)
        logger.log(event)
    }
}