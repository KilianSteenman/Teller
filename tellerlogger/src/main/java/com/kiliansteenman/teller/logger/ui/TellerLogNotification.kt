package com.kiliansteenman.teller.logger.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.TellerLogIntentFactory
import com.kiliansteenman.teller.logger.data.TellerLog

class TellerLogNotification(
    private val context: Context
) {

    companion object {
        private const val CHANNEL_ID = "Teller"
        private const val NOTIFICATION_ID = 7698
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManagerCompat.from(context)
                .createNotificationChannel(
                    NotificationChannel(
                        CHANNEL_ID,
                        "Teller",
                        NotificationManager.IMPORTANCE_LOW
                    )
                )
        }
    }

    fun show(log: TellerLog) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    NOTIFICATION_ID,
                    TellerLogIntentFactory.createIntent(context),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .setLocalOnly(true)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.InboxStyle())
            .setContentTitle("${log.type}: ${log.title}")
            .setContentText(log.content)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
}
