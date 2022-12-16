package gamefield.yourdays.extensions

import android.content.Context
import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.ui.adapter.DayContainer
import gamefield.yourdays.ui.customviews.emotions.*

fun Day.getEmotionViewFromDay(context: Context, forceLightenTheme: Boolean = false): DayContainer? = when(emotion?.type) {
    EmotionType.ZERO -> DayContainer(
        view = ZeroEmotionView(context = context, forceLightenTheme = forceLightenTheme).initDay(
            day = this
        ), emotion = emotion!!, isSelected = isSelected, dayNumber = dayInMonth
    )
    EmotionType.PLUS -> DayContainer(
        view = PlusEmotionView(context = context, forceLightenTheme = forceLightenTheme).initDay(
            day = this
        ), emotion = emotion!!, isSelected = isSelected, dayNumber = dayInMonth
    )
    EmotionType.MINUS -> DayContainer(
        view = MinusEmotionView(context = context, forceLightenTheme = forceLightenTheme).initDay(
            day = this
        ), emotion = emotion!!, isSelected = isSelected, dayNumber = dayInMonth
    )
    EmotionType.NONE -> DayContainer(
        view = EmptyEmotionView(context = context, forceLightenTheme = forceLightenTheme),
        emotion = emotion!!,
        isSelected = isSelected, dayNumber = dayInMonth
    )
    else -> null
}

fun EmotionView.parseEmotionInEmotionView(emotion: Emotion) {
    worry = emotion.worry
    happiness = emotion.happiness
    sadness = emotion.sadness
    productivity = emotion.productivity
}

fun EmotionView.initDay(day: Day): EmotionView {
    parseEmotionInEmotionView(day.emotion!!)
    return this
}