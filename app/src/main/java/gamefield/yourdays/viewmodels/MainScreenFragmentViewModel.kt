package gamefield.yourdays.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.domain.usecase.io.AddDayUseCase
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.extensions.getDayFromNumberInMonth
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.utils.main_screen.DateToExportData
import gamefield.yourdays.utils.main_screen.DaySelectedContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MainScreenFragmentViewModel : ViewModel() {

    private val _worryEmotionChangedEvent = MutableLiveData<Int>()
    val worryEmotionChangedEvent = _worryEmotionChangedEvent.toImmutable()

    private val _happinessEmotionChangedEvent = MutableLiveData<Int>()
    val happinessEmotionChangedEvent = _happinessEmotionChangedEvent.toImmutable()

    private val _sadnessEmotionChangedEvent = MutableLiveData<Int>()
    val sadnessEmotionChangedEvent = _sadnessEmotionChangedEvent.toImmutable()

    private val _productivityEmotionChangedEvent = MutableLiveData<Int>()
    val productivityEmotionChangedEvent = _productivityEmotionChangedEvent.toImmutable()

    private val _smoothScrollPeriodToTop = MutableLiveData<Boolean>()
    val smoothScrollPeriodToTop = _smoothScrollPeriodToTop.toImmutable()

    private val _scrollPeriodToTop = MutableLiveData<Boolean>()
    val scrollPeriodToTop = _scrollPeriodToTop.toImmutable()

    private val _daySelectedEvent = MutableLiveData<DaySelectedContainer>()
    val daySelectedEvent = _daySelectedEvent.toImmutable()

    private val _currentDaySelected = MutableLiveData<DaySelectedContainer>()

    private val _isDayMutableChangedEvent = MutableLiveData<Boolean>()
    val isDayMutableChangedEvent = _isDayMutableChangedEvent.toImmutable()

    private val _showCantChangeEmotionToastEvent = MutableLiveData<Boolean>()
    val showCantChangeEmotionToastEvent = _showCantChangeEmotionToastEvent.toImmutable()

    private val _navigateToExportScreen = MutableLiveData<DateToExportData?>()
    val navigateToExportScreen = _navigateToExportScreen.toImmutable()

    private val _changeEmotionFragmentOpenCloseAction =
        MutableLiveData<CloseChangeEmotionContainerData>()
    val changeEmotionFragmentOpeCloseAction = _changeEmotionFragmentOpenCloseAction.toImmutable()

    private var isFillEmotionClicked = false
    private var isEmotionContainerOpened = true

    private val _emotionsPeriodScrolled = MutableLiveData<Int>()
    val emotionsPeriodScrolled = _emotionsPeriodScrolled.toImmutable()

    private val _mothListChangedEvent = MutableLiveData<List<Month>>()
    val mothListChangedEvent = _mothListChangedEvent.toImmutable()

    private val _firstDayOfWeekChangedEvent = MutableLiveData<Int>()
    val firstDayOfWeekChangedEvent = _firstDayOfWeekChangedEvent.toImmutable()

    private var emotionType: EmotionType = EmotionType.PLUS

    private val calendar = Calendar.getInstance()

    private lateinit var addDayUseCase: AddDayUseCase
    private lateinit var getAllMonthsListUseCase: GetAllMonthsListUseCase

    private var selectedDate: DaySelectedContainer = DaySelectedContainer(
        month = Calendar.getInstance().get(Calendar.MONTH),
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        year = Calendar.getInstance().get(Calendar.YEAR),
        emotion = null
    )

    init {
        _worryEmotionChangedEvent.value = 0
        _happinessEmotionChangedEvent.value = 0
        _productivityEmotionChangedEvent.value = 0
        _sadnessEmotionChangedEvent.value = 0
        _showCantChangeEmotionToastEvent.value = false
        _isDayMutableChangedEvent.value = true

        _currentDaySelected.observeForever { selectedDay ->
            selectedDay.emotion?.let {
                onDaySelected(
                    selectedDay.month,
                    selectedDay.day,
                    selectedDay.year,
                    selectedDay.emotion
                )
            }
        }
    }

    fun initDatabaseWithContext(context: Context) {
        addDayUseCase = AddDayUseCase(context)
        _navigateToExportScreen.postValue(null)
        getAllMonthsListUseCase = GetAllMonthsListUseCase(
            context = context,
            firstDayOfWeekChanged = _firstDayOfWeekChangedEvent,
            mothListChangedEvent = _mothListChangedEvent,
            viewModelScope = viewModelScope,
            daySelectedEvent = _daySelectedEvent,
            currentDaySelectedEvent = _currentDaySelected
        )

        fetchMonths()
    }

    private fun fetchMonths() {
        getAllMonthsListUseCase.invoke()
    }

    fun worryChanged(progress: Int) {
        _worryEmotionChangedEvent.value = progress
    }

    fun happinessChanged(progress: Int) {
        _happinessEmotionChangedEvent.value = progress
    }

    fun productivityChanged(progress: Int) {
        _productivityEmotionChangedEvent.value = progress
    }

    fun sadnessChanged(progress: Int) {
        _sadnessEmotionChangedEvent.value = progress
    }

    fun emotionContainerOkButtonClicked() {
        isFillEmotionClicked = false
        _scrollPeriodToTop.postValue(true)
        val isEmotionNotFilled = isEmotionNotFilled()
        _changeEmotionFragmentOpenCloseAction.postValue(
            CloseChangeEmotionContainerData(
                isOpening = false,
                isEmotionNotFilled = isEmotionNotFilled
            )
        )
        if (!isEmotionNotFilled) {
            mothListChangedEvent.value?.let { monthList ->
                viewModelScope.launch(Dispatchers.IO) {
                    monthList.forEach { month ->
                        if (month.monthNumber == selectedDate.month) {
                            addDayUseCase.invoke(
                                month,
                                selectedDate.day,
                                Emotion(
                                    worry = worryEmotionChangedEvent.value!!,
                                    happiness = happinessEmotionChangedEvent.value!!,
                                    productivity = productivityEmotionChangedEvent.value!!,
                                    sadness = sadnessEmotionChangedEvent.value!!,
                                    type = emotionType
                                )
                            )
                            fetchMonths()
                        }
                    }

                }
            }
        }
    }

    private fun isEmotionNotFilled(): Boolean =
        worryEmotionChangedEvent.value == 0 && happinessEmotionChangedEvent.value == 0 &&
                productivityEmotionChangedEvent.value == 0 && sadnessEmotionChangedEvent.value == 0

    fun onFillEmotionClicked() {
        if (!isFillEmotionClicked) {
            isFillEmotionClicked = true
            _smoothScrollPeriodToTop.postValue(true)
            _changeEmotionFragmentOpenCloseAction.postValue(
                CloseChangeEmotionContainerData(
                    isOpening = true,
                    isEmotionNotFilled = isEmotionNotFilled()
                )
            )
        }
    }

    fun onDaySelected(monthNumber: Int, day: Int, year: Int, emotion: Emotion) {
        if (_changeEmotionFragmentOpenCloseAction.value?.isOpening != true) {
            clearSelectedDayAndSelectClicked(monthNumber, day, year)

            selectedDate =
                DaySelectedContainer(day = day, month = monthNumber, year = year, emotion = emotion)
            _daySelectedEvent.postValue(selectedDate)

            _worryEmotionChangedEvent.value = emotion.worry
            _happinessEmotionChangedEvent.value = emotion.happiness
            _sadnessEmotionChangedEvent.value = emotion.sadness
            _productivityEmotionChangedEvent.value = emotion.productivity

            val isMutable = day == calendar.get(Calendar.DAY_OF_MONTH) || isEmotionNotFilled()
            _isDayMutableChangedEvent.postValue(isMutable)
        }
    }

    private fun clearSelectedDayAndSelectClicked(monthNumber: Int, day: Int, year: Int) {
        if (monthNumber == selectedDate.month && day == selectedDate.day && year == selectedDate.year)
            return
        _mothListChangedEvent.value?.forEach { month ->
            if (month.monthNumber == monthNumber && month.year == year) {
                month.getDayFromNumberInMonth(dayNumber = day)?.isSelected = true
            }
            if (selectedDate.month == month.monthNumber && selectedDate.year == month.year) {
                month.getDayFromNumberInMonth(dayNumber = selectedDate.day)?.isSelected = false
            }
        }
        _mothListChangedEvent.postValue(_mothListChangedEvent.value)
    }

    fun onChangeEmotionContainerOpenCloseAnimationEnd(isOpened: Boolean) {
        isEmotionContainerOpened = isOpened
    }

    fun emotionTypeChanged(emotionType: EmotionType) {
        this.emotionType = emotionType
    }

    fun onChangeContainerClicked() {
        if (_isDayMutableChangedEvent.value == false)
            _showCantChangeEmotionToastEvent.postValue(true)
    }

    fun onEmotionPeriodScrolled(y: Int) {
        if (isEmotionContainerOpened)
            _emotionsPeriodScrolled.postValue(y)
    }

    fun onExportToInstagramClicked(isExportDay: Boolean = false) {
        _navigateToExportScreen.postValue(
            DateToExportData(
                year = selectedDate.year,
                month = selectedDate.month,
                day = selectedDate.day,
                isExportDay = isExportDay
            )
        )
    }

    fun onNavigate() {
        _navigateToExportScreen.value = null
    }

}