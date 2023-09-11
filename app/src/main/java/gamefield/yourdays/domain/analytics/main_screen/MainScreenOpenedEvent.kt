package gamefield.yourdays.domain.analytics.main_screen

import gamefield.yourdays.domain.analytics.AnalyticsEvent

class MainScreenOpenedEvent : AnalyticsEvent {

    override fun getScreenName(): String = "MainScreen"

    override fun getEventName(): String = "Open"

}
