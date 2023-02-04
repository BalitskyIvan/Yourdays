package gamefield.yourdays.utils.analytics.export_to_instagram_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent
import java.io.Serializable

class ExportToInstagramScreenDaySelectorClickedEvent(
    private val pickedDay: String
): AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "DayPicked"

    override fun getEventParameters(): Map<String, Serializable> = hashMapOf("PICKED_DAY" to pickedDay)

}
