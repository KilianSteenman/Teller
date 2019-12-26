package com.kiliansteenman.beancounter.internal.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.kiliansteenman.beancounter.R
import com.kiliansteenman.beancounter.internal.data.AnalyticsLogEvent
import com.kiliansteenman.beancounter.internal.data.room.BeanCounterDatabase
import java.util.concurrent.Executors.newSingleThreadExecutor

class DetailActivity : AppCompatActivity(R.layout.beancounter_detail) {

    private val logId: Long
        get() = intent.getLongExtra(PARAM_LOG_ID, -1)

    private val db: BeanCounterDatabase
        get() {
            return Room.databaseBuilder(
                applicationContext,
                BeanCounterDatabase::class.java, "database-name"
            ).build()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newSingleThreadExecutor().execute {
            val log = db.analyticsLogEventDao().get(logId)
            runOnUiThread {
                onLogLoaded(log)
            }
        }
    }

    private fun onLogLoaded(log: AnalyticsLogEvent) {
        findViewById<TextView>(R.id.detail_title).text = log.title
        findViewById<TextView>(R.id.detail_timestamp).text = log.logDate.toString()
        findViewById<TextView>(R.id.detail_type).text = log.type
        findViewById<TextView>(R.id.detail_content).text = log.content
    }

    companion object {

        private const val PARAM_LOG_ID = "PARAM_LOG_ID"

        fun createIntent(context: Context, logId: Long): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(PARAM_LOG_ID, logId)
            }
        }
    }
}