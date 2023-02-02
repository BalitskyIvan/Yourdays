package gamefield.yourdays.utils.analytics.main_screen

import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.utils.analytics.AnalyticsEvent
import java.io.Serializable

class MainScreenOkButtonClicked(
    private val emotion: Emotion?,
    private val isFilled: Boolean
    ) : AnalyticsEvent {

    override fun getScreenName(): String = "MainScreen"

    override fun getEventName(): String = "OkButton_Clicked"

    override fun getEventParameters(): Map<String, Serializable> {
        val parameters = hashMapOf<String, Serializable>("IS_FILLED" to isFilled)
        if (emotion != null) {
            parameters["WORRY"] = emotion.worry
            parameters["SADNESS"] = emotion.sadness
            parameters["HAPPINESS"] = emotion.happiness
            parameters["PRODUCTIVITY"] = emotion.productivity
            parameters["EMOTION_TYPE"] = emotion.type
        }
        return parameters
    }

}
