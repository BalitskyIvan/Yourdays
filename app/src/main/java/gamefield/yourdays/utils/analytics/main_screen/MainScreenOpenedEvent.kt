package gamefield.yourdays.utils.analytics.main_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent

class MainScreenOpenedEvent: AnalyticsEvent {

    override fun getScreenName(): String = "MainScreen"

    override fun getEventName(): String = "Open"

}