package gamefield.yourdays.domain.usecase.period_logic

import android.content.Context
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.extensions.getMonthName
import java.util.*

class GetMonthsInMonthsListUseCase(
    private val context: Context
) {

    private val calendar = Calendar.getInstance()

    operator fun invoke(months: List<Month>): Set<Pair<String, Int>> {
        val currentMonth = calendar.get(Calendar.MONTH)
        val monthNames = mutableSetOf(
            Pair(
                currentMonth.getMonthName(isUppercase = false, context = context),
                currentMonth
            )
        )

        months.forEach { month ->
            monthNames.add(
                Pair(
                    month.monthNumber.getMonthName(
                        isUppercase = false,
                        context = context
                    ), month.monthNumber
                )
            )
        }
        return monthNames
    }

}
