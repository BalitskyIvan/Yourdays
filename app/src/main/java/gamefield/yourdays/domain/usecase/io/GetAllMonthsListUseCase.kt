package gamefield.yourdays.domain.usecase.io

import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.repository.EmotionsRepository
import gamefield.yourdays.domain.usecase.time_logic.FillDaysBeforeNowUseCase
import gamefield.yourdays.domain.usecase.time_logic.FillNewMonthUseCase
import gamefield.yourdays.domain.usecase.time_logic.IsNeedToAddDaysInMonthUseCase
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

    suspend operator fun invoke(): List<Month> {
        return withContext(Dispatchers.IO) {
            val firstDayOfWeek = getCalendarFirstDayOfWeekUseCase.invoke()
            fetch(firstDayOfWeek)
        }
    }

    private suspend fun fetch(firstDayOfWeek: Int): List<Month> {
        val monthList = emotionsRepository.getMonths()
        calendar.toInstant()

        val initData = monthList.getNeedToFillAndMonthIndex()
        val isNeedToFillNewMonth = initData.first
        val currentMonthIndex = initData.second

        return if (isNeedToFillNewMonth) {
            fillNewMonthUseCase.invoke()
            fetch(firstDayOfWeek)
        } else {
            if (isNeedToAddDaysInMonthUseCase.invoke(month = monthList[currentMonthIndex])) {
                fillDaysBeforeNowUseCase.invoke(month = monthList[currentMonthIndex])
                fetch(firstDayOfWeek)
            } else {
                monthList
                    .sortedByDescending { it.year + it.monthNumber }
            }
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

}
