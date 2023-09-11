package gamefield.yourdays.domain.analytics.main_screen

import gamefield.yourdays.domain.analytics.AnalyticsEvent
import java.io.Serializable

class MainScreenEmotionClickedEvent(private val isEmotionFilled: Boolean) : AnalyticsEvent {

    override fun getScreenName(): String = "MainScreen"

    override fun getEventName(): String = "EmotionClicked"

    override fun getEventParameters(): Map<String, Serializable> =
        hashMapOf("IS_EMOTION_FILLED" to isEmotionFilled)

}
