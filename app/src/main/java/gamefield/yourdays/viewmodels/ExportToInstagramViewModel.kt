package gamefield.yourdays.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetMonthsInMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetYearsInMonthsListUseCase
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.utils.emum.DatePickerType
import java.util.Calendar

class ExportToInstagramViewModel : ViewModel() {

    private val _monthButtonAlphaChanged = MutableLiveData<Float>()
    val monthButtonAlphaChanged = _monthButtonAlphaChanged.toImmutable()

    private val _dayButtonAlphaChanged = MutableLiveData<Float>()
    val dayButtonAlphaChanged = _dayButtonAlphaChanged.toImmutable()

    private val _periodPickerChanged = MutableLiveData<DatePickerType>()
    val periodPickerChanged = _periodPickerChanged.toImmutable()

    private val _mothListChangedEvent = MutableLiveData<List<Month>>()
    val mothListChangedEvent = _mothListChangedEvent.toImmutable()

    private val _monthListInPickerChanged = MutableLiveData<Set<String>>()
    val monthListInPickerChanged = _monthListInPickerChanged.toImmutable()

    private val _yearsListInPickerChanged = MutableLiveData<Set<String>>()
    val yearsListInPickerChanged = _yearsListInPickerChanged.toImmutable()

    private var cardType = DatePickerType.MONTH
    private var calendar = Calendar.getInstance()

    private lateinit var getMonthsInMonthsListUseCase: GetMonthsInMonthsListUseCase
    private val getYearsInMonthsListUseCase = GetYearsInMonthsListUseCase()

    init {
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.MONTH)
        observeMonthListChanged()
    }

    fun initWithContext(context: Context) {
        getMonthsInMonthsListUseCase = GetMonthsInMonthsListUseCase(context = context)

        _monthListInPickerChanged.postValue(setOf(calendar.get(Calendar.MONTH).getMonthName(isUppercase = false, context = context)))
        _yearsListInPickerChanged.postValue(setOf(calendar.get(Calendar.YEAR).toString()))

        val getAllMonthsListUseCase = GetAllMonthsListUseCase(
            context = context,
            mothListChangedEvent = _mothListChangedEvent,
            viewModelScope = viewModelScope,
        )
        getAllMonthsListUseCase.invoke()
    }

    private fun observeMonthListChanged() {
        mothListChangedEvent.observeForever { monthList ->
            val monthNames = getMonthsInMonthsListUseCase.invoke(monthList)
            val yearNames = getYearsInMonthsListUseCase.invoke(monthList)

            _monthListInPickerChanged.postValue(monthNames)
            _yearsListInPickerChanged.postValue(yearNames)
        }
    }

    fun onDayButtonClicked() {
        cardType = DatePickerType.DAY
        _monthButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(SELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.DAY)
    }

    fun onMonthButtonClicked() {
        cardType = DatePickerType.MONTH
        _monthButtonAlphaChanged.postValue(SELECTED_ALPHA)
        _dayButtonAlphaChanged.postValue(UNSELECTED_ALPHA)
        _periodPickerChanged.postValue(DatePickerType.MONTH)
    }

    fun onUploadButtonClicked() {

    }

    private companion object {
        const val SELECTED_ALPHA = 1f
        const val UNSELECTED_ALPHA = 0.5f
    }
}