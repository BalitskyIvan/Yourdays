package gamefield.yourdays.utils.analytics.main_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent

class AppClosedEvent: AnalyticsEvent {

    override fun getScreenName(): String = "App"

    override fun getEventName(): String = "Closed"

}
