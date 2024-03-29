package com.kiliansteenman.teller.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiliansteenman.teller.Teller
import com.kiliansteenman.teller.logger.LoggerProvider
import com.kiliansteenman.teller.logger.TellerLogIntentFactory
import com.kiliansteenman.teller.sample.adobe.AdobeAction
import com.kiliansteenman.teller.sample.adobe.AdobeActionAnalyticsAdapter
import com.kiliansteenman.teller.sample.adobe.AdobeActionLoggingAdapter
import com.kiliansteenman.teller.sample.adobe.AdobeState
import com.kiliansteenman.teller.sample.adobe.AdobeStateAnalyticsAdapter
import com.kiliansteenman.teller.sample.adobe.AdobeStateLoggingAdapter
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

        binding.sendAdobeActionButton.setOnClickListener {
            teller.count(
                AdobeAction(
                    name = "click",
                    data = mapOf(
                        "button_press" to "send_adobe_event",
                        "user_state" to "logged_out",
                        "is_experiment_active" to "true",
                        "money_on_the_bank" to "12,50",
                        "notification_enabled" to "false",
                        "active_experiments" to "EXPERIMENT_1,EXPERIMENT_2,EXPERIMENT_7,EXPERIMENT_8,EXPERIMENT_9,EXPERIMENT_27,EXPERIMENT_200"
                    )
                )
            )
        }

        binding.sendAdobeStateButton.setOnClickListener {
            teller.count(AdobeState("MainActivity"))
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
            addMapping(AdobeStateLoggingAdapter())
            addMapping(AdobeActionLoggingAdapter())
        }

        teller = Teller.instance.apply {
            logger = loggerAdapter
            addAdapter(FirebaseAnalyticsAdapter())
            addAdapter(AdobeStateAnalyticsAdapter())
            addAdapter(AdobeActionAnalyticsAdapter())
        }
    }
}
