package com.kiliansteenman.beancounter.logger.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnalyticsLogEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "framework") val framework: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "logDate") val logDate: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String
)