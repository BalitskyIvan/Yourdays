package gamefield.yourdays.domain.usecase.period_logic

import android.content.res.Resources
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.extensions.getMonthName
import java.util.*

class GetMonthsInMonthsListUseCase(
    private val calendar: Calendar,
    private val resources: Resources
) {

    operator fun invoke(months: List<Month>): Set<Pair<String, Int>> {
        val currentMonth = calendar.get(Calendar.MONTH)
        val monthNames = mutableSetOf(
            Pair(
                currentMonth.getMonthName(isUppercase = false, resources = resources),
                currentMonth
            )
        )

        months.forEach { month ->
            monthNames.add(
                Pair(
                    month.monthNumber.getMonthName(
                        isUppercase = false,
                        resources = resources
                    ), month.monthNumber
                )
            )
        }
        return monthNames
    }

}
