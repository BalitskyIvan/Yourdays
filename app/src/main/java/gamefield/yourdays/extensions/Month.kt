package gamefield.yourdays.extensions

import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.utils.main_screen.DaySelectedContainer
import java.util.Calendar

fun Month.getDayFromNumberInMonth(dayNumber: Int): Day? {
    for (week in weeks) {
        for (day in week.days) {
            if (dayNumber == day.dayInMonth)
                return day
        }
    }
    return null
}

fun List<Month>.selectCurrentDay(
    daySelectedContainer: DaySelectedContainer?,
    isSelectCurrentDay: Boolean
): DaySelectedContainer {
    val calendar = Calendar.getInstance()

    val currentMonthNumber: Int
    val currentDayOfMonth: Int
    val currentYear: Int

    var selectedDayEmotion: Emotion? = null
    if (!isSelectCurrentDay && daySelectedContainer != null) {
        currentMonthNumber = daySelectedContainer.month
        currentDayOfMonth = daySelectedContainer.day
        currentYear = daySelectedContainer.year
    } else {
        currentMonthNumber = calendar.get(Calendar.MONTH)
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        currentYear = calendar.get(Calendar.YEAR)
    }

    forEach { month ->
        if (month.monthNumber == currentMonthNumber && month.year == currentYear) {
            val searchedDay =
                month.getDayFromNumberInMonth(currentDayOfMonth)?.apply { isSelected = true }
            selectedDayEmotion = searchedDay?.emotion
        }
    }
    return DaySelectedContainer(
        currentDayOfMonth,
        currentMonthNumber,
        currentYear,
        selectedDayEmotion
    )
}