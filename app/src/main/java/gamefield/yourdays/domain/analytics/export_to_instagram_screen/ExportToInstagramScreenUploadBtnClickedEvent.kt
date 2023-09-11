package gamefield.yourdays.domain.analytics.export_to_instagram_screen

import gamefield.yourdays.domain.analytics.AnalyticsEvent
import gamefield.yourdays.presentation.screen.export_screen.view_model.DatePickerType
import java.io.Serializable

class ExportToInstagramScreenUploadBtnClickedEvent(
    private val previewType: DatePickerType
) : AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "UploadBtnClicked"

    override fun getEventParameters(): Map<String, Serializable> =
        hashMapOf("PREVIEW_TYPE" to previewType)

}
