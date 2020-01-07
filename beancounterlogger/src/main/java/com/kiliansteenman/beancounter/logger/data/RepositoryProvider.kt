package com.kiliansteenman.beancounter.logger.data

import android.content.Context
import com.kiliansteenman.beancounter.logger.data.room.RoomAnalyticsLogRepository

internal object RepositoryProvider {

    private var repository: AnalyticsLogRepository? = null

    fun getRepository(context: Context): AnalyticsLogRepository {
        val repo = repository
        if (repo == null) {
            val repo = RoomAnalyticsLogRepository(context)
            repository = repo
            return repo
        } else {
            return repo
        }
    }
}