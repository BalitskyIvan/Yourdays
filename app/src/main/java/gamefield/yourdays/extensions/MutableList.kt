package gamefield.yourdays.extensions

import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.domain.models.EmotionType

fun MutableList<Day>.setEmptyDay(dayNumber: Int, dayInMonth: Int) = set(
    dayNumber, Day(
        number = dayNumber,
        dayInMonth = dayInMonth,
        Emotion(
            worry = 0,
            happiness = 0,
            productivity = 0,
            sadness = 0,
            type = EmotionType.NONE
        )
    )
)