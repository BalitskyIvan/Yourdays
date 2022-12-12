package gamefield.yourdays.data.entity

data class Day(
    val number: Int,
    val dayInMonth: Int,
    var emotion: Emotion? = null,
    var isSelected: Boolean = false
)
