package gamefield.yourdays.utils.main_screen

import gamefield.yourdays.data.entity.Emotion

data class DaySelectedContainer(
    val day: Int,
    val month: Int,
    val emotion: Emotion?
)
