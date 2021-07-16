package com.kiliansteenman.teller.sample.adobe

import android.util.Log
import com.kiliansteenman.teller.AnalyticsAdapter

internal class AdobeAnalyticsAdapter : AnalyticsAdapter<AdobeEvent> {

    override fun count(event: AdobeEvent) {
        Log.i("Adobe", "Sending event to Adobe ${event.name} with params ${event.data}")

        // Send the event to Adobe.
        // Example code:
        // when(event) {
        //    is AdobeEvent.State -> MobileCore.trackState(event.name, event.data)
        //    is AdobeEvent.Action -> MobileCore.trackAction(event.name, event.data)
        // }
    }
}