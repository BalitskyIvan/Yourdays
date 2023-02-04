package gamefield.yourdays.utils.analytics.export_to_instagram_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent

class ExportToInstagramScreenOpenedEvent: AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "Open"

}
