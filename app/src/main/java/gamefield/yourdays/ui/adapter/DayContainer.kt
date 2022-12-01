package gamefield.yourdays.ui.adapter

import android.view.View
import gamefield.yourdays.data.entity.Emotion

data class DayContainer(
    val view: View,
    val emotion: Emotion,
    val isSelected: Boolean
)
