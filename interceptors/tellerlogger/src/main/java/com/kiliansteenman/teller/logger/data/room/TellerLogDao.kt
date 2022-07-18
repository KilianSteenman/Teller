package com.kiliansteenman.teller.logger.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kiliansteenman.teller.logger.data.TellerLog
import kotlinx.coroutines.flow.Flow

@Dao
interface TellerLogDao {

    @Query("SELECT * FROM tellerlog ORDER BY logDate DESC")
    fun getAll(): Flow<List<TellerLog>>

    @Query("SELECT * FROM tellerlog WHERE params LIKE '%' || :query || '%' ORDER BY logDate DESC")
    fun search(query: String?): Flow<List<TellerLog>>

    @Insert
    suspend fun insert(event: TellerLog): Long

    @Delete
    fun delete(event: TellerLog)

    @Query("SELECT * FROM tellerlog WHERE id=:logId")
    fun get(logId: Long): TellerLog

    @Query("SELECT DISTINCT framework FROM tellerlog")
    fun getFrameWorks(): List<String>

    @Query("DELETE FROM tellerlog")
    fun clearAll()
}
