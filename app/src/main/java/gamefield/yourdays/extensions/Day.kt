package gamefield.yourdays.extensions

import android.content.Context
import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.ui.adapter.DayContainer
import gamefield.yourdays.ui.customviews.emotions.*

fun Day.getEmotionViewFromDay(context: Context): DayContainer? = when(emotion?.type) {
    EmotionType.ZERO -> DayContainer(
        view = ZeroEmotionView(context = context).initDay(
            day = this
        ), emotion = emotion!!, isSelected = isSelected
    )
    EmotionType.PLUS -> DayContainer(
        view = PlusEmotionView(context = context).initDay(
            day = this
        ), emotion = emotion!!, isSelected = isSelected
    )
    EmotionType.MINUS -> DayContainer(
        view = MinusEmotionView(context = context).initDay(
            day = this
        ), emotion = emotion!!, isSelected = isSelected
    )
    EmotionType.NONE -> DayContainer(
        view = EmptyEmotionView(context = context),
        emotion = emotion!!,
        isSelected = isSelected
    )
    else -> null
}

private fun EmotionView.parseEmotionInEmotionView(emotion: Emotion) {
    anxiety = emotion.anxiety
    joy = emotion.joy
    sadness = emotion.sadness
    calmness = emotion.calmness
}

fun EmotionView.initDay(day: Day): EmotionView {
    parseEmotionInEmotionView(day.emotion!!)
    return this
}