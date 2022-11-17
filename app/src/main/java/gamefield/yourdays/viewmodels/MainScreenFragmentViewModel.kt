package gamefield.yourdays.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.usecase.io.AddDayUseCase
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.io.SeedUseCase
import gamefield.yourdays.domain.usecase.time_logic.FillNewMonthUseCase
import gamefield.yourdays.extensions.toImmutable
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

    private val _changeEmotionFragmentOpenCloseAction =
        MutableLiveData<CloseChangeEmotionContainerData>()
    val changeEmotionFragmentOpeCloseAction = _changeEmotionFragmentOpenCloseAction.toImmutable()

    private var isFillEmotionClicked = false
    private var isEmotionContainerOpened = true

    private val _emotionsPeriodScrolled = MutableLiveData<Int>()
    val emotionsPeriodScrolled = _emotionsPeriodScrolled.toImmutable()

    private val _mothListChangedEvent = MutableLiveData<List<Month>>()
    val mothListChangedEvent = _mothListChangedEvent.toImmutable()

    private lateinit var addDayUseCase: AddDayUseCase
    private lateinit var getAllMonthsListUseCase: GetAllMonthsListUseCase
    private lateinit var seedUseCase: SeedUseCase
    private lateinit var fillNewMonthUseCase: FillNewMonthUseCase

    init {
        _anxietyEmotionChangedEvent.value = 0
        _joyEmotionChangedEvent.value = 0
        _calmnessEmotionChangedEvent.value = 0
        _sadnessEmotionChangedEvent.value = 0
    }

    fun initDatabaseWithContext(context: Context) {
        addDayUseCase = AddDayUseCase(context)
        getAllMonthsListUseCase = GetAllMonthsListUseCase(context)
        seedUseCase = SeedUseCase(context)
        fillNewMonthUseCase = FillNewMonthUseCase(context)

        fetchMonths()
    }

    private fun fetchMonths() {
        viewModelScope.launch(Dispatchers.IO) {

            getAllMonthsListUseCase.invoke().collect { monthList ->
                val calendar = Calendar.getInstance()
                calendar.toInstant()
                var isNeedToFillNewMonth = true
                monthList.forEach { month ->
                    if (month.monthNumber == calendar.get(Calendar.MONTH) && month.year == calendar.get(Calendar.YEAR))
                        isNeedToFillNewMonth = false
                }
                if (isNeedToFillNewMonth) {
                    fillNewMonthUseCase.invoke()
                    fetchMonths()
                } else {
                    _mothListChangedEvent.postValue(monthList)
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
        _changeEmotionFragmentOpenCloseAction.postValue(
            CloseChangeEmotionContainerData(
                isOpening = false,
                isEmotionNotFilled = isEmotionNotFilled()
            )
        )
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

    fun onChangeEmotionContainerOpenCloseAnimationEnd(isOpened: Boolean) {
        isEmotionContainerOpened = isOpened
    }

    fun onEmotionPeriodScrolled(y: Int) {
        if (isEmotionContainerOpened)
        _emotionsPeriodScrolled.postValue(y)
    }
}