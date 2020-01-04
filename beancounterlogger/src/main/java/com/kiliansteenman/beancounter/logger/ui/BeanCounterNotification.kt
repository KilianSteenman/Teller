package com.kiliansteenman.beancounter.logger.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kiliansteenman.beancounter.logger.BeanCounterIntentFactory
import com.kiliansteenman.beancounter.logger.data.AnalyticsLogEvent

class BeanCounterNotification(
    private val context: Context
) {

    companion object {
        private const val CHANNEL_ID = "BeanCounter"
        private const val NOTIFICATION_ID = 7698
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat.from(context)
                .createNotificationChannel(
                    NotificationChannel(
                        CHANNEL_ID,
                        "BeanCounter",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                )
        }
    }

    fun show(logEvent: AnalyticsLogEvent) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    NOTIFICATION_ID,
                    BeanCounterIntentFactory.createIntent(context),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setLocalOnly(true)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.InboxStyle())
            .setContentTitle("${logEvent.type}: ${logEvent.title}")
            .setContentText(logEvent.content)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
}