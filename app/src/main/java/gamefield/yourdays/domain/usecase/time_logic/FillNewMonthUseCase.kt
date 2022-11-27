package gamefield.yourdays.domain.usecase.time_logic

import android.content.Context
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository
import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.entity.Week
import gamefield.yourdays.extensions.setEmptyDay
import java.util.*

class FillNewMonthUseCase(context: Context) {

    private val calendar = Calendar.getInstance()
    private val repository =
        Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())

    operator fun invoke() {
        var dayInMonth = 0
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val weeksList = mutableListOf<Week>()
        calendar.set(currentYear, currentMonth, dayInMonth)

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        while (dayInMonth < daysInMonth && dayInMonth < currentDay) {
            calendar.set(currentYear, currentMonth, dayInMonth)

            val days = getEmptyWeek()
            var dayInWeekNumber = calendar.get(Calendar.DAY_OF_WEEK)
            while (dayInWeekNumber <= 7 && dayInMonth < daysInMonth && dayInMonth < currentDay) {
                days.setEmptyDay(dayInWeekNumber - 1)
                dayInWeekNumber++
                dayInMonth++
            }
            weeksList.add(Week(days))
        }
        repository.addMonth(
            Month(
                id = UUID.randomUUID(),
                monthNumber = currentMonth,
                weeks = weeksList,
                year = currentYear
            )
        )
    }

    private fun getEmptyWeek(): MutableList<Day> {
        val days = mutableListOf<Day>()
        for (i in 0..6) {
            days.add(Day(i))
        }
        return days
    }

}
