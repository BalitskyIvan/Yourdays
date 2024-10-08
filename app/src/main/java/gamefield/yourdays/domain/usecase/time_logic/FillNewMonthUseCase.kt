package gamefield.yourdays.domain.usecase.time_logic

import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.entity.Week
import gamefield.yourdays.data.repository.EmotionsRepository
import gamefield.yourdays.extensions.setEmptyDay
import java.util.*

class FillNewMonthUseCase(
    private val calendar: Calendar,
    private val emotionsRepository: EmotionsRepository
) {

    suspend operator fun invoke() {
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(currentYear, currentMonth, 1)

        val weeksList = mutableListOf<Week>()

        while (isSameMonth(currentMonth = currentMonth, currentDay = currentDay)) {

            val currentWeek = calendar.get(Calendar.WEEK_OF_MONTH)
            val days = getEmptyWeek()
            var dayInWeekNumber = calendar.get(Calendar.DAY_OF_WEEK)

            while (isSameWeek(
                    currentMonth = currentMonth,
                    currentWeek = currentWeek,
                    currentDay = currentDay
                )
            ) {
                days.setEmptyDay(dayInWeekNumber - 1, calendar.get(Calendar.DAY_OF_MONTH))
                calendar.set(currentYear, currentMonth, calendar.get(Calendar.DAY_OF_MONTH) + 1)
                dayInWeekNumber = calendar.get(Calendar.DAY_OF_WEEK)
            }
            weeksList.add(Week(days))
        }
        emotionsRepository.addMonth(
            Month(
                id = UUID.randomUUID(),
                monthNumber = currentMonth,
                weeks = weeksList,
                year = currentYear
            )
        )
    }

    private fun isSameMonth(currentMonth: Int, currentDay: Int): Boolean {
        return currentMonth == calendar.get(Calendar.MONTH) &&
                currentDay >= calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun isSameWeek(currentMonth: Int, currentWeek: Int, currentDay: Int): Boolean {
        return currentMonth == calendar.get(Calendar.MONTH) &&
                currentDay >= calendar.get(Calendar.DAY_OF_MONTH) &&
                currentWeek == calendar.get(Calendar.WEEK_OF_MONTH)
    }

    private fun getEmptyWeek(): MutableList<Day> {
        val days = mutableListOf<Day>()
        for (i in 0..6) {
            days.add(Day(i, 0))
        }
        return days
    }

}
