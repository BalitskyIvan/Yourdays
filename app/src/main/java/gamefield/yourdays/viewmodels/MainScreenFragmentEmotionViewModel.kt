package gamefield.yourdays.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.toImmutable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

class MainScreenFragmentEmotionViewModel : ViewModel() {

    private val _currentEmotionType = MutableLiveData<EmotionType>()
    val currentEmotionType = _currentEmotionType.toImmutable()

    private val _emotionContainerAlpha = MutableLiveData<Float>()
    val emotionContainerAlpha = _emotionContainerAlpha.toImmutable()

    private val _changeDateWithTitle = MutableLiveData<Boolean>()
    val changeDateWithTitle = _changeDateWithTitle.toImmutable()

    @Volatile
    private var alphaState = AlphaState.INCREASE

    @Volatile
    private var isAnimationActive: Boolean = true

    init {
        _currentEmotionType.value = EmotionType.PLUS
        _emotionContainerAlpha.value = 0f
        startContainerAnimation()
    }

    fun onEmotionClicked() {
        if (isAnimationActive) {
            isAnimationActive = false
            _emotionContainerAlpha.postValue(1f)
            _changeDateWithTitle.postValue(true)
        } else {
            _currentEmotionType.postValue(_currentEmotionType.value?.getNextEmotion())
        }
    }

    private fun startContainerAnimation() {
        viewModelScope.launch(newSingleThreadContext("EmotionContainerAnimation")) {
            while (isAnimationActive) {
                if (alphaState == AlphaState.INCREASE) {
                    val alpha = _emotionContainerAlpha.value
                    if (alpha != null && alpha + ALPHA_SPEED > 1) {
                        alphaState = AlphaState.DECREASE
                        _emotionContainerAlpha.postValue(1f)
                    } else if (alpha != null){
                        _emotionContainerAlpha.postValue(alpha + ALPHA_SPEED)
                    }
                } else {
                    val alpha = _emotionContainerAlpha.value
                    if (alpha != null && alpha - ALPHA_SPEED < 0.1f) {
                        alphaState = AlphaState.INCREASE
                        _currentEmotionType.postValue(_currentEmotionType.value?.getNextEmotion())
                        _emotionContainerAlpha.postValue(0.1f)
                    } else if (alpha != null){
                        _emotionContainerAlpha.postValue(alpha - ALPHA_SPEED)
                    }
                }
                delay(40)
            }
        }
    }

    private fun EmotionType.getNextEmotion(): EmotionType = when (this) {
        EmotionType.PLUS -> EmotionType.ZERO
        EmotionType.ZERO -> EmotionType.MINUS
        EmotionType.MINUS -> EmotionType.PLUS
    }

    private companion object {
        const val ALPHA_SPEED: Float = 0.03f
    }

    private enum class AlphaState {
        INCREASE,
        DECREASE
    }
}