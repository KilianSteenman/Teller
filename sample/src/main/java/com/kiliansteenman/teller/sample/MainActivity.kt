package com.kiliansteenman.teller.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiliansteenman.teller.Measurement
import com.kiliansteenman.teller.Teller
import com.kiliansteenman.teller.logger.TellerLogIntentFactory
import com.kiliansteenman.teller.sample.adobe.AdobeFramework
import com.kiliansteenman.teller.sample.databinding.ActivityMainBinding
import com.kiliansteenman.teller.sample.firebase.FirebaseFramework

class MainActivity : AppCompatActivity() {

    private lateinit var teller: Teller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindButtons(binding)

        teller = Teller.instance
    }

    private fun bindButtons(binding: ActivityMainBinding) {
        binding.sendFirebaseEventButton.setOnClickListener {
            teller.count(
                Measurement.Builder(FirebaseFramework.NAME, "ScreenView")
                    .setName("click")
                    .addParam("button_press" to "send_firebase_event")
                    .build()
            )
        }

        binding.sendAdobeActionButton.setOnClickListener {
            teller.count(
                Measurement.Builder(AdobeFramework.NAME, "Action")
                    .setName("click")
                    .addParams(mapOf(
                        "button_press" to "send_adobe_event",
                        "user_state" to "logged_out",
                        "is_experiment_active" to "true",
                        "money_on_the_bank" to "12,50",
                        "notification_enabled" to "false",
                        "active_experiments" to "EXPERIMENT_1,EXPERIMENT_2,EXPERIMENT_7,EXPERIMENT_8,EXPERIMENT_9,EXPERIMENT_27,EXPERIMENT_200"
                    ))
                    .build()
            )
        }

        binding.sendAdobeStateButton.setOnClickListener {
            teller.count(
                Measurement.Builder(AdobeFramework.NAME, "State")
                    .setName("MainActivity")
                    .build()
            )
        }

        binding.openLogButton.setOnClickListener {
            teller.count(
                Measurement.Builder(FirebaseFramework.NAME, "Action")
                    .setName("click")
                    .addParam("button_press" to "open_log")
                    .build()
            )

            startActivity(TellerLogIntentFactory.createIntent(this@MainActivity))
        }
    }
}
