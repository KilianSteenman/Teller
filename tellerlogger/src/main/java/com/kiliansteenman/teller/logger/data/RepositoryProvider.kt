package com.kiliansteenman.teller.logger.data

import android.content.Context
import com.kiliansteenman.teller.logger.data.room.RoomTellerLogRepository

internal object RepositoryProvider {

    private var repository: TellerLogRepository? = null

    fun getRepository(context: Context): TellerLogRepository {
        val repo = repository
        return if (repo == null) {
            val repo = RoomTellerLogRepository(context)
            repository = repo
            repo
        } else {
            repo
        }
    }
}