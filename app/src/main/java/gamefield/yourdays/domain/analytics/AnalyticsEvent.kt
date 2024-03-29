package gamefield.yourdays.domain.analytics

import java.io.Serializable

interface AnalyticsEvent {

    fun getScreenName(): String

    fun getEventName(): String

    fun getEventParameters(): Map<String, Serializable>? = null

}
