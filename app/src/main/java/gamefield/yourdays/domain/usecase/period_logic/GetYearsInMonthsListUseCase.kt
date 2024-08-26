package gamefield.yourdays.domain.usecase.period_logic

import gamefield.yourdays.data.entity.Month
import java.util.Calendar

class GetYearsInMonthsListUseCase(
    private val calendar: Calendar
) {

    operator fun invoke(months: List<Month>): Set<String> {
        val years = mutableSetOf(calendar.get(Calendar.YEAR).toString())

        months.forEach { month ->
            years.add(month.year.toString())
        }
        return years
    }

}
