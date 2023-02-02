package gamefield.yourdays.ui.adapter

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import gamefield.yourdays.R
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.domain.usecase.ui.GetMonthMarkupByFirstDayOfWeekUseCase
import gamefield.yourdays.extensions.getEmotionViewFromDay
import gamefield.yourdays.extensions.getMonthName

class MonthViewHolder(
    private val view: View,
    private val onDayClickedAction: (moth: Int, day: Int, year: Int, emotion: Emotion) -> Unit
): RecyclerView.ViewHolder(view) {

    private val monthTitle: TextView = view.findViewById(R.id.card_period_month_name)

    private val getMonthMarkupByFirstDayOfWeekUseCase = GetMonthMarkupByFirstDayOfWeekUseCase(
        view.findViewById(R.id.markup_day_mon),
        view.findViewById(R.id.markup_day_tue),
        view.findViewById(R.id.markup_day_wed),
        view.findViewById(R.id.markup_day_thu),
        view.findViewById(R.id.markup_day_fri),
        view.findViewById(R.id.markup_day_sat),
        view.findViewById(R.id.markup_day_sun)
    )

    //FIRST WEEK
    private val first_week_first_day: FrameLayout = view.findViewById(R.id.first_week_mon)
    private val first_week_second_day: FrameLayout = view.findViewById(R.id.first_week_tue)
    private val first_week_third_day: FrameLayout = view.findViewById(R.id.first_week_wed)
    private val first_week_fourth_day: FrameLayout = view.findViewById(R.id.first_week_thu)
    private val first_week_fifth_day: FrameLayout = view.findViewById(R.id.first_week_fri)
    private val first_week_sixth_day: FrameLayout = view.findViewById(R.id.first_week_sat)
    private val first_week_seventh_day: FrameLayout = view.findViewById(R.id.first_week_sun)

    private val first_week_first_day_date: TextView = view.findViewById(R.id.first_week_mon_date)
    private val first_week_second_day_date: TextView = view.findViewById(R.id.first_week_tue_date)
    private val first_week_third_day_date: TextView = view.findViewById(R.id.first_week_wed_date)
    private val first_week_fourth_day_date: TextView = view.findViewById(R.id.first_week_thu_date)
    private val first_week_fifth_day_date: TextView = view.findViewById(R.id.first_week_fri_date)
    private val first_week_sixth_day_date: TextView = view.findViewById(R.id.first_week_sat_date)
    private val first_week_seventh_day_date: TextView = view.findViewById(R.id.first_week_sun_date)


    //SECOND WEEK
    private val second_week_layout: ConstraintLayout = view.findViewById(R.id.card_period_month_second_week)
    private val second_week_first_day: FrameLayout = view.findViewById(R.id.second_week_mon)
    private val second_week_second_day: FrameLayout = view.findViewById(R.id.second_week_tue)
    private val second_week_third_day: FrameLayout = view.findViewById(R.id.second_week_wed)
    private val second_week_fourth_day: FrameLayout = view.findViewById(R.id.second_week_thu)
    private val second_week_fifth_day: FrameLayout = view.findViewById(R.id.second_week_fri)
    private val second_week_sixth_day: FrameLayout = view.findViewById(R.id.second_week_sat)
    private val second_week_seventh_day: FrameLayout = view.findViewById(R.id.second_week_sun)

    private val second_week_first_day_date: TextView = view.findViewById(R.id.second_week_mon_date)
    private val second_week_second_day_date: TextView = view.findViewById(R.id.second_week_tue_date)
    private val second_week_third_day_date: TextView = view.findViewById(R.id.second_week_wed_date)
    private val second_week_fourth_day_date: TextView = view.findViewById(R.id.second_week_thu_date)
    private val second_week_fifth_day_date: TextView = view.findViewById(R.id.second_week_fri_date)
    private val second_week_sixth_day_date: TextView = view.findViewById(R.id.second_week_sat_date)
    private val second_week_seventh_day_date: TextView = view.findViewById(R.id.second_week_sun_date)


    //THIRD WEEK
    private val third_week_layout: ConstraintLayout = view.findViewById(R.id.card_period_month_third_week)
    private val third_week_first_day: FrameLayout = view.findViewById(R.id.third_week_mon)
    private val third_week_second_day: FrameLayout = view.findViewById(R.id.third_week_tue)
    private val third_week_third_day: FrameLayout = view.findViewById(R.id.third_week_wed)
    private val third_week_fourth_day: FrameLayout = view.findViewById(R.id.third_week_thu)
    private val third_week_fifth_day: FrameLayout = view.findViewById(R.id.third_week_fri)
    private val third_week_sixth_day: FrameLayout = view.findViewById(R.id.third_week_sat)
    private val third_week_seventh_day: FrameLayout = view.findViewById(R.id.third_week_sun)

    private val third_week_first_day_date: TextView = view.findViewById(R.id.third_week_mon_date)
    private val third_week_second_day_date: TextView = view.findViewById(R.id.third_week_tue_date)
    private val third_week_third_day_date: TextView = view.findViewById(R.id.third_week_wed_date)
    private val third_week_fourth_day_date: TextView = view.findViewById(R.id.third_week_thu_date)
    private val third_week_fifth_day_date: TextView = view.findViewById(R.id.third_week_fri_date)
    private val third_week_sixth_day_date: TextView = view.findViewById(R.id.third_week_sat_date)
    private val third_week_seventh_day_date: TextView = view.findViewById(R.id.third_week_sun_date)


    //FOURTH WEEK
    private val fourth_week_layout: ConstraintLayout = view.findViewById(R.id.card_period_month_fourth_week)
    private val fourth_week_first_day: FrameLayout = view.findViewById(R.id.fourth_week_mon)
    private val fourth_week_second_day: FrameLayout = view.findViewById(R.id.fourth_week_tue)
    private val fourth_week_third_day: FrameLayout = view.findViewById(R.id.fourth_week_wed)
    private val fourth_week_fourth_day: FrameLayout = view.findViewById(R.id.fourth_week_thu)
    private val fourth_week_fifth_day: FrameLayout = view.findViewById(R.id.fourth_week_fri)
    private val fourth_week_sixth_day: FrameLayout = view.findViewById(R.id.fourth_week_sat)
    private val fourth_week_seventh_day: FrameLayout = view.findViewById(R.id.fourth_week_sun)

    private val fourth_week_first_day_date: TextView = view.findViewById(R.id.fourth_week_mon_date)
    private val fourth_week_second_day_date: TextView = view.findViewById(R.id.fourth_week_tue_date)
    private val fourth_week_third_day_date: TextView = view.findViewById(R.id.fourth_week_wed_date)
    private val fourth_week_fourth_day_date: TextView = view.findViewById(R.id.fourth_week_thu_date)
    private val fourth_week_fifth_day_date: TextView = view.findViewById(R.id.fourth_week_fri_date)
    private val fourth_week_sixth_day_date: TextView = view.findViewById(R.id.fourth_week_sat_date)
    private val fourth_week_seventh_day_date: TextView = view.findViewById(R.id.fourth_week_sun_date)

    //FIFTH WEEK
    private val fifth_week_layout: ConstraintLayout = view.findViewById(R.id.card_period_month_fifth_week)
    private val fifth_week_first_day: FrameLayout = view.findViewById(R.id.fifth_week_mon)
    private val fifth_week_second_day: FrameLayout = view.findViewById(R.id.fifth_week_tue)
    private val fifth_week_third_day: FrameLayout = view.findViewById(R.id.fifth_week_wed)
    private val fifth_week_fourth_day: FrameLayout = view.findViewById(R.id.fifth_week_thu)
    private val fifth_week_fifth_day: FrameLayout = view.findViewById(R.id.fifth_week_fri)
    private val fifth_week_sixth_day: FrameLayout = view.findViewById(R.id.fifth_week_sat)
    private val fifth_week_seventh_day: FrameLayout = view.findViewById(R.id.fifth_week_sun)

    private val fifth_week_first_day_date: TextView = view.findViewById(R.id.fifth_week_mon_date)
    private val fifth_week_second_day_date: TextView = view.findViewById(R.id.fifth_week_tue_date)
    private val fifth_week_third_day_date: TextView = view.findViewById(R.id.fifth_week_wed_date)
    private val fifth_week_fourth_day_date: TextView = view.findViewById(R.id.fifth_week_thu_date)
    private val fifth_week_fifth_day_date: TextView = view.findViewById(R.id.fifth_week_fri_date)
    private val fifth_week_sixth_day_date: TextView = view.findViewById(R.id.fifth_week_sat_date)
    private val fifth_week_seventh_day_date: TextView = view.findViewById(R.id.fifth_week_sun_date)

    //SIXTH WEEK
    private val sixth_week_layout: ConstraintLayout = view.findViewById(R.id.card_period_month_sixth_week)
    private val sixth_week_first_day: FrameLayout = view.findViewById(R.id.sixth_week_mon)
    private val sixth_week_second_day: FrameLayout = view.findViewById(R.id.sixth_week_tue)
    private val sixth_week_third_day: FrameLayout = view.findViewById(R.id.sixth_week_wed)
    private val sixth_week_fourth_day: FrameLayout = view.findViewById(R.id.sixth_week_thu)
    private val sixth_week_fifth_day: FrameLayout = view.findViewById(R.id.sixth_week_fri)
    private val sixth_week_sixth_day: FrameLayout = view.findViewById(R.id.sixth_week_sat)
    private val sixth_week_seventh_day: FrameLayout = view.findViewById(R.id.sixth_week_sun)

    private val sixth_week_first_day_date: TextView = view.findViewById(R.id.sixth_week_mon_date)
    private val sixth_week_second_day_date: TextView = view.findViewById(R.id.sixth_week_tue_date)
    private val sixth_week_third_day_date: TextView = view.findViewById(R.id.sixth_week_wed_date)
    private val sixth_week_fourth_day_date: TextView = view.findViewById(R.id.sixth_week_thu_date)
    private val sixth_week_fifth_day_date: TextView = view.findViewById(R.id.sixth_week_fri_date)
    private val sixth_week_sixth_day_date: TextView = view.findViewById(R.id.sixth_week_sat_date)
    private val sixth_week_seventh_day_date: TextView = view.findViewById(R.id.sixth_week_sun_date)

    private val selectedDayDrawable = view.context.getDrawable(R.drawable.day_selector)

    fun bind(month: Month, firstDayOfWeek: Int) {

        val daysInWeek = getMonthMarkupByFirstDayOfWeekUseCase.invoke(view.context, firstDayOfWeek)

        with(month) {
            monthTitle.text = monthNumber.getMonthName(context = view.context, year = month.year)

            if (weeks.isEmpty())
                return
            weeks[0].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { first_week_first_day.setEmotion(it, monthNumber, year, first_week_first_day_date) }
            weeks[0].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { first_week_second_day.setEmotion(it, monthNumber, year, first_week_second_day_date) }
            weeks[0].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { first_week_third_day.setEmotion(it, monthNumber, year, first_week_third_day_date) }
            weeks[0].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { first_week_fourth_day.setEmotion(it, monthNumber, year, first_week_fourth_day_date) }
            weeks[0].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { first_week_fifth_day.setEmotion(it, monthNumber, year, first_week_fifth_day_date) }
            weeks[0].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { first_week_sixth_day.setEmotion(it, monthNumber, year, first_week_sixth_day_date) }
            weeks[0].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { first_week_seventh_day.setEmotion(it, monthNumber, year, first_week_seventh_day_date) }

            if (weeks.size < 2) {
                second_week_layout.visibility = View.GONE
                third_week_layout.visibility = View.GONE
                fourth_week_layout.visibility = View.GONE
                fifth_week_layout.visibility = View.GONE
                sixth_week_layout.visibility = View.GONE
                return
            } else {
                second_week_layout.visibility = View.VISIBLE
            }

            weeks[1].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { second_week_first_day.setEmotion(it, monthNumber, year, second_week_first_day_date) }
            weeks[1].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { second_week_second_day.setEmotion(it, monthNumber, year, second_week_second_day_date) }
            weeks[1].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { second_week_third_day.setEmotion(it, monthNumber, year, second_week_third_day_date) }
            weeks[1].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { second_week_fourth_day.setEmotion(it, monthNumber, year, second_week_fourth_day_date) }
            weeks[1].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { second_week_fifth_day.setEmotion(it, monthNumber, year, second_week_fifth_day_date) }
            weeks[1].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { second_week_sixth_day.setEmotion(it, monthNumber, year, second_week_sixth_day_date) }
            weeks[1].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { second_week_seventh_day.setEmotion(it, monthNumber, year, second_week_seventh_day_date) }

            if (weeks.size < 3) {
                third_week_layout.visibility = View.GONE
                fourth_week_layout.visibility = View.GONE
                fifth_week_layout.visibility = View.GONE
                sixth_week_layout.visibility = View.GONE
                return
            } else {
                third_week_layout.visibility = View.VISIBLE
            }
            weeks[2].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { third_week_first_day.setEmotion(it, monthNumber, year, third_week_first_day_date) }
            weeks[2].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { third_week_second_day.setEmotion(it, monthNumber, year, third_week_second_day_date) }
            weeks[2].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { third_week_third_day.setEmotion(it, monthNumber, year, third_week_third_day_date) }
            weeks[2].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { third_week_fourth_day.setEmotion(it, monthNumber, year, third_week_fourth_day_date) }
            weeks[2].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { third_week_fifth_day.setEmotion(it, monthNumber, year, third_week_fifth_day_date) }
            weeks[2].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { third_week_sixth_day.setEmotion(it, monthNumber, year, third_week_sixth_day_date) }
            weeks[2].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { third_week_seventh_day.setEmotion(it, monthNumber, year, third_week_seventh_day_date) }

            if (weeks.size < 4) {
                fourth_week_layout.visibility = View.GONE
                fifth_week_layout.visibility = View.GONE
                sixth_week_layout.visibility = View.GONE
                return
            } else {
                fourth_week_layout.visibility = View.VISIBLE
            }

            weeks[3].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { fourth_week_first_day.setEmotion(it, monthNumber, year, fourth_week_first_day_date) }
            weeks[3].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { fourth_week_second_day.setEmotion(it, monthNumber, year, fourth_week_second_day_date) }
            weeks[3].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { fourth_week_third_day.setEmotion(it, monthNumber, year, fourth_week_third_day_date) }
            weeks[3].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { fourth_week_fourth_day.setEmotion(it, monthNumber, year, fourth_week_fourth_day_date) }
            weeks[3].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { fourth_week_fifth_day.setEmotion(it, monthNumber, year, fourth_week_fifth_day_date) }
            weeks[3].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { fourth_week_sixth_day.setEmotion(it, monthNumber, year, fourth_week_sixth_day_date) }
            weeks[3].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { fourth_week_seventh_day.setEmotion(it, monthNumber, year, fourth_week_seventh_day_date) }

            if (weeks.size < 5) {
                fifth_week_layout.visibility = View.GONE
                sixth_week_layout.visibility = View.GONE
                return
            } else {
                fifth_week_layout.visibility = View.VISIBLE
            }
            weeks[4].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { fifth_week_first_day.setEmotion(it, monthNumber, year, fifth_week_first_day_date) }
            weeks[4].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { fifth_week_second_day.setEmotion(it, monthNumber, year, fifth_week_second_day_date) }
            weeks[4].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { fifth_week_third_day.setEmotion(it, monthNumber, year, fifth_week_third_day_date) }
            weeks[4].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { fifth_week_fourth_day.setEmotion(it, monthNumber, year, fifth_week_fourth_day_date) }
            weeks[4].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { fifth_week_fifth_day.setEmotion(it, monthNumber, year, fifth_week_fifth_day_date) }
            weeks[4].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { fifth_week_sixth_day.setEmotion(it, monthNumber, year, fifth_week_sixth_day_date) }
            weeks[4].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { fifth_week_seventh_day.setEmotion(it, monthNumber, year, fifth_week_seventh_day_date) }

            if (weeks.size < 6) {
                sixth_week_layout.visibility = View.GONE
                return
            } else {
                sixth_week_layout.visibility = View.VISIBLE
            }
            weeks[5].days[daysInWeek.first].getEmotionViewFromDay(view.context)?.let { sixth_week_first_day.setEmotion(it, monthNumber, year, sixth_week_first_day_date) }
            weeks[5].days[daysInWeek.second].getEmotionViewFromDay(view.context)?.let { sixth_week_second_day.setEmotion(it, monthNumber, year, sixth_week_second_day_date) }
            weeks[5].days[daysInWeek.third].getEmotionViewFromDay(view.context)?.let { sixth_week_third_day.setEmotion(it, monthNumber, year, sixth_week_third_day_date) }
            weeks[5].days[daysInWeek.fourth].getEmotionViewFromDay(view.context)?.let { sixth_week_fourth_day.setEmotion(it, monthNumber, year, sixth_week_fourth_day_date) }
            weeks[5].days[daysInWeek.fifth].getEmotionViewFromDay(view.context)?.let { sixth_week_fifth_day.setEmotion(it, monthNumber, year, sixth_week_fifth_day_date) }
            weeks[5].days[daysInWeek.sixth].getEmotionViewFromDay(view.context)?.let { sixth_week_sixth_day.setEmotion(it, monthNumber, year, sixth_week_sixth_day_date) }
            weeks[5].days[daysInWeek.seventh].getEmotionViewFromDay(view.context)?.let { sixth_week_seventh_day.setEmotion(it, monthNumber, year, sixth_week_seventh_day_date) }
        }
    }

    private fun FrameLayout.setEmotion(dayContainer: DayContainer?, month: Int, year: Int, dateText: TextView) {
        if (dayContainer != null) {
            removeAllViews()
            addView(dayContainer.view)
            val day = dayContainer.dayNumber

            if (dayContainer.isSelected) {
                this.background = selectedDayDrawable
                dateText.visibility = View.INVISIBLE
            } else {
                this.background = null
                dateText.text = day.toString()
                dateText.visibility = View.VISIBLE
            }
            setOnClickListener {
                onDayClickedAction.invoke(month, day, year, dayContainer.emotion)
            }
        } else {
            dateText.visibility = View.INVISIBLE
        }
    }

}
