package com.kiliansteenman.teller.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kiliansteenman.teller.Teller
import com.kiliansteenman.teller.logger.TellerLogIntentFactory
import com.kiliansteenman.teller.logger.LoggerProvider
import com.kiliansteenman.teller.sample.analytics.SomeAnalyticsFramework
import com.kiliansteenman.teller.sample.analytics.SomeAnalyticsFrameworkAdapter
import com.kiliansteenman.teller.sample.analytics.SomeAnalyticsFrameworkEvent
import com.kiliansteenman.teller.sample.analytics.SomeAnalyticsFrameworkLoggingAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var teller: Teller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.main_send_event).setOnClickListener {
            teller.count(
                SomeAnalyticsFrameworkEvent("click", mapOf("button_press" to "send_event"))
            )
        }

        findViewById<Button>(R.id.main_send_not_registered_event).setOnClickListener {
            teller.count(NotRegisteredEvent("Crash!", "data"))
        }

        findViewById<Button>(R.id.main_open_log).setOnClickListener {
            teller.count(
                SomeAnalyticsFrameworkEvent("click", mapOf("button_press" to "open_log"))
            )

            startActivity(TellerLogIntentFactory.createIntent(this@MainActivity))
        }

        val analyticsFramework = SomeAnalyticsFramework()

        val loggerAdapter = LoggerProvider.createAnalyticsLogger(applicationContext).apply {
            addMapping(SomeAnalyticsFrameworkLoggingAdapter())
        }
        teller = Teller().apply {
            logger = loggerAdapter
            addAdapter(SomeAnalyticsFrameworkAdapter(analyticsFramework))
        }
    }
}
