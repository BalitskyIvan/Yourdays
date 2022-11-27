package gamefield.yourdays.viewmodels

import android.content.Context
import android.graphics.Color
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.domain.usecase.io.AddDayUseCase
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.io.SeedUseCase
import gamefield.yourdays.domain.usecase.time_logic.FillDaysBeforeNow
import gamefield.yourdays.domain.usecase.time_logic.FillNewMonthUseCase
import gamefield.yourdays.domain.usecase.time_logic.IsNeedToAddDaysInMonth
import gamefield.yourdays.extensions.getDayFromNumberInMonth
import gamefield.yourdays.extensions.selectCurrentDay
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.utils.main_screen.DaySelectedContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MainScreenFragmentViewModel : ViewModel() {

    private val _anxietyEmotionChangedEvent = MutableLiveData<Int>()
    val anxietyEmotionChangedEvent = _anxietyEmotionChangedEvent.toImmutable()

    private val _joyEmotionChangedEvent = MutableLiveData<Int>()
    val joyEmotionChangedEvent = _joyEmotionChangedEvent.toImmutable()

    private val _sadnessEmotionChangedEvent = MutableLiveData<Int>()
    val sadnessEmotionChangedEvent = _sadnessEmotionChangedEvent.toImmutable()

    private val _calmnessEmotionChangedEvent = MutableLiveData<Int>()
    val calmnessEmotionChangedEvent = _calmnessEmotionChangedEvent.toImmutable()

    private val _smoothScrollPeriodToTop = MutableLiveData<Boolean>()
    val smoothScrollPeriodToTop = _smoothScrollPeriodToTop.toImmutable()

    private val _scrollPeriodToTop = MutableLiveData<Boolean>()
    val scrollPeriodToTop = _scrollPeriodToTop.toImmutable()

    private val _daySelectedEvent = MutableLiveData<DaySelectedContainer>()
    val daySelectedEvent = _daySelectedEvent.toImmutable()

    private val _isDayMutableChangedEvent = MutableLiveData<Boolean>()
    val isDayMutableChangedEvent = _isDayMutableChangedEvent.toImmutable()

    private val _showCantChangeEmotionToastEvent = MutableLiveData<Boolean>()
    val showCantChangeEmotionToastEvent = _showCantChangeEmotionToastEvent.toImmutable()

    private val _changeEmotionFragmentOpenCloseAction =
        MutableLiveData<CloseChangeEmotionContainerData>()
    val changeEmotionFragmentOpeCloseAction = _changeEmotionFragmentOpenCloseAction.toImmutable()

    private var isFillEmotionClicked = false
    private var isEmotionContainerOpened = true

    private val _emotionsPeriodScrolled = MutableLiveData<Int>()
    val emotionsPeriodScrolled = _emotionsPeriodScrolled.toImmutable()

    private val _mothListChangedEvent = MutableLiveData<List<Month>>()
    val mothListChangedEvent = _mothListChangedEvent.toImmutable()

    private var emotionType: EmotionType = EmotionType.PLUS

    private val calendar = Calendar.getInstance()

    private lateinit var addDayUseCase: AddDayUseCase
    private lateinit var getAllMonthsListUseCase: GetAllMonthsListUseCase
    private lateinit var seedUseCase: SeedUseCase
    private lateinit var fillNewMonthUseCase: FillNewMonthUseCase
    private lateinit var fillDaysBeforeNow: FillDaysBeforeNow
    private val isNeedToAddDaysInMonth = IsNeedToAddDaysInMonth()

    private var selectedDate: DaySelectedContainer = DaySelectedContainer(
        month = Calendar.getInstance().get(Calendar.MONTH),
        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        emotion = null
    )

    private var isFirstTimeMonthFetched = true

    init {
        _anxietyEmotionChangedEvent.value = 0
        _joyEmotionChangedEvent.value = 0
        _calmnessEmotionChangedEvent.value = 0
        _sadnessEmotionChangedEvent.value = 0
        _showCantChangeEmotionToastEvent.value = false
        _isDayMutableChangedEvent.value = true
    }

    fun initDatabaseWithContext(context: Context) {
        addDayUseCase = AddDayUseCase(context)
        getAllMonthsListUseCase = GetAllMonthsListUseCase(context)
        seedUseCase = SeedUseCase(context)
        fillNewMonthUseCase = FillNewMonthUseCase(context)
        fillDaysBeforeNow = FillDaysBeforeNow(context)

        fetchMonths()
    }

    private fun fetchMonths() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMonthsListUseCase.invoke().collect { monthList ->
                calendar.toInstant()

                val sortedByMonth = monthList.sortedByDescending { it.monthNumber }
                val sortedMonthList = sortedByMonth.sortedByDescending { it.year }


                var isNeedToFillNewMonth = true
                var currentMonthIndex = 0
                sortedMonthList.forEachIndexed { index, month ->
                    if (month.monthNumber == calendar.get(Calendar.MONTH) &&
                        month.year == calendar.get(Calendar.YEAR)
                    ) {
                        isNeedToFillNewMonth = false
                        currentMonthIndex = index
                    }
                }

                if (isNeedToFillNewMonth) {
                    fillNewMonthUseCase.invoke()
                    fetchMonths()
                } else {
                    if (isNeedToAddDaysInMonth.invoke(sortedMonthList[currentMonthIndex])) {
                        fillDaysBeforeNow.invoke(month = sortedMonthList[currentMonthIndex])
                        fetchMonths()
                    } else {
                        sortedMonthList.selectCurrentDay(daySelectedContainer = daySelectedEvent.value, isSelectCurrentDay = isFirstTimeMonthFetched)
                        isFirstTimeMonthFetched = false
                        _mothListChangedEvent.postValue(sortedMonthList)
                    }
                }
            }
        }
    }

    fun anxietyChanged(progress: Int) {
        _anxietyEmotionChangedEvent.value = progress
    }

    fun joyChanged(progress: Int) {
        _joyEmotionChangedEvent.value = progress
    }

    fun calmnessChanged(progress: Int) {
        _calmnessEmotionChangedEvent.value = progress
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
                                    anxiety = anxietyEmotionChangedEvent.value!!,
                                    joy = joyEmotionChangedEvent.value!!,
                                    calmness = calmnessEmotionChangedEvent.value!!,
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
        anxietyEmotionChangedEvent.value == 0 && joyEmotionChangedEvent.value == 0 &&
                calmnessEmotionChangedEvent.value == 0 && sadnessEmotionChangedEvent.value == 0

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

    fun onDaySelected(monthNumber: Int, day: Int, emotion: Emotion) {
        if (_changeEmotionFragmentOpenCloseAction.value?.isOpening != true) {
            clearSelectedDayAndSelectClicked(monthNumber, day)
            _anxietyEmotionChangedEvent.value = emotion.anxiety
            _joyEmotionChangedEvent.value = emotion.joy
            _sadnessEmotionChangedEvent.value = emotion.sadness
            _calmnessEmotionChangedEvent.value = emotion.calmness

            selectedDate = DaySelectedContainer(day = day, month = monthNumber, emotion = emotion)
            _daySelectedEvent.postValue(selectedDate)
            val isMutable = day == calendar.get(Calendar.DAY_OF_MONTH) || isEmotionNotFilled()
            _isDayMutableChangedEvent.postValue(isMutable)
        }
    }

    private fun clearSelectedDayAndSelectClicked(monthNumber: Int, day: Int) {
        _mothListChangedEvent.value?.forEach { month ->
            if (month.monthNumber == monthNumber) {
                month.getDayFromNumberInMonth(dayNumber = day)?.isSelected = true
            }
            if (selectedDate.month == month.monthNumber) {
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

}