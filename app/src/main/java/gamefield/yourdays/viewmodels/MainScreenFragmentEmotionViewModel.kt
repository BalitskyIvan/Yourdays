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
import gamefield.yourdays.utils.animation.SoftVisibilityAnimation
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

    private val _changeFirstTitleVisibility = MutableLiveData<Boolean>()
    val changeFirstTitleVisibility = _changeFirstTitleVisibility.toImmutable()

    private val _clickToFillTextAlphaChangedEvent = MutableLiveData<Float>()
    val clickToFillTextAlphaChangedEvent = _clickToFillTextAlphaChangedEvent.toImmutable()

    private val _clickToChangeEmotionTextAlphaChangedEvent = MutableLiveData<Float>()
    val clickToChangeEmotionTextAlphaChangedEvent =
        _clickToChangeEmotionTextAlphaChangedEvent.toImmutable()

    private val _exportInstagramAlphaChangedEvent = MutableLiveData<Float>()
    val exportInstagramAlphaChangedEvent = _exportInstagramAlphaChangedEvent.toImmutable()

    private val changeEmotionAnimation = ChangingEmotionAnimation(
        viewModelScope = viewModelScope,
        emotionContainerAlphaChangedEvent = _emotionContainerAlpha,
        currentEmotionTypeChangedEvent = _currentEmotionType,
    )
    private val clickToFillVisibilityAnimation =
        SoftVisibilityAnimation(_clickToFillTextAlphaChangedEvent, SoftVisibilityAnimation.State.APPEAR)
    private val clickToChangeEmotionVisibilityAnimation =
        SoftVisibilityAnimation(_clickToChangeEmotionTextAlphaChangedEvent, SoftVisibilityAnimation.State.DISAPPEAR)
    private val exportToInstagramVisibilityAnimation =
        SoftVisibilityAnimation(_exportInstagramAlphaChangedEvent, SoftVisibilityAnimation.State.APPEAR)

    private var changeEmotionOnClick: Boolean = false
    private lateinit var calendarNow: Calendar

    private var isDayMutable = true

    init {
        _clickToChangeEmotionTextAlphaChangedEvent.postValue(0f)
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
            if (isDayMutable) {
                clickToChangeEmotionVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
                _currentEmotionType.postValue(_currentEmotionType.value?.getNextEmotion() ?: EmotionType.PLUS)
            }
        }
        if (changeEmotionAnimation.isAnimationActive) {
            changeEmotionAnimation.isAnimationActive = false
            _emotionContainerAlpha.postValue(1f)
        }
    }

    fun onDayChanged(daySelectedContainer: DaySelectedContainer, context: Context) {
        if (daySelectedContainer.emotion?.isEmotionNotFilled() == true) {
            exportToInstagramVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            clickToFillVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)
            changeEmotionAnimation.start()
            if (_changeDateWithTitle.value == true) {
                _changeFirstTitleVisibility.postValue(true)
                _changeDateWithTitle.postValue(false)
            }
        } else {
            exportToInstagramVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)

            if (daySelectedContainer.emotion != null && daySelectedContainer.emotion.type != EmotionType.NONE)
                clickToFillVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            _currentEmotionType.value = daySelectedContainer.emotion?.type
            changeEmotionAnimation.isAnimationActive = false
            _emotionContainerAlpha.postValue(1f)

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
            exportToInstagramVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            clickToFillVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            if (data.isEmotionNotFilled) {
                clickToChangeEmotionVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR_FOR_TWO_SECONDS)
                _changeFirstTitleVisibility.postValue(true)
                _changeDateWithTitle.postValue(true)
            }
        } else {
            changeEmotionOnClick = false
            clickToChangeEmotionVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            if (data.isEmotionNotFilled) {
                _changeDateWithTitle.postValue(false)
                _changeFirstTitleVisibility.postValue(true)
                clickToFillVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)
                changeEmotionAnimation.start()
            } else {
                exportToInstagramVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)
                _changeFirstTitleVisibility.postValue(false)
            }
        }
    }

    fun dayMutableChanged(isMutable: Boolean) {
        this.isDayMutable = isMutable
    }

    private companion object {
        const val TITLE_DATE_STRING = "%s, %d"
    }

}