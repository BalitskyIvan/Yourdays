package gamefield.yourdays.presentation.components.adapter

import android.view.View
import gamefield.yourdays.data.entity.Emotion

data class DayContainer(
    val view: View,
    val dayNumber: Int,
    val emotion: Emotion,
    val isSelected: Boolean
)
