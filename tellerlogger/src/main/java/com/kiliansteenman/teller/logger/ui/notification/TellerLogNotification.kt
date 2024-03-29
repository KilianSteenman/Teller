package com.kiliansteenman.teller.logger.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.collection.LongSparseArray
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.kiliansteenman.teller.logger.ClearLogActionBroadcastReceiver
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.TellerLogIntentFactory
import com.kiliansteenman.teller.logger.data.TellerLog

class TellerLogNotification(
    private val context: Context
) {

    companion object {
        private const val CHANNEL_ID = "Teller"
        private const val NOTIFICATION_ID = 7698

        private const val MAX_BUFFER_SIZE = 10
        private const val CLEAR_INTENT_REQUEST_CODE = 11

        private val eventBuffer = LongSparseArray<TellerLog>()
        private val eventIdsSet = HashSet<Long>()

        fun clearBuffer() {
            synchronized(eventBuffer) {
                eventBuffer.clear()
                eventIdsSet.clear()
            }
        }

        private fun TellerLog.formatAsNotification() = "$framework - ${this.type}: $title"
    }

    private val notificationManager = NotificationManagerCompat.from(context)

    private val immutableFlag: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            0
        }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "Teller",
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }
    }

    fun show(log: TellerLog) {
        addToBuffer(log)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(createContentIntent())
            .setLocalOnly(true)
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setContentTitle("Telling analytics")
            .setContentText(log.formatAsNotification())
            .addAction(createClearAction())

        val inboxStyle = NotificationCompat.InboxStyle()

        synchronized(eventBuffer) {
            for (i in eventBuffer.size() - 1 downTo 0) {
                val bufferedEvent = eventBuffer.valueAt(i)
                if (bufferedEvent != null && i < MAX_BUFFER_SIZE) {
                    inboxStyle.addLine(bufferedEvent.formatAsNotification())
                }
            }

            builder.setStyle(inboxStyle)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setSubText(eventIdsSet.size.toString())
            } else {
                builder.setNumber(eventIdsSet.size)
            }
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun dismissNotifications() {
        notificationManager.cancel(NOTIFICATION_ID)
    }

    private fun addToBuffer(log: TellerLog) {
        synchronized(eventBuffer) {
            eventIdsSet.add(log.id)
            eventBuffer.put(log.id, log)

            if (eventBuffer.size() > MAX_BUFFER_SIZE) {
                eventBuffer.removeAt(0)
            }
        }
    }

    private fun createContentIntent() = PendingIntent.getActivity(
        context,
        NOTIFICATION_ID,
        TellerLogIntentFactory.createIntent(context),
        PendingIntent.FLAG_UPDATE_CURRENT or immutableFlag,
    )

    private fun createClearAction(): NotificationCompat.Action {
        val clearLogBroadcastIntent = Intent(context, ClearLogActionBroadcastReceiver::class.java)
        val pendingBroadcastIntent = PendingIntent.getBroadcast(
            context,
            CLEAR_INTENT_REQUEST_CODE,
            clearLogBroadcastIntent,
            PendingIntent.FLAG_ONE_SHOT or immutableFlag
        )
        return NotificationCompat.Action(
            R.drawable.ic_delete_white,
            context.getString(R.string.notification_action_clear),
            pendingBroadcastIntent
        )
    }
}
