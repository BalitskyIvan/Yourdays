package gamefield.yourdays.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.utils.animation.ChangingEmotionAnimation
import gamefield.yourdays.extensions.getNextEmotion
import gamefield.yourdays.extensions.isEmotionNotFilled
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.utils.main_screen.DaySelectedContainer
import java.util.Calendar

class MainScreenFragmentEmotionViewModel : ViewModel() {

    private val _currentEmotionType = MutableLiveData<EmotionType>()
    val currentEmotionType = _currentEmotionType.toImmutable()

    private val _emotionContainerAlpha = MutableLiveData<Float>()
    val emotionContainerAlpha = _emotionContainerAlpha.toImmutable()

    private val _changeDateWithTitle = MutableLiveData<Boolean>()
    val changeDateWithTitle = _changeDateWithTitle.toImmutable()

    private val _dateTitleChanged = MutableLiveData<String>()
    val dateTitleChanged = _dateTitleChanged.toImmutable()

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
    private lateinit var calendarNow: Calendar

    init {
        _currentEmotionType.value = EmotionType.PLUS
        _emotionContainerAlpha.value = 0f

        changeEmotionAnimation.start()
    }

    fun initializeAction(context: Context) {
        calendarNow = Calendar.getInstance()
        calcDateTitle(
            month = calendarNow.get(Calendar.MONTH),
            day = calendarNow.get(Calendar.DAY_OF_MONTH),
            context = context
        )
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

    fun onDayChanged(daySelectedContainer: DaySelectedContainer, context: Context) {
        if (daySelectedContainer.emotion?.isEmotionNotFilled() == true) {
            _changeClickToFillTextVisibility.postValue(true)
            changeEmotionAnimation.start()
            if (_changeDateWithTitle.value == true) {
                _changeFirstTitleVisibility.postValue(true)
                _changeDateWithTitle.postValue(false)
            }
        } else {
            _changeClickToFillTextVisibility.postValue(false)
            _currentEmotionType.value = daySelectedContainer.emotion?.type
            _emotionContainerAlpha.postValue(1f)
            changeEmotionAnimation.isAnimationActive = false

            if (_changeDateWithTitle.value != true) {
                _changeFirstTitleVisibility.postValue(false)
                _changeDateWithTitle.postValue(true)
            }
        }

        calcDateTitle(month = daySelectedContainer.month, day = daySelectedContainer.day, context)
    }

    private fun calcDateTitle(month: Int, day: Int, context: Context) {
        _dateTitleChanged.value = TITLE_DATE_STRING
            .format(
                month.getMonthName(isUppercase = false, context = context),
                day
            )
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
                _changeDateWithTitle.postValue(false)
                _changeClickToFillTextVisibility.postValue(true)
                _changeFirstTitleVisibility.postValue(true)
                changeEmotionAnimation.start()
            } else {
                _changeFirstTitleVisibility.postValue(false)
            }
        }
    }

    private companion object {
        const val TITLE_DATE_STRING = "%s, %d"
    }

}