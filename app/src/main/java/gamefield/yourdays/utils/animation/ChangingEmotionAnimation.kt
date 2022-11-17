package gamefield.yourdays.utils.animation

import androidx.lifecycle.MutableLiveData
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.getNextEmotion
import kotlinx.coroutines.*

class ChangingEmotionAnimation(
    private val viewModelScope: CoroutineScope,
    private val emotionContainerAlpha: MutableLiveData<Float>,
    private val currentEmotionType: MutableLiveData<EmotionType>,
) : Animation {

    @Volatile
    var isAnimationActive: Boolean = true

    @Volatile
    private var alphaState = AlphaState.INCREASE

    override fun start() {
        isAnimationActive = true
        viewModelScope.launch(Dispatchers.Default) {
            while (isAnimationActive) {
                if (alphaState == AlphaState.INCREASE) {
                    val alpha = emotionContainerAlpha.value
                    if (alpha != null && alpha + ALPHA_SPEED > 1) {
                        alphaState = AlphaState.DECREASE
                        emotionContainerAlpha.postValue(1f)
                    } else if (alpha != null) {
                        emotionContainerAlpha.postValue(alpha + ALPHA_SPEED)
                    }
                } else {
                    val alpha = emotionContainerAlpha.value
                    if (alpha != null && alpha - ALPHA_SPEED < 0.1f) {
                        alphaState = AlphaState.INCREASE
                        currentEmotionType.postValue(currentEmotionType.value?.getNextEmotion())
                        emotionContainerAlpha.postValue(0.1f)
                    } else if (alpha != null) {
                        emotionContainerAlpha.postValue(alpha - ALPHA_SPEED)
                    }
                }
                delay(40)
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
