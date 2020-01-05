package com.kiliansteenman.beancounter.logger.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent

@Dao
interface AnalyticsLogEventDao {

    @Query("SELECT * FROM analyticsLogEvent ORDER BY logDate DESC")
    fun getAll(): LiveData<List<AnalyticsLogEvent>>

    @Insert
    fun insertAll(vararg events: AnalyticsLogEvent)

    @Delete
    fun delete(event: AnalyticsLogEvent)

    @Query("SELECT * FROM analyticsLogEvent WHERE id=:logId")
    fun get(logId: Long): AnalyticsLogEvent

    @Query("SELECT DISTINCT framework FROM analyticslogevent")
    fun getFrameWorks(): List<String>

    @Query("DELETE FROM analyticslogevent")
    fun clearAll()
}