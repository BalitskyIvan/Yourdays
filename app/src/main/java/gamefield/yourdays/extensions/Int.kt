package gamefield.yourdays.extensions

import android.content.res.Resources
import gamefield.yourdays.R
import java.util.Calendar

fun Int.getMonthName(
    isUppercase: Boolean = true,
    isNeedToAddYear: Boolean = true,
    resources: Resources,
    year: Int = 0
): String {
    var name = with(resources) {
        when (this@getMonthName) {
            0 -> getString(R.string.january)
            1 -> getString(R.string.february)
            2 -> getString(R.string.march)
            3 -> getString(R.string.april)
            4 -> getString(R.string.may)
            5 -> getString(R.string.june)
            6 -> getString(R.string.july)
            7 -> getString(R.string.august)
            8 -> getString(R.string.september)
            9 -> getString(R.string.october)
            10 -> getString(R.string.november)
            11 -> getString(R.string.december)
            else -> ""
        }
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
