package com.kiliansteenman.beancounter.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kiliansteenman.beancounter.BeanCounter
import com.kiliansteenman.beancounter.BeanCounterIntentFactory
import com.kiliansteenman.beancounter.internal.LoggerProvider
import com.kiliansteenman.beancounter.sample.analytics.SomeAnalyticsFramework
import com.kiliansteenman.beancounter.sample.analytics.SomeAnalyticsFrameworkAdapter
import com.kiliansteenman.beancounter.sample.analytics.SomeAnalyticsFrameworkEvent
import com.kiliansteenman.beancounter.sample.analytics.SomeAnalyticsFrameworkLoggingAdapter

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var beanCounter: BeanCounter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.main_send_event).setOnClickListener {
            beanCounter.count(
                SomeAnalyticsFrameworkEvent("click", mapOf("button_press" to "send_event"))
            )
        }

        findViewById<Button>(R.id.main_send_not_registered_event).setOnClickListener {
            beanCounter.count(NotRegisteredEvent("Crash!", "data"))
        }

        findViewById<Button>(R.id.main_open_log).setOnClickListener {
            beanCounter.count(
                SomeAnalyticsFrameworkEvent("click", mapOf("button_press" to "open_log"))
            )

            startActivity(BeanCounterIntentFactory.createIntent(this@MainActivity))
        }

        val analyticsFramework = SomeAnalyticsFramework()

        val loggerAdapter = LoggerProvider.createAnalyticsLogger(applicationContext).apply {
            addMapping(SomeAnalyticsFrameworkEvent::class, SomeAnalyticsFrameworkLoggingAdapter())
        }
        beanCounter = BeanCounter().apply {
            logger = loggerAdapter
            addAdapter(
                SomeAnalyticsFrameworkAdapter(analyticsFramework),
                SomeAnalyticsFrameworkEvent::class
            )
        }
    }
}
