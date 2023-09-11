package gamefield.yourdays.extensions

import gamefield.yourdays.data.entity.Emotion

fun Emotion.isEmotionNotFilled(): Boolean = worry == 0 &&
        happiness == 0 &&
        sadness == 0 &&
        productivity == 0
