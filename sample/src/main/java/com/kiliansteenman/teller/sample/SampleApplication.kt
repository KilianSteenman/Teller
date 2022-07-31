package com.kiliansteenman.teller.sample

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.soloader.SoLoader
import com.kiliansteenman.teller.MeasurementChain
import com.kiliansteenman.teller.Teller
import com.kiliansteenman.teller.framework.FlipperInterceptor
import com.kiliansteenman.teller.logger.LoggingMeasurementInterceptor
import com.kiliansteenman.teller.sample.adobe.AdobeFramework
import com.kiliansteenman.teller.sample.firebase.FirebaseFramework

class SampleApplication : Application() {

    private val flipperInterceptor = FlipperInterceptor()

    override fun onCreate() {
        super.onCreate()

        // Only required when using the Flipper interceptor (https://github.com/facebook/flipper)
        setupFlipper()

        setupTeller()
    }

    private fun setupFlipper() {
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(flipperInterceptor)
                start()
            }
        }
    }

    private fun setupTeller() {
        val loggingInterceptor = LoggingMeasurementInterceptor(applicationContext)

        Teller.instance.apply {
            addMeasurementChain(
                MeasurementChain.Builder()
                    .addInterceptor(flipperInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build(FirebaseFramework())
            )
            addMeasurementChain(
                MeasurementChain.Builder()
                    .addInterceptor(UserStateInterceptor())
                    .addInterceptor(flipperInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build(AdobeFramework())
            )
        }
    }
}