package gamefield.yourdays.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.utils.animation.ChangingEmotionAnimation
import gamefield.yourdays.extensions.getNextEmotion
import gamefield.yourdays.extensions.toImmutable

class MainScreenFragmentEmotionViewModel : ViewModel() {

    private val _currentEmotionType = MutableLiveData<EmotionType>()
    val currentEmotionType = _currentEmotionType.toImmutable()

    private val _emotionContainerAlpha = MutableLiveData<Float>()
    val emotionContainerAlpha = _emotionContainerAlpha.toImmutable()

    private val _changeDateWithTitle = MutableLiveData<Boolean>()
    val changeDateWithTitle = _changeDateWithTitle.toImmutable()

    private val _changeClickToFillTextVisibility = MutableLiveData<Boolean>()
    val changeClickToFillTextVisibility = _changeClickToFillTextVisibility.toImmutable()

    private val _changeFirstTitleVisibility = MutableLiveData<Boolean>()
    val changeFirstTitleVisibility = _changeFirstTitleVisibility.toImmutable()

    private val changeEmotionAnimation = ChangingEmotionAnimation(
        viewModelScope = viewModelScope,
        emotionContainerAlpha = _emotionContainerAlpha,
        currentEmotionType = _currentEmotionType,
    )

    private var changeEmotionOnClick: Boolean = false


    init {
        _currentEmotionType.value = EmotionType.PLUS
        _emotionContainerAlpha.value = 0f
        changeEmotionAnimation.start()
    }

    fun onEmotionClicked() {
        if (changeEmotionOnClick) {
            _currentEmotionType.postValue(_currentEmotionType.value?.getNextEmotion())
        }
        if (changeEmotionAnimation.isAnimationActive) {
            changeEmotionAnimation.isAnimationActive = false
            _emotionContainerAlpha.postValue(1f)
        }
    }

    fun onOpenCloseChangingEmotionContained(data: CloseChangeEmotionContainerData) {
        if (data.isOpening) {
            changeEmotionOnClick = true
            _changeClickToFillTextVisibility.postValue(false)
            if (data.isEmotionNotFilled) {
                _changeFirstTitleVisibility.postValue(true)
                _changeDateWithTitle.postValue(true)
            }
        } else {
            changeEmotionOnClick = false
            if (data.isEmotionNotFilled) {
                _changeDateWithTitle.postValue(true)
                _changeClickToFillTextVisibility.postValue(true)
                _changeFirstTitleVisibility.postValue(true)
                changeEmotionAnimation.start()
            } else {
                _changeFirstTitleVisibility.postValue(false)
            }
        }
    }

}