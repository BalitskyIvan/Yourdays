package gamefield.yourdays.utils.analytics.main_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent
import java.io.Serializable

class MainScreenExportToInstagramByDayBtnClickedEvent(
    private val isFilled: Boolean
) : AnalyticsEvent {

    override fun getScreenName(): String = "MainScreen"

    override fun getEventName(): String = "ExportToInstagramByDay"

    override fun getEventParameters(): Map<String, Serializable> = hashMapOf("IS_FILLED" to isFilled)

}