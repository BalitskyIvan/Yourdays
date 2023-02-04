package gamefield.yourdays.utils.analytics.export_to_instagram_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent
import java.io.Serializable

class ExportToInstagramScreenMonthSelectorChangedEvent(
    private val pickedMonth: String,
    private val pickedYear: String
): AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "MonthSelectorChanged"

    override fun getEventParameters(): Map<String, Serializable> = hashMapOf(
        "PICKED_MONTH" to pickedMonth,
        "PICKED_YEAR" to pickedYear
    )

}
