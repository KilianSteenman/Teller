package com.kiliansteenman.teller.sample.adobe

import android.util.Log
import com.kiliansteenman.teller.AnalyticsAdapter

internal class AdobeStateAnalyticsAdapter : AnalyticsAdapter<AdobeState> {

    override fun count(event: AdobeState) {
        Log.i("Adobe", "Sending state event to Adobe ${event.name} with params ${event.data}")

        // Send the event to Adobe.
        // Example code:
        // MobileCore.trackState(event.name, event.data)
    }
}

internal class AdobeActionAnalyticsAdapter: AnalyticsAdapter<AdobeAction> {

    override fun count(event: AdobeAction) {
        Log.i("Adobe", "Sending action event to Adobe ${event.name} with params ${event.data}")

        // Send the event to Adobe.
        // Example code:
        // MobileCore.trackAction(event.name, event.data)
    }
}