package gamefield.yourdays.domain.usecase.time_logic

import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.entity.Week
import gamefield.yourdays.data.repository.EmotionsRepository
import gamefield.yourdays.extensions.setEmptyDay
import java.util.*

class FillDaysBeforeNowUseCase(
    private val emotionsRepository: EmotionsRepository
) {

    private val calendar = Calendar.getInstance()

    suspend operator fun invoke(month: Month) {
        calendar.toInstant()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentWeek = calendar.get(Calendar.WEEK_OF_MONTH)

        calendar.set(currentYear, currentMonth, 1)

        while (isSameMonth(
                currentMonth = currentMonth,
                currentDay = currentDay,
                currentWeek = currentWeek
            )
        ) {

            val weekInMonth = calendar.get(Calendar.WEEK_OF_MONTH)
            var dayInWeekNumber = calendar.get(Calendar.DAY_OF_WEEK)

            while (isSameWeek(
                    currentMonth = currentMonth,
                    currentWeek = currentWeek,
                    currentDay = currentDay,
                    weekInMonth = weekInMonth
                )
            ) {
                if (weekInMonth > month.weeks.size) {
                    month.weeks.add(Week(getEmptyWeek()))
                }
                with(month.weeks[weekInMonth - 1].days[dayInWeekNumber - 1]) {
                    if (emotion == null) {
                        month.weeks[weekInMonth - 1].days.setEmptyDay(
                            dayInWeekNumber - 1,
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                    }
                }
                calendar.set(currentYear, currentMonth, calendar.get(Calendar.DAY_OF_MONTH) + 1)
                dayInWeekNumber = calendar.get(Calendar.DAY_OF_WEEK)
            }
        }
        emotionsRepository.updateMonth(month)
    }

    private fun isSameMonth(currentMonth: Int, currentDay: Int, currentWeek: Int): Boolean {
        return currentMonth == calendar.get(Calendar.MONTH) &&
                currentDay >= calendar.get(Calendar.DAY_OF_MONTH) &&
                currentWeek >= calendar.get(Calendar.WEEK_OF_MONTH)
    }

    private fun isSameWeek(
        currentMonth: Int,
        currentWeek: Int,
        currentDay: Int,
        weekInMonth: Int
    ): Boolean {
        return currentMonth == calendar.get(Calendar.MONTH) &&
                currentDay >= calendar.get(Calendar.DAY_OF_MONTH) &&
                weekInMonth == calendar.get(Calendar.WEEK_OF_MONTH) &&
                currentWeek >= calendar.get(Calendar.WEEK_OF_MONTH)
    }

    private fun getEmptyWeek(): MutableList<Day> {
        val days = mutableListOf<Day>()
        for (i in 0..6) {
            days.add(Day(i, 0))
        }
        return days
    }

}