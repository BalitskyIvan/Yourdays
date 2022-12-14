package gamefield.yourdays.domain.usecase.ui

import android.content.Context
import android.widget.TextView
import gamefield.yourdays.R

class GetMonthMarkupByFirstDayOfWeekUseCase(
    private val markupFirstDay: TextView,
    private val markupSecondDay: TextView,
    private val markupThirdDay: TextView,
    private val markupFourthDay: TextView,
    private val markupFifthDay: TextView,
    private val markupSixthDay: TextView,
    private val markupSeventhDay: TextView
) {

    operator fun invoke(context: Context, firstDayOfWeek: Int): DaysInWeek {
        if (firstDayOfWeek == 1) {
            markupFirstDay.text = context.getString(R.string.sun)
            markupSecondDay.text = context.getString(R.string.mon)
            markupThirdDay.text = context.getString(R.string.tue)
            markupFourthDay.text = context.getString(R.string.wed)
            markupFifthDay.text = context.getString(R.string.thu)
            markupSixthDay.text = context.getString(R.string.fri)
            markupSeventhDay.text = context.getString(R.string.sat)

            return DaysInWeek()
        } else {
            markupFirstDay.text = context.getString(R.string.mon)
            markupSecondDay.text = context.getString(R.string.tue)
            markupThirdDay.text = context.getString(R.string.wed)
            markupFourthDay.text = context.getString(R.string.thu)
            markupFifthDay.text = context.getString(R.string.fri)
            markupSixthDay.text = context.getString(R.string.sat)
            markupSeventhDay.text = context.getString(R.string.sun)

            return DaysInWeek(
                first = 1,
                second = 2,
                third = 3,
                fourth = 4,
                fifth = 5,
                sixth = 6,
                seventh = 0
            )
        }
    }
}
