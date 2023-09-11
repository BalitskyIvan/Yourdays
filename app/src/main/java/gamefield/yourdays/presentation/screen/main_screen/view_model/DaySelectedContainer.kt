package gamefield.yourdays.presentation.screen.main_screen.view_model

import gamefield.yourdays.data.entity.Emotion

data class DaySelectedContainer(
    val day: Int,
    val month: Int,
    val year: Int,
    val emotion: Emotion?
)
