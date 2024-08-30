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

        val fillMonthIndex = monthList.getNeedToFillMonthIndex()

        return if (fillMonthIndex == null) {
            fillNewMonthUseCase.invoke()
            fetch(firstDayOfWeek)
        } else {
            if (isNeedToAddDaysInMonthUseCase.invoke(month = monthList[fillMonthIndex])) {
                fillDaysBeforeNowUseCase.invoke(month = monthList[fillMonthIndex])
                fetch(firstDayOfWeek)
            } else {
                monthList
                    .sortedByDescending { it.year + it.monthNumber }
            }
        }
    }

    private fun List<Month>.getNeedToFillMonthIndex(): Int? {
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        forEachIndexed { index, month ->
            if (month.monthNumber == currentMonth && month.year == currentYear) {
                return index
            }
        }
        return null
    }

}
