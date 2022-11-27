package gamefield.yourdays.domain.usecase.time_logic

import gamefield.yourdays.data.entity.Month
import java.util.Calendar

class IsNeedToAddDaysInMonth {

    private val calendar = Calendar.getInstance()

    operator fun invoke(month: Month): Boolean {
        var week = 0
        var dayInMonth = 0
        var dayInWeek = 0
        val currentDayInMonth = calendar.get(Calendar.DAY_OF_MONTH)

        while (month.weeks.size > week && dayInMonth < currentDayInMonth) {
            if (dayInWeek > 6) {
                week++
                dayInWeek = 0
            }
            dayInWeek++
            dayInMonth++
        }

        if (month.weeks[week].days[dayInWeek].emotion == null)
            return true

        return false
    }

}
