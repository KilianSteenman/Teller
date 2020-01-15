package com.kiliansteenman.teller.logger.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kiliansteenman.teller.logger.data.TellerLog

@Database(entities = [TellerLog::class], version = 3)
abstract class TellerLogDatabase : RoomDatabase() {
    abstract fun tellerLogDao(): TellerLogDao
}