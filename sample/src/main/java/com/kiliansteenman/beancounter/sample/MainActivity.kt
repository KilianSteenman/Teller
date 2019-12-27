package com.kiliansteenman.beancounter.sample

import android.app.Application
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

class MainActivity : AppCompatActivity() {

    private lateinit var beanCounter: BeanCounter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.main_send_event).setOnClickListener {
            beanCounter.count(
                SomeAnalyticsFrameworkEvent("click", mapOf("button_press" to "send_event"))
            )
        }

        findViewById<Button>(R.id.main_open_log).setOnClickListener {
            beanCounter.count(
                SomeAnalyticsFrameworkEvent("click", mapOf("button_press" to "open_log"))
            )

            startActivity(BeanCounterIntentFactory.createIntent(this@MainActivity))
        }

        val analyticsFramework = SomeAnalyticsFramework()
        beanCounter = BeanCounter().apply {
            logger = LoggerProvider.createAnalyticsLogger(applicationContext)
            addAdapter(SomeAnalyticsFrameworkAdapter(analyticsFramework))
            addLoggingAdapter(SomeAnalyticsFrameworkLoggingAdapter())
        }
    }
}
