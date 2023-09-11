package gamefield.yourdays.domain.usecase.io

import android.content.Context
import androidx.lifecycle.MutableLiveData
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.usecase.time_logic.FillDaysBeforeNow
import gamefield.yourdays.domain.usecase.time_logic.FillNewMonthUseCase
import gamefield.yourdays.domain.usecase.time_logic.IsNeedToAddDaysInMonthUseCase
import gamefield.yourdays.extensions.selectCurrentDay
import gamefield.yourdays.presentation.screen.main_screen.view_model.DaySelectedContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

class GetAllMonthsListUseCase(
    context: Context,
    private val firstDayOfWeekChanged: MutableLiveData<Int>,
    private val mothListChangedEvent: MutableLiveData<List<Month>>,
    private val viewModelScope: CoroutineScope,
    private val daySelectedEvent: MutableLiveData<DaySelectedContainer>? = null,
    private val currentDaySelectedEvent: MutableLiveData<DaySelectedContainer>? = null
) {

    private val repository =
        Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())
    private val calendar = Calendar.getInstance()
    private val isNeedToAddDaysInMonthUseCase = IsNeedToAddDaysInMonthUseCase()
    private var isFirstTimeMonthFetched = true
    private val fillNewMonthUseCase: FillNewMonthUseCase = FillNewMonthUseCase(context)
    private val getCalendarFirstDayOfWeekUseCase = GetCalendarFirstDayOfWeekUseCase(context)
    private val fillDaysBeforeNow: FillDaysBeforeNow = FillDaysBeforeNow(context)

    operator fun invoke() {
        viewModelScope.launch(Dispatchers.IO) {
            val firstDayOfWeek: Int
            runBlocking {
                firstDayOfWeek = getCalendarFirstDayOfWeekUseCase.invoke()
                firstDayOfWeekChanged.postValue(firstDayOfWeek)
            }
            fetch(firstDayOfWeek)
        }
    }

    private suspend fun fetch(firstDayOfWeek: Int) {
        repository.getMonths().collect { monthList ->
            calendar.toInstant()

            val initData = monthList.getNeedToFillAndMonthIndex()
            val isNeedToFillNewMonth = initData.first
            val currentMonthIndex = initData.second

            if (isNeedToFillNewMonth) {
                fillNewMonthUseCase.invoke()
                fetch(firstDayOfWeek)
            } else {
                if (isNeedToAddDaysInMonthUseCase.invoke(month = monthList[currentMonthIndex])) {
                    fillDaysBeforeNow.invoke(month = monthList[currentMonthIndex])
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
        mothListChangedEvent.postValue(sortedMonthList)
        if (daySelectedEvent != null) {
            val selectedDay = sortedMonthList.selectCurrentDay(
                daySelectedContainer = daySelectedEvent.value,
                isSelectCurrentDay = isFirstTimeMonthFetched
            )
            currentDaySelectedEvent?.postValue(selectedDay)
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
