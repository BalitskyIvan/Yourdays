package gamefield.yourdays.presentation.screen.main_screen.view_model

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.extensions.getNextEmotion
import gamefield.yourdays.presentation.components.animation.ChangingEmotionAnimation
import gamefield.yourdays.extensions.isEmotionNotFilled
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.presentation.components.animation.DateTitleAnimation
import gamefield.yourdays.presentation.components.animation.SoftVisibilityAnimation
import gamefield.yourdays.presentation.screen.onboarding.view_model.CloseChangeEmotionContainerData
import java.util.Calendar

class MainScreenEmotionViewModel : ViewModel() {

    private val _currentEmotionType = MutableLiveData<EmotionType>()
    val currentEmotionType = _currentEmotionType.toImmutable()

    private val _emotionContainerAlpha = MutableLiveData<Float>()
    val emotionContainerAlpha = _emotionContainerAlpha.toImmutable()

    private val _changeDateWithTitle =
        MutableLiveData<Pair<DateTitleAnimation.AnimationState, Boolean>>()
    val changeDateWithTitle = _changeDateWithTitle.toImmutable()

    private val _dateTitleChanged = MutableLiveData<String>()
    val dateTitleChanged = _dateTitleChanged.toImmutable()

    private val _clickToFillTextAlphaChangedEvent = MutableLiveData<Float>()
    val clickToFillTextAlphaChangedEvent = _clickToFillTextAlphaChangedEvent.toImmutable()

    private val _clickToChangeEmotionTextAlphaChangedEvent = MutableLiveData<Float>()
    val clickToChangeEmotionTextAlphaChangedEvent =
        _clickToChangeEmotionTextAlphaChangedEvent.toImmutable()

    private val _exportInstagramAlphaChangedEvent = MutableLiveData<Float>()
    val exportInstagramAlphaChangedEvent = _exportInstagramAlphaChangedEvent.toImmutable()

    private val _worryEmotionChangedEvent = MutableLiveData<Int>()
    val worryEmotionChangedEvent = _worryEmotionChangedEvent.toImmutable()

    private val _happinessEmotionChangedEvent = MutableLiveData<Int>()
    val happinessEmotionChangedEvent = _happinessEmotionChangedEvent.toImmutable()

    private val _sadnessEmotionChangedEvent = MutableLiveData<Int>()
    val sadnessEmotionChangedEvent = _sadnessEmotionChangedEvent.toImmutable()

    private val _productivityEmotionChangedEvent = MutableLiveData<Int>()
    val productivityEmotionChangedEvent = _productivityEmotionChangedEvent.toImmutable()

    private val changeEmotionAnimation = ChangingEmotionAnimation(
        viewModelScope = viewModelScope,
        emotionContainerAlphaChangedEvent = _emotionContainerAlpha,
        currentEmotionTypeChangedEvent = _currentEmotionType,
        worryEmotionChangedEvent = _worryEmotionChangedEvent,
        happinessEmotionChangedEvent = _happinessEmotionChangedEvent,
        sadnessEmotionChangedEvent = _sadnessEmotionChangedEvent,
        productivityEmotionChangedEvent = _productivityEmotionChangedEvent
    )
    private val clickToFillVisibilityAnimation =
        SoftVisibilityAnimation(
            _clickToFillTextAlphaChangedEvent,
            SoftVisibilityAnimation.State.APPEAR
        )
    private val clickToChangeEmotionVisibilityAnimation =
        SoftVisibilityAnimation(
            _clickToChangeEmotionTextAlphaChangedEvent,
            SoftVisibilityAnimation.State.DISAPPEAR
        )
    private val exportToInstagramVisibilityAnimation =
        SoftVisibilityAnimation(
            _exportInstagramAlphaChangedEvent,
            SoftVisibilityAnimation.State.APPEAR
        )

    private var changeEmotionOnClick: Boolean = false
    private lateinit var calendarNow: Calendar

    private var isDayMutable = true

    init {
        _clickToChangeEmotionTextAlphaChangedEvent.postValue(0f)
    }

    fun initializeAction(resources: Resources) {
        calendarNow = Calendar.getInstance()
        calculateDateTitle(
            month = calendarNow.get(Calendar.MONTH),
            day = calendarNow.get(Calendar.DAY_OF_MONTH),
            resources = resources
        )
    }

    fun onEmotionClicked() {
        if (changeEmotionOnClick && isDayMutable) {
            clickToChangeEmotionVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            changeEmotionAnimation.changeEmotion(
                emotion = Emotion(
                    0,
                    0,
                    0,
                    0,
                    _currentEmotionType.value?.getNextEmotion() ?: EmotionType.PLUS
                ),
                needToChangeEmotion = false
            )
        } else {
            changeEmotionAnimation.stopAnimation()
        }
    }

    fun onDayChanged(daySelectedContainer: DaySelectedContainer, resources: Resources) {
        if (daySelectedContainer.emotion?.isEmotionNotFilled() == true) {
            exportToInstagramVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            clickToFillVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)

            changeEmotionAnimation.startAnimation()
            _changeDateWithTitle.postValue(
                Pair(
                    DateTitleAnimation.AnimationState.EMPTY_EMOTION_POSITIONS,
                    true
                )
            )
        } else {
            exportToInstagramVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)

            if (daySelectedContainer.emotion != null && daySelectedContainer.emotion.type != EmotionType.NONE)
                clickToFillVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)

            daySelectedContainer.emotion?.let {
                changeEmotionAnimation.changeEmotion(
                    emotion = it,
                    needToChangeEmotion = true
                )
            }
            _changeDateWithTitle.postValue(
                Pair(
                    DateTitleAnimation.AnimationState.FILLED_EMOTION_POSITIONS,
                    true
                )
            )
        }

        calculateDateTitle(month = daySelectedContainer.month, day = daySelectedContainer.day, resources = resources)
    }

    private fun calculateDateTitle(month: Int, day: Int, resources: Resources) {
        _dateTitleChanged.value = TITLE_DATE_STRING
            .format(
                month.getMonthName(isUppercase = false, resources = resources),
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
                _changeDateWithTitle.postValue(
                    Pair(
                        DateTitleAnimation.AnimationState.FILLING_EMOTION_POSITIONS,
                        true
                    )
                )
            }
        } else {
            changeEmotionOnClick = false
            clickToChangeEmotionVisibilityAnimation.start(SoftVisibilityAnimation.State.DISAPPEAR)
            if (data.isEmotionNotFilled) {
                _changeDateWithTitle.postValue(
                    Pair(
                        DateTitleAnimation.AnimationState.EMPTY_EMOTION_POSITIONS,
                        true
                    )
                )
                clickToFillVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)
                changeEmotionAnimation.startAnimation()
            } else {
                exportToInstagramVisibilityAnimation.start(SoftVisibilityAnimation.State.APPEAR)
            }
        }
    }

    fun onEmotionWorryChanged(worry: Int) {
        _worryEmotionChangedEvent.postValue(worry)
    }

    fun onEmotionHappinessChanged(happiness: Int) {
        _happinessEmotionChangedEvent.postValue(happiness)
    }

    fun onEmotionSadnessChanged(sadness: Int) {
        _sadnessEmotionChangedEvent.postValue(sadness)
    }

    fun onEmotionProductivityChanged(productivity: Int) {
        _productivityEmotionChangedEvent.postValue(productivity)
    }

    fun dayMutableChanged(isMutable: Boolean) {
        this.isDayMutable = isMutable
    }

    private companion object {
        const val TITLE_DATE_STRING = "%s, %d"
    }

}