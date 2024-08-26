package gamefield.yourdays.domain.usecase.io

import androidx.lifecycle.MutableLiveData
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.repository.EmotionsRepository
import gamefield.yourdays.domain.usecase.time_logic.FillDaysBeforeNowUseCase
import gamefield.yourdays.domain.usecase.time_logic.FillNewMonthUseCase
import gamefield.yourdays.domain.usecase.time_logic.IsNeedToAddDaysInMonthUseCase
import gamefield.yourdays.extensions.selectCurrentDay
import gamefield.yourdays.extensions.toImmutable
import gamefield.yourdays.presentation.screen.main_screen.view_model.DaySelectedContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class GetAllMonthsListUseCase(
    private val calendar: Calendar,
    private val emotionsRepository: EmotionsRepository,
    private val fillNewMonthUseCase: FillNewMonthUseCase,
    private val getCalendarFirstDayOfWeekUseCase: GetCalendarFirstDayOfWeekUseCase,
    private val isNeedToAddDaysInMonthUseCase: IsNeedToAddDaysInMonthUseCase,
    private val fillDaysBeforeNowUseCase: FillDaysBeforeNowUseCase
) {

    private val _firstDayOfWeekChangedEvent = MutableLiveData<Int>()
    val firstDayOfWeekChangedEvent = _firstDayOfWeekChangedEvent.toImmutable()

    private val _monthListChangedEvent = MutableLiveData<List<Month>>()
    val monthListChangedEvent = _monthListChangedEvent.toImmutable()

    private val _daySelectedEvent = MutableLiveData<DaySelectedContainer>()
    val daySelectedEvent = _daySelectedEvent.toImmutable()

    private var isFirstTimeMonthFetched = true

    suspend operator fun invoke() {
        withContext(Dispatchers.IO) {
            val firstDayOfWeek = getCalendarFirstDayOfWeekUseCase.invoke()
            _firstDayOfWeekChangedEvent.postValue(firstDayOfWeek)

            fetch(firstDayOfWeek)
        }
    }

    private suspend fun fetch(firstDayOfWeek: Int) {
        emotionsRepository.getMonths().collect { monthList ->
            calendar.toInstant()

            val initData = monthList.getNeedToFillAndMonthIndex()
            val isNeedToFillNewMonth = initData.first
            val currentMonthIndex = initData.second

            if (isNeedToFillNewMonth) {
                fillNewMonthUseCase.invoke()
                fetch(firstDayOfWeek)
            } else {
                if (isNeedToAddDaysInMonthUseCase.invoke(month = monthList[currentMonthIndex])) {
                    fillDaysBeforeNowUseCase.invoke(month = monthList[currentMonthIndex])
                    fetch(firstDayOfWeek)
                } else {
                    monthList.putMonthList()
                }
            }
        }
    }

    private fun List<Month>.putMonthList() {
        val sortedMonthList = sortMonths()

        isFirstTimeMonthFetched = false
        _monthListChangedEvent.postValue(sortedMonthList)
        if (daySelectedEvent != null) {
            val selectedDay = sortedMonthList.selectCurrentDay(
                daySelectedContainer = daySelectedEvent.value,
                isSelectCurrentDay = isFirstTimeMonthFetched
            )
         //   currentDaySelectedEvent?.postValue(selectedDay)
        }
    }

    private fun List<Month>.getNeedToFillAndMonthIndex(): Pair<Boolean, Int> {
        var isNeedToFillNewMonth = true
        var currentMonthIndex = 0
        forEachIndexed { index, month ->
            if (month.monthNumber == calendar.get(Calendar.MONTH) &&
                month.year == calendar.get(Calendar.YEAR)
            ) {
                isNeedToFillNewMonth = false
                currentMonthIndex = index
            }
        }
        return Pair(isNeedToFillNewMonth, currentMonthIndex)
    }

    private fun List<Month>.sortMonths(): List<Month> {
        val sortedByMonth = sortedByDescending { it.monthNumber }
        return sortedByMonth.sortedByDescending { it.year }
    }

}
