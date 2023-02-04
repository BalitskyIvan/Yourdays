package gamefield.yourdays.utils.analytics.main_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent

class MainScreenExportToInstagramBtnClickedEvent: AnalyticsEvent {

    override fun getScreenName(): String = "MainScreen"

    override fun getEventName(): String = "ExportToInstagramBtnClicked"

}
