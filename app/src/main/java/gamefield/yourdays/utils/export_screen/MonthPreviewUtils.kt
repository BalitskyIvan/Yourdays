package gamefield.yourdays.utils.export_screen

import android.view.View
import gamefield.yourdays.data.entity.Week
import gamefield.yourdays.databinding.FragmentMonthPreviewFragmetBinding

object MonthPreviewUtils {

    fun setAllAfterFirstWeekVisibility(
        binding: FragmentMonthPreviewFragmetBinding,
        weeks: List<Week>
    ): Boolean {
        with(binding) {
            if (weeks.size < 2) {
                cardPeriodMonthSecondWeek.visibility = View.GONE
                weekSecondMarkup.visibility = View.GONE
                cardPeriodMonthThirdWeek.visibility = View.GONE
                weekThirdMarkup.visibility = View.GONE
                cardPeriodMonthFourthWeek.visibility = View.GONE
                weekFourthMarkup.visibility = View.GONE
                cardPeriodMonthFifthWeek.visibility = View.GONE
                weekFifthMarkup.visibility = View.GONE
                return false
            } else {
                cardPeriodMonthSecondWeek.visibility = View.VISIBLE
                weekSecondMarkup.visibility = View.VISIBLE
                return true
            }
        }
    }

    fun setAllAfterSecondWeekVisibility(
        binding: FragmentMonthPreviewFragmetBinding,
        weeks: List<Week>
    ): Boolean {
        with(binding) {
            if (weeks.size < 3) {
                cardPeriodMonthThirdWeek.visibility = View.GONE
                weekThirdMarkup.visibility = View.GONE
                cardPeriodMonthFourthWeek.visibility = View.GONE
                weekFourthMarkup.visibility = View.GONE
                cardPeriodMonthFifthWeek.visibility = View.GONE
                weekFifthMarkup.visibility = View.GONE
                return false
            } else {
                cardPeriodMonthThirdWeek.visibility = View.VISIBLE
                weekThirdMarkup.visibility = View.VISIBLE
                return true
            }
        }
    }

    fun setAllAfterThirdWeekVisibility(
        binding: FragmentMonthPreviewFragmetBinding,
        weeks: List<Week>
    ): Boolean {
        with(binding) {
            if (weeks.size < 4) {
                cardPeriodMonthFourthWeek.visibility = View.GONE
                weekFourthMarkup.visibility = View.GONE
                cardPeriodMonthFifthWeek.visibility = View.GONE
                weekFifthMarkup.visibility = View.GONE
                return false
            } else {
                cardPeriodMonthFourthWeek.visibility = View.VISIBLE
                weekFourthMarkup.visibility = View.VISIBLE
                return true
            }
        }
    }

    fun setAllAfterFourthWeekVisibility(
        binding: FragmentMonthPreviewFragmetBinding,
        weeks: List<Week>
    ): Boolean {
        with(binding) {
            if (weeks.size < 5) {
                cardPeriodMonthFifthWeek.visibility = View.GONE
                weekFifthMarkup.visibility = View.GONE
                return false
            } else {
                cardPeriodMonthFifthWeek.visibility = View.VISIBLE
                weekFifthMarkup.visibility = View.VISIBLE
                return true
            }
        }
    }

}
