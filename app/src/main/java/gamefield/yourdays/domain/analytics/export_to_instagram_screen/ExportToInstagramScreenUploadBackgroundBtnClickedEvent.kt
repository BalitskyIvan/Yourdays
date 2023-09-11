package gamefield.yourdays.domain.analytics.export_to_instagram_screen

import gamefield.yourdays.domain.analytics.AnalyticsEvent

class ExportToInstagramScreenUploadBackgroundBtnClickedEvent : AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "UploadBackgroundBtnClicked"

}
