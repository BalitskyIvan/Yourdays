package gamefield.yourdays.domain.usecase.period_logic

import android.content.Context
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.extensions.getMonthName
import java.util.*

class GetMonthsInMonthsListUseCase(
    private val context: Context
) {

    private val calendar = Calendar.getInstance()

    operator fun invoke(months: List<Month>): Set<String> {
        val monthNames = mutableSetOf(calendar.get(Calendar.MONTH).getMonthName(isUppercase = false, context = context))

        months.forEach { month ->
            monthNames.add(month.monthNumber.getMonthName(isUppercase = false, context = context))
        }
        return monthNames
    }

}
