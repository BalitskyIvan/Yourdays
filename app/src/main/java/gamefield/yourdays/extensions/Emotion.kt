package gamefield.yourdays.extensions

import gamefield.yourdays.data.entity.Emotion

fun Emotion.isEmotionNotFilled(): Boolean = anxiety == 0 && joy == 0 &&
        sadness == 0 && calmness == 0