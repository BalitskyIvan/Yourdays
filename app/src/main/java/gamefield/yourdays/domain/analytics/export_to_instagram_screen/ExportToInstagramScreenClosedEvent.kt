package gamefield.yourdays.domain.analytics.export_to_instagram_screen

import gamefield.yourdays.domain.analytics.AnalyticsEvent

class ExportToInstagramScreenClosedEvent : AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "Close"

}
