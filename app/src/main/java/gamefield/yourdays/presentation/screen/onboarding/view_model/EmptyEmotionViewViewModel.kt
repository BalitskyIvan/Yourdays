package gamefield.yourdays.presentation.screen.onboarding.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.presentation.components.animation.ChangingEmotionAnimation

class EmptyEmotionViewViewModel : ViewModel() {

    private val _currentEmotionType = MutableLiveData<EmotionType>()
    val currentEmotionType = _currentEmotionType.toImmutable()

    private val _emotionContainerAlpha = MutableLiveData<Float>()
    val emotionContainerAlpha = _emotionContainerAlpha.toImmutable()

    private val changeEmotionAnimation = ChangingEmotionAnimation(
        viewModelScope = viewModelScope,
        emotionContainerAlphaChangedEvent = _emotionContainerAlpha,
        currentEmotionTypeChangedEvent = _currentEmotionType,
    )

    init {
        _currentEmotionType.value = EmotionType.PLUS
        _emotionContainerAlpha.value = 0f
        changeEmotionAnimation.startAnimation()
    }

}