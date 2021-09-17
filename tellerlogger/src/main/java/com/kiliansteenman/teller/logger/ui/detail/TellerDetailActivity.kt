package com.kiliansteenman.teller.logger.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.kiliansteenman.teller.logger.R
import com.kiliansteenman.teller.logger.data.TellerLog
import com.kiliansteenman.teller.logger.data.room.TellerLogDatabase
import com.kiliansteenman.teller.logger.formatTimeStamp
import java.util.concurrent.Executors.newSingleThreadExecutor

internal class TellerDetailActivity : AppCompatActivity(R.layout.teller_detail) {

    private val logId: Long
        get() = intent.getLongExtra(PARAM_LOG_ID, -1)

    private val db: TellerLogDatabase
        get() {
            return Room.databaseBuilder(
                applicationContext,
                TellerLogDatabase::class.java, "database-name"
            ).build()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Toolbar>(R.id.detail_toolbar).setNavigationOnClickListener { onBackPressed() }

        newSingleThreadExecutor().execute {
            val log = db.tellerLogDao().get(logId)
            runOnUiThread {
                onLogLoaded(log)
            }
        }
    }

    private fun onLogLoaded(log: TellerLog) {
        findViewById<TextView>(R.id.detail_title).text = log.title
        findViewById<TextView>(R.id.detail_timestamp).text = log.logDate.formatTimeStamp()
        findViewById<TextView>(R.id.detail_type).text = log.type
        findViewById<TextView>(R.id.detail_content).text = log.content
    }

    companion object {

        private const val PARAM_LOG_ID = "PARAM_LOG_ID"

        fun createIntent(context: Context, logId: Long): Intent {
            return Intent(context, TellerDetailActivity::class.java).apply {
                putExtra(PARAM_LOG_ID, logId)
            }
        }
    }
}
