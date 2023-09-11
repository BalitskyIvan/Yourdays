package gamefield.yourdays.domain.analytics.main_screen

import gamefield.yourdays.domain.analytics.AnalyticsEvent

class AppClosedEvent : AnalyticsEvent {

    override fun getScreenName(): String = "App"

    override fun getEventName(): String = "Closed"

}
