package gamefield.yourdays.extensions

import android.content.Context
import gamefield.yourdays.R
import java.util.Calendar

fun Int.getMonthName(
    isUppercase: Boolean = true,
    isNeedToAddYear: Boolean = true,
    context: Context,
    year: Int = 0
): String {
    var name = when (this) {
        0 -> context.getString(R.string.january)
        1 -> context.getString(R.string.february)
        2 -> context.getString(R.string.march)
        3 -> context.getString(R.string.april)
        4 -> context.getString(R.string.may)
        5 -> context.getString(R.string.june)
        6 -> context.getString(R.string.july)
        7 -> context.getString(R.string.august)
        8 -> context.getString(R.string.september)
        9 -> context.getString(R.string.october)
        10 -> context.getString(R.string.november)
        11 -> context.getString(R.string.december)
        else -> ""
    }
    if (isUppercase) {
        val currentYear = Calendar
            .getInstance()
            .get(Calendar.YEAR)
        name = name.uppercase()
        if (isNeedToAddYear && currentYear != year)
            name = "$name $year"
    }
    return name
}
