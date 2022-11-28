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
import gamefield.yourdays.utils.main_screen.DaySelectedContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GetAllMonthsListUseCase(
    context: Context,
    private val mothListChangedEvent: MutableLiveData<List<Month>>,
    private val viewModelScope: CoroutineScope,
    private val daySelectedEvent: MutableLiveData<DaySelectedContainer>
) {

    private val repository =
        Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())
    private val calendar = Calendar.getInstance()
    private val isNeedToAddDaysInMonthUseCase = IsNeedToAddDaysInMonthUseCase()
    private var isFirstTimeMonthFetched = true
    private val fillNewMonthUseCase: FillNewMonthUseCase = FillNewMonthUseCase(context)
    private val fillDaysBeforeNow: FillDaysBeforeNow = FillDaysBeforeNow(context)

    operator fun invoke() {
        viewModelScope.launch(Dispatchers.IO) {
            fetch()
        }
    }

    private suspend fun fetch() {
        repository.getMonths().collect { monthList ->
            calendar.toInstant()

            val initData = monthList.getNeedToFillAndMonthIndex()
            val isNeedToFillNewMonth = initData.first
            val currentMonthIndex = initData.second

            if (isNeedToFillNewMonth) {
                fillNewMonthUseCase.invoke()
                fetch()
            } else {
                if (isNeedToAddDaysInMonthUseCase.invoke(monthList[currentMonthIndex])) {
                    fillDaysBeforeNow.invoke(month = monthList[currentMonthIndex])
                    fetch()
                } else {
                    monthList.putMonthList()
                }
            }
        }
    }

    private fun List<Month>.putMonthList() {
        val sortedMonthList = sortMonths()

        sortedMonthList.selectCurrentDay(
            daySelectedContainer = daySelectedEvent.value,
            isSelectCurrentDay = isFirstTimeMonthFetched
        )
        isFirstTimeMonthFetched = false
        mothListChangedEvent.postValue(sortedMonthList)
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
