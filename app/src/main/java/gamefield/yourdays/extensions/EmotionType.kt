package gamefield.yourdays.extensions

import gamefield.yourdays.domain.models.EmotionType

fun EmotionType.getNextEmotion(): EmotionType = when (this) {
    EmotionType.PLUS -> EmotionType.ZERO
    EmotionType.ZERO -> EmotionType.MINUS
    EmotionType.MINUS -> EmotionType.PLUS
    EmotionType.NONE -> EmotionType.NONE
}