package gamefield.yourdays.ui.adapter

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gamefield.yourdays.R
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.extensions.getEmotionViewFromDay
import gamefield.yourdays.extensions.getMonthName

class MonthViewHolder(
    private val view: View,
    private val onDayClickedAction: (moth: Int, day: Int, year: Int, emotion: Emotion) -> Unit
): RecyclerView.ViewHolder(view) {

    private val monthTitle: TextView = view.findViewById(R.id.card_period_month_name)

    //WEEK MARKUP
    private val markupFirstDay: TextView = view.findViewById(R.id.markup_day_mon)
    private val markupSecondDay: TextView = view.findViewById(R.id.markup_day_tue)
    private val markupThirdDay: TextView = view.findViewById(R.id.markup_day_wed)
    private val markupFourthDay: TextView = view.findViewById(R.id.markup_day_thu)
    private val markupFifthDay: TextView = view.findViewById(R.id.markup_day_fri)
    private val markupSixthDay: TextView = view.findViewById(R.id.markup_day_sat)
    private val markupSeventhDay: TextView = view.findViewById(R.id.markup_day_sun)

    //FIRST WEEK
    private val first_week_first_day: FrameLayout = view.findViewById(R.id.first_week_mon)
    private val first_week_second_day: FrameLayout = view.findViewById(R.id.first_week_tue)
    private val first_week_third_day: FrameLayout = view.findViewById(R.id.first_week_wed)
    private val first_week_fourth_day: FrameLayout = view.findViewById(R.id.first_week_thu)
    private val first_week_fifth_day: FrameLayout = view.findViewById(R.id.first_week_fri)
    private val first_week_sixth_day: FrameLayout = view.findViewById(R.id.first_week_sat)
    private val first_week_seventh_day: FrameLayout = view.findViewById(R.id.first_week_sun)

    //SECOND WEEK
    private val second_week_layout: LinearLayout = view.findViewById(R.id.card_period_month_second_week)
    private val second_week_markup: TextView = view.findViewById(R.id.week_second_markup)
    private val second_week_first_day: FrameLayout = view.findViewById(R.id.second_week_mon)
    private val second_week_second_day: FrameLayout = view.findViewById(R.id.second_week_tue)
    private val second_week_third_day: FrameLayout = view.findViewById(R.id.second_week_wed)
    private val second_week_fourth_day: FrameLayout = view.findViewById(R.id.second_week_thu)
    private val second_week_fifth_day: FrameLayout = view.findViewById(R.id.second_week_fri)
    private val second_week_sixth_day: FrameLayout = view.findViewById(R.id.second_week_sat)
    private val second_week_seventh_day: FrameLayout = view.findViewById(R.id.second_week_sun)

    //THIRD WEEK
    private val third_week_layout: LinearLayout = view.findViewById(R.id.card_period_month_third_week)
    private val third_week_markup: TextView = view.findViewById(R.id.week_third_markup)
    private val third_week_first_day: FrameLayout = view.findViewById(R.id.third_week_mon)
    private val third_week_second_day: FrameLayout = view.findViewById(R.id.third_week_tue)
    private val third_week_third_day: FrameLayout = view.findViewById(R.id.third_week_wed)
    private val third_week_fourth_day: FrameLayout = view.findViewById(R.id.third_week_thu)
    private val third_week_fifth_day: FrameLayout = view.findViewById(R.id.third_week_fri)
    private val third_week_sixth_day: FrameLayout = view.findViewById(R.id.third_week_sat)
    private val third_week_seventh_day: FrameLayout = view.findViewById(R.id.third_week_sun)

    //FOURTH WEEK
    private val fourth_week_layout: LinearLayout = view.findViewById(R.id.card_period_month_fourth_week)
    private val fourth_week_markup: TextView = view.findViewById(R.id.week_fourth_markup)
    private val fourth_week_first_day: FrameLayout = view.findViewById(R.id.fourth_week_mon)
    private val fourth_week_second_day: FrameLayout = view.findViewById(R.id.fourth_week_tue)
    private val fourth_week_third_day: FrameLayout = view.findViewById(R.id.fourth_week_wed)
    private val fourth_week_fourth_day: FrameLayout = view.findViewById(R.id.fourth_week_thu)
    private val fourth_week_fifth_day: FrameLayout = view.findViewById(R.id.fourth_week_fri)
    private val fourth_week_sixth_day: FrameLayout = view.findViewById(R.id.fourth_week_sat)
    private val fourth_week_seventh_day: FrameLayout = view.findViewById(R.id.fourth_week_sun)

    //FIFTH WEEK
    private val fifth_week_layout: LinearLayout = view.findViewById(R.id.card_period_month_fifth_week)
    private val fifth_week_markup: TextView = view.findViewById(R.id.week_fifth_markup)
    private val fifth_week_first_day: FrameLayout = view.findViewById(R.id.fifth_week_mon)
    private val fifth_week_second_day: FrameLayout = view.findViewById(R.id.fifth_week_tue)
    private val fifth_week_third_day: FrameLayout = view.findViewById(R.id.fifth_week_wed)
    private val fifth_week_fourth_day: FrameLayout = view.findViewById(R.id.fifth_week_thu)
    private val fifth_week_fifth_day: FrameLayout = view.findViewById(R.id.fifth_week_fri)
    private val fifth_week_sixth_day: FrameLayout = view.findViewById(R.id.fifth_week_sat)
    private val fifth_week_seventh_day: FrameLayout = view.findViewById(R.id.fifth_week_sun)

    private val selectedDayDrawable = view.context.getDrawable(R.drawable.day_selector)

    fun bind(month: Month, firstDayOfWeek: Int) {

        val daysInWeek = setMarkupByFirstDayOfWeek(view.context, firstDayOfWeek)

        with(month) {
            monthTitle.text = monthNumber.getMonthName(context = view.context, year = month.year)

            if (weeks.isEmpty())
                return
            weeks[0].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { first_week_first_day.setEmotion(it, monthNumber, year) }
            weeks[0].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { first_week_second_day.setEmotion(it, monthNumber, year) }
            weeks[0].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { first_week_third_day.setEmotion(it, monthNumber, year) }
            weeks[0].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { first_week_fourth_day.setEmotion(it, monthNumber, year) }
            weeks[0].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { first_week_fifth_day.setEmotion(it, monthNumber, year) }
            weeks[0].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { first_week_sixth_day.setEmotion(it, monthNumber, year) }
            weeks[0].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { first_week_seventh_day.setEmotion(it, monthNumber, year) }

            if (weeks.size < 2) {
                second_week_layout.visibility = View.GONE
                second_week_markup.visibility = View.GONE
                third_week_layout.visibility = View.GONE
                third_week_markup.visibility = View.GONE
                fourth_week_layout.visibility = View.GONE
                fourth_week_markup.visibility = View.GONE
                fifth_week_layout.visibility = View.GONE
                fifth_week_markup.visibility = View.GONE
                return
            } else {
                second_week_markup.visibility = View.VISIBLE
                second_week_layout.visibility = View.VISIBLE
            }

            weeks[1].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { second_week_first_day.setEmotion(it, monthNumber, year) }
            weeks[1].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { second_week_second_day.setEmotion(it, monthNumber, year) }
            weeks[1].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { second_week_third_day.setEmotion(it, monthNumber, year) }
            weeks[1].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { second_week_fourth_day.setEmotion(it, monthNumber, year) }
            weeks[1].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { second_week_fifth_day.setEmotion(it, monthNumber, year) }
            weeks[1].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { second_week_sixth_day.setEmotion(it, monthNumber, year) }
            weeks[1].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { second_week_seventh_day.setEmotion(it, monthNumber, year) }

            if (weeks.size < 3) {
                third_week_layout.visibility = View.GONE
                third_week_markup.visibility = View.GONE
                fourth_week_layout.visibility = View.GONE
                fourth_week_markup.visibility = View.GONE
                fifth_week_layout.visibility = View.GONE
                fifth_week_markup.visibility = View.GONE
                return
            } else {
                third_week_markup.visibility = View.VISIBLE
                third_week_layout.visibility = View.VISIBLE
            }
            weeks[2].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { third_week_first_day.setEmotion(it, monthNumber, year) }
            weeks[2].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { third_week_second_day.setEmotion(it, monthNumber, year) }
            weeks[2].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { third_week_third_day.setEmotion(it, monthNumber, year) }
            weeks[2].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { third_week_fourth_day.setEmotion(it, monthNumber, year) }
            weeks[2].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { third_week_fifth_day.setEmotion(it, monthNumber, year) }
            weeks[2].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { third_week_sixth_day.setEmotion(it, monthNumber, year) }
            weeks[2].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { third_week_seventh_day.setEmotion(it, monthNumber, year) }

            if (weeks.size < 4) {
                fourth_week_layout.visibility = View.GONE
                fourth_week_markup.visibility = View.GONE
                fifth_week_layout.visibility = View.GONE
                fifth_week_markup.visibility = View.GONE
                return
            } else {
                fourth_week_markup.visibility = View.VISIBLE
                fourth_week_layout.visibility = View.VISIBLE
            }

            weeks[3].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { fourth_week_first_day.setEmotion(it, monthNumber, year) }
            weeks[3].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { fourth_week_second_day.setEmotion(it, monthNumber, year) }
            weeks[3].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { fourth_week_third_day.setEmotion(it, monthNumber, year) }
            weeks[3].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { fourth_week_fourth_day.setEmotion(it, monthNumber, year) }
            weeks[3].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { fourth_week_fifth_day.setEmotion(it, monthNumber, year) }
            weeks[3].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { fourth_week_sixth_day.setEmotion(it, monthNumber, year) }
            weeks[3].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { fourth_week_seventh_day.setEmotion(it, monthNumber, year) }

            if (weeks.size < 5) {
                fifth_week_layout.visibility = View.GONE
                fifth_week_markup.visibility = View.GONE
                return
            } else {
                fifth_week_markup.visibility = View.VISIBLE
                fifth_week_layout.visibility = View.VISIBLE
            }
            weeks[4].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { fifth_week_first_day.setEmotion(it, monthNumber, year) }
            weeks[4].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { fifth_week_second_day.setEmotion(it, monthNumber, year) }
            weeks[4].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { fifth_week_third_day.setEmotion(it, monthNumber, year) }
            weeks[4].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { fifth_week_fourth_day.setEmotion(it, monthNumber, year) }
            weeks[4].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { fifth_week_fifth_day.setEmotion(it, monthNumber, year) }
            weeks[4].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { fifth_week_sixth_day.setEmotion(it, monthNumber, year) }
            weeks[4].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { fifth_week_seventh_day.setEmotion(it, monthNumber, year) }
        }
    }

    private fun setMarkupByFirstDayOfWeek(context: Context, firstDayOfWeek: Int): DaysInWeek {
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

    private fun FrameLayout.setEmotion(dayContainer: DayContainer, month: Int, year: Int) {
        removeAllViews()
        addView(dayContainer.view)
        val day = dayContainer.dayNumber

        if (dayContainer.isSelected) {
            this.background = selectedDayDrawable
        } else {
            this.background = null
        }
        setOnClickListener {
            onDayClickedAction.invoke(month, day, year, dayContainer.emotion)
        }
    }

    private data class DaysInWeek(
        val first: Int = 0,
        val second: Int = 1,
        val third: Int = 2,
        val fourth: Int = 3,
        val fifth: Int = 4,
        val sixth: Int = 5,
        val seventh: Int = 6
    )
}
