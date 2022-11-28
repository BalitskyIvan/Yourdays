package gamefield.yourdays.extensions

import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.utils.main_screen.DaySelectedContainer
import java.util.Calendar

fun Month.getDayFromNumberInMonth(dayNumber: Int): Day? {
    var index = 0
    for (week in weeks) {
        for (day in week.days) {
            if (dayNumber == index)
                return day
            if (day.emotion != null) {
                index++
                if (dayNumber == index)
                    return day
            }
        }
    }
    return null
}

fun List<Month>.selectCurrentDay(daySelectedContainer: DaySelectedContainer?, isSelectCurrentDay: Boolean): DaySelectedContainer {
    val calendar = Calendar.getInstance()
    val currentMonthNumber: Int
    val currentDayOfMonth: Int
    var selectedDayEmotion: Emotion? = null
    if (!isSelectCurrentDay && daySelectedContainer != null) {
        currentMonthNumber = daySelectedContainer.month
        currentDayOfMonth = daySelectedContainer.day
    } else {
        currentMonthNumber = calendar.get(Calendar.MONTH)
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    }

    forEach { month ->
        if (month.monthNumber == currentMonthNumber) {
            val searchedDay = month.getDayFromNumberInMonth(currentDayOfMonth)?.apply { isSelected = true }
            selectedDayEmotion = searchedDay?.emotion
        }
    }
    return DaySelectedContainer(currentDayOfMonth, currentMonthNumber, selectedDayEmotion)
}