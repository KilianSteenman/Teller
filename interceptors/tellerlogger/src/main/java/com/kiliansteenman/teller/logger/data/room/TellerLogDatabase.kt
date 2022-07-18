package com.kiliansteenman.teller.logger.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.kiliansteenman.teller.logger.data.TellerLog

@Database(
    entities = [TellerLog::class],
    version = 4,
)
@TypeConverters(MapConverter::class)
abstract class TellerLogDatabase : RoomDatabase() {
    abstract fun tellerLogDao(): TellerLogDao
}