package gamefield.yourdays.domain.analytics.export_to_instagram_screen

import gamefield.yourdays.domain.analytics.AnalyticsEvent
import gamefield.yourdays.presentation.screen.export_screen.view_model.InstagramStoriesBackgroundColor
import java.io.Serializable

class ExportToInstagramScreenBackgroundColorClickedEvent(
    private val color: InstagramStoriesBackgroundColor
) : AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "BackgroundColorChanged"

    override fun getEventParameters(): Map<String, Serializable> = hashMapOf("COLOR" to color)

}
