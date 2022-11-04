package gamefield.yourdays.data.entity

import gamefield.yourdays.domain.models.EmotionType

data class Emotion(
    val anxiety: Int,
    val joy: Int,
    val sadness: Int,
    val calmness: Int,
    val type: EmotionType
)
