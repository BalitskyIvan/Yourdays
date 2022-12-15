package gamefield.yourdays.data.entity

import gamefield.yourdays.domain.models.EmotionType

data class Emotion(
    val worry: Int,
    val happiness: Int,
    val sadness: Int,
    val productivity: Int,
    val type: EmotionType
)
