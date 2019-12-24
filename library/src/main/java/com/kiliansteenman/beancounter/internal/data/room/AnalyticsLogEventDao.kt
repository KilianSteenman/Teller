package com.kiliansteenman.beancounter.internal.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent

@Dao
interface AnalyticsLogEventDao {

    @Query("SELECT * FROM analyticsLogEvent")
    fun getAll(): List<AnalyticsLogEvent>

    @Insert
    fun insertAll(vararg events: AnalyticsLogEvent)

    @Delete
    fun delete(event: AnalyticsLogEvent)
}