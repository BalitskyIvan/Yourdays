package gamefield.yourdays.utils.animation

import androidx.lifecycle.MutableLiveData
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.getNextEmotion
import kotlinx.coroutines.*

class ChangingEmotionAnimation(
    private val viewModelScope: CoroutineScope,
    private val emotionContainerAlphaChangedEvent: MutableLiveData<Float>,
    private val currentEmotionTypeChangedEvent: MutableLiveData<EmotionType>,
) : Animation {

    private var emotionContainerAlpha: Float = 0f
    private var currentEmotionType: EmotionType = EmotionType.PLUS

    @Volatile
    var isAnimationActive: Boolean = false

    @Volatile
    private var alphaState = AlphaState.INCREASE

    override fun start() {
        currentEmotionType = currentEmotionType.getNextEmotion()
        currentEmotionTypeChangedEvent.postValue(currentEmotionType)
        emotionContainerAlpha = 0f
        emotionContainerAlphaChangedEvent.postValue(emotionContainerAlpha)
        alphaState = AlphaState.INCREASE

        if (!isAnimationActive) {
            isAnimationActive = true
            viewModelScope.launch(Dispatchers.Default) {
                while (isAnimationActive) {
                    if (alphaState == AlphaState.INCREASE) {
                        if (emotionContainerAlpha + ALPHA_SPEED > 1) {
                            alphaState = AlphaState.DECREASE
                            emotionContainerAlpha = 1f
                            emotionContainerAlphaChangedEvent.postValue(emotionContainerAlpha)
                        } else {
                            emotionContainerAlpha += ALPHA_SPEED
                            emotionContainerAlphaChangedEvent.postValue(emotionContainerAlpha)
                        }
                    } else {
                        if (emotionContainerAlpha - ALPHA_SPEED < 0.1f) {
                            alphaState = AlphaState.INCREASE
                            currentEmotionType = currentEmotionType.getNextEmotion()
                            currentEmotionTypeChangedEvent.postValue(currentEmotionType)
                            emotionContainerAlpha = 0.1f
                            emotionContainerAlphaChangedEvent.postValue(emotionContainerAlpha)
                        } else {
                            emotionContainerAlpha -= ALPHA_SPEED
                            emotionContainerAlphaChangedEvent.postValue(emotionContainerAlpha)
                        }
                    }
                    delay(40)
                }
                emotionContainerAlpha = 1f
                emotionContainerAlphaChangedEvent.postValue(emotionContainerAlpha)
                alphaState = AlphaState.INCREASE
            }
        }
    }

    private companion object {
        const val ALPHA_SPEED: Float = 0.03f
    }

    private enum class AlphaState {
        INCREASE,
        DECREASE
    }
}
