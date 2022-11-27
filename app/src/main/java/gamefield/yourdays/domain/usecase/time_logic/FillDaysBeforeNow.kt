package gamefield.yourdays.domain.usecase.time_logic

import android.content.Context
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository
import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.entity.Week
import gamefield.yourdays.extensions.setEmptyDay
import java.util.*

class FillDaysBeforeNow(context: Context) {

    private val calendar = Calendar.getInstance()
    private val repository =
        Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())

    operator fun invoke(month: Month) {
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        var week = 0
        var dayInWeek = 0
        var startDay = 0

        while (month.weeks.size != week && month.weeks[week].days[dayInWeek].emotion == null) {
            startDay++
            dayInWeek++
            if (month.weeks[week].days.size == dayInWeek) {
                week++
                dayInWeek = 0
            }
        }
        while (month.weeks.size != week && month.weeks[week].days[dayInWeek].emotion != null) {
            startDay++
            dayInWeek++
            if (month.weeks[week].days.size == dayInWeek) {
                week++
                dayInWeek = 0
            }
        }
        while (startDay <= currentDay) {
            if (month.weeks.size == week) {
                month.weeks = month.weeks.toMutableList().apply { add(Week(getEmptyWeek())) }
                dayInWeek = 0
            }
            month.weeks[week].days = month.weeks[week].days.toMutableList().apply { setEmptyDay(dayInWeek) }
            dayInWeek++
            startDay++
        }
        repository.updateMonth(month)
    }

    private fun getEmptyWeek(): MutableList<Day> {
        val days = mutableListOf<Day>()
        for (i in 0..6) {
            days.add(Day(i))
        }
        return days
    }

}