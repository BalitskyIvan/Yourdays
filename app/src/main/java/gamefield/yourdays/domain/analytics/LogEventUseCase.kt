package gamefield.yourdays.domain.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class LogEventUseCase(private val analytics: FirebaseAnalytics) {

    operator fun invoke(event: AnalyticsEvent) {
        val eventName = "${event.getScreenName()}_${event.getEventName()}"
        val bundle = Bundle().apply {
            event.getEventParameters()?.forEach { params ->
                putSerializable(params.key, params.value)
            }
        }
        analytics.logEvent(eventName, bundle)
    }

}
