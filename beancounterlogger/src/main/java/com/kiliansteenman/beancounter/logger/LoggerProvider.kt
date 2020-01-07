package com.kiliansteenman.beancounter.logger

import android.content.Context
import com.kiliansteenman.beancounter.logger.data.RepositoryProvider
import com.kiliansteenman.beancounter.logger.ui.BeanCounterNotification

object LoggerProvider {

    fun createAnalyticsLogger(context: Context): DefaultBeanCounterLogger =
        DefaultBeanCounterLogger(
            RepositoryProvider.getRepository(context),
            BeanCounterNotification(context)
        )
}