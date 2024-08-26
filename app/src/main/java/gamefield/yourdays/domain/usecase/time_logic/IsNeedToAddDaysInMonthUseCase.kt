package gamefield.yourdays.domain.usecase.time_logic

import gamefield.yourdays.data.entity.Month
import java.util.Calendar

class IsNeedToAddDaysInMonthUseCase(
    private val calendar: Calendar
) {

    operator fun invoke(month: Month): Boolean {
        calendar.toInstant()
        val week = calendar.get(Calendar.WEEK_OF_MONTH)
        val currentDayInWeek = calendar.get(Calendar.DAY_OF_WEEK)

        return month.weeks.size < week || month.weeks[week - 1].days[currentDayInWeek - 1].emotion == null
    }

}
