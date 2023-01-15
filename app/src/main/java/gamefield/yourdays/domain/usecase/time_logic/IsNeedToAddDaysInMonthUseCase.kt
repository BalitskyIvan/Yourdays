package gamefield.yourdays.domain.usecase.time_logic

import gamefield.yourdays.data.entity.Month
import java.util.Calendar

class IsNeedToAddDaysInMonthUseCase {

    private val calendar = Calendar.getInstance()

    operator fun invoke(month: Month): Boolean {
        val week = calendar.get(Calendar.WEEK_OF_MONTH)
        val currentDayInWeek = calendar.get(Calendar.DAY_OF_WEEK)

        if (month.weeks.size - 1 < week || month.weeks[week].days[currentDayInWeek - 1].emotion == null)
            return true

        return false
    }

}
