package gamefield.yourdays.extensions

import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.domain.models.EmotionType

fun MutableList<Day>.setEmptyDay(dayNumber: Int, dayInMonth: Int) = set(
    dayNumber, Day(
        number = dayNumber,
        dayInMonth = dayInMonth,
        Emotion(
            anxiety = 0,
            joy = 0,
            calmness = 0,
            sadness = 0,
            type = EmotionType.NONE
        )
    )
)