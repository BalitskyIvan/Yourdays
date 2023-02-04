package gamefield.yourdays.utils.analytics.export_to_instagram_screen

import gamefield.yourdays.utils.analytics.AnalyticsEvent
import gamefield.yourdays.utils.export_screen.InstagramStoriesBackgroundColor
import java.io.Serializable

class ExportToInstagramScreenBackgroundColorClickedEvent(
    private val color: InstagramStoriesBackgroundColor
): AnalyticsEvent {

    override fun getScreenName(): String = "ExportScreen"

    override fun getEventName(): String = "BackgroundColorChanged"

    override fun getEventParameters(): Map<String, Serializable> = hashMapOf("COLOR" to color)

}
