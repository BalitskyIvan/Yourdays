package gamefield.yourdays.utils.animation

import android.animation.ValueAnimator
import androidx.lifecycle.MutableLiveData
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.getNextEmotion
import kotlinx.coroutines.*

class ChangingEmotionAnimation(
    private val viewModelScope: CoroutineScope,
    private val emotionContainerAlphaChangedEvent: MutableLiveData<Float>,
    private val currentEmotionTypeChangedEvent: MutableLiveData<EmotionType>,
    private val worryEmotionChangedEvent: MutableLiveData<Int>? = null,
    private val happinessEmotionChangedEvent: MutableLiveData<Int>? = null,
    private val sadnessEmotionChangedEvent: MutableLiveData<Int>? = null,
    private val productivityEmotionChangedEvent: MutableLiveData<Int>? = null,
) {

    private var emotionContainerAlpha: Float = 0f
    private var currentEmotionType: EmotionType = EmotionType.PLUS

    @Volatile
    private var isAnimationActive: Boolean = false

    @Volatile
    private var alphaState = AlphaState.INCREASE

    private var animationJob: Job? = null

    private fun start() {
        alphaState = AlphaState.INCREASE

        if (!isAnimationActive) {
            isAnimationActive = true
            animationJob = viewModelScope.launch(Dispatchers.Default) {
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
            }
        }
    }

    fun startAnimation() {
        start()

        worryEmotionChangedEvent?.let { animateEmotionChanged(it, 0) }
        happinessEmotionChangedEvent?.let { animateEmotionChanged(it, 0) }
        sadnessEmotionChangedEvent?.let { animateEmotionChanged(it, 0) }
        productivityEmotionChangedEvent?.let { animateEmotionChanged(it, 0) }
    }

    fun stopAnimation() {
        isAnimationActive = false
        animationJob?.cancel()

        animateContainerVisible()
    }

    fun changeEmotion(emotion: Emotion, needToChangeEmotion: Boolean) {
        isAnimationActive = false
        animationJob?.cancel()

        currentEmotionTypeChangedEvent.postValue(emotion.type)
        animateContainerVisible()

        if (needToChangeEmotion) {
            animateEmotionChanged(worryEmotionChangedEvent, emotion.worry)
            animateEmotionChanged(happinessEmotionChangedEvent, emotion.happiness)
            animateEmotionChanged(sadnessEmotionChangedEvent, emotion.sadness)
            animateEmotionChanged(productivityEmotionChangedEvent, emotion.productivity)
        }
    }

    private fun animateContainerVisible() {
        ValueAnimator
            .ofFloat(emotionContainerAlphaChangedEvent.value ?: emotionContainerAlpha, 1f)
            .setDuration(ALPHA_DURATION).apply {
                addUpdateListener {
                    emotionContainerAlphaChangedEvent.postValue(it.animatedValue as Float)
                    emotionContainerAlpha = it.animatedValue as Float
                }
            }
            .start()
    }

    private fun animateEmotionChanged(event: MutableLiveData<Int>?, emotionVal: Int) {
        ValueAnimator
            .ofInt(event?.value ?: 0, emotionVal)
            .setDuration(ALPHA_DURATION)
            .apply {
                addUpdateListener {
                    event?.postValue(it.animatedValue as Int)
                }
            }
            .start()
    }

    private companion object {
        const val ALPHA_DURATION = 300L
        const val ALPHA_SPEED: Float = 0.03f
    }

    private enum class AlphaState {
        INCREASE,
        DECREASE
    }
}
