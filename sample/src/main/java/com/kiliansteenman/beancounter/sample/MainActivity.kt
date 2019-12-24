package com.kiliansteenman.beancounter.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.kiliansteenman.beancounter.BeanCounter
import com.kiliansteenman.beancounter.internal.ui.BeanCounterActivity
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
            beanCounter.count(SomeAnalyticsFrameworkEvent("Superduper event"))
        }

        findViewById<Button>(R.id.main_open_log).setOnClickListener {
            startActivity(Intent(this@MainActivity, BeanCounterActivity::class.java))
        }

        val analyticsFramework = SomeAnalyticsFramework()
        beanCounter = BeanCounter(applicationContext).apply {
            addAdapter(SomeAnalyticsFrameworkAdapter(analyticsFramework))
            addLoggingAdapter(SomeAnalyticsFrameworkLoggingAdapter())
        }
    }
}
