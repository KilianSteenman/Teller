package com.kiliansteenman.teller.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kiliansteenman.teller.Teller
import com.kiliansteenman.teller.logger.TellerLogIntentFactory
import com.kiliansteenman.teller.logger.LoggerProvider
import com.kiliansteenman.teller.sample.adobe.AdobeAnalyticsAdapter
import com.kiliansteenman.teller.sample.adobe.AdobeAnalyticsLoggingAdapter
import com.kiliansteenman.teller.sample.adobe.AdobeEvent
import com.kiliansteenman.teller.sample.databinding.ActivityMainBinding
import com.kiliansteenman.teller.sample.firebase.FirebaseAnalyticsAdapter
import com.kiliansteenman.teller.sample.firebase.FirebaseAnalyticsLoggingAdapter
import com.kiliansteenman.teller.sample.firebase.FirebaseEvent

class MainActivity : AppCompatActivity() {

    private lateinit var teller: Teller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindButtons(binding)

        setupTeller()
    }

    private fun bindButtons(binding: ActivityMainBinding) {
        binding.sendFirebaseEventButton.setOnClickListener {
            teller.count(FirebaseEvent("click", mapOf("button_press" to "send_firebase_event")))
        }

        binding.sendAdobeEventButton.setOnClickListener {
            teller.count(AdobeEvent.Action("pageview", mapOf("button_press" to "send_adobe_event")))
        }

        binding.sendUnregisteredEventButton.setOnClickListener {
            teller.count(UnregisteredEvent("Crash!", "data"))
        }

        binding.openLogButton.setOnClickListener {
            teller.count(FirebaseEvent("click", mapOf("button_press" to "open_log")))

            startActivity(TellerLogIntentFactory.createIntent(this@MainActivity))
        }
    }

    private fun setupTeller() {
        val loggerAdapter = LoggerProvider.createAnalyticsLogger(applicationContext).apply {
            addMapping(FirebaseAnalyticsLoggingAdapter())
            addMapping(AdobeAnalyticsLoggingAdapter())
        }

        teller = Teller.instance.apply {
            logger = loggerAdapter
            addAdapter(FirebaseAnalyticsAdapter())
            addAdapter(AdobeAnalyticsAdapter())
        }
    }
}
