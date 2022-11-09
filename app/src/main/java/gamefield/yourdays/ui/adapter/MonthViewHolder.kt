package gamefield.yourdays.ui.adapter

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gamefield.yourdays.R
import gamefield.yourdays.data.entity.Day
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.ui.customviews.emotions.EmotionView
import gamefield.yourdays.ui.customviews.emotions.MinusEmotionView
import gamefield.yourdays.ui.customviews.emotions.PlusEmotionView
import gamefield.yourdays.ui.customviews.emotions.ZeroEmotionView

class MonthViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    private val monthTitle: TextView = view.findViewById(R.id.card_period_month_name)

    //FIRST WEEK
    private val first_week_first_day: FrameLayout = view.findViewById(R.id.first_week_mon)
    private val first_week_second_day: FrameLayout = view.findViewById(R.id.first_week_tue)
    private val first_week_third_day: FrameLayout = view.findViewById(R.id.first_week_wed)
    private val first_week_fourth_day: FrameLayout = view.findViewById(R.id.first_week_thu)
    private val first_week_fifth_day: FrameLayout = view.findViewById(R.id.first_week_fri)
    private val first_week_sixth_day: FrameLayout = view.findViewById(R.id.first_week_sat)
    private val first_week_seventh_day: FrameLayout = view.findViewById(R.id.first_week_sun)


    //SECOND WEEK
    private val second_week_first_day: FrameLayout = view.findViewById(R.id.second_week_mon)
    private val second_week_second_day: FrameLayout = view.findViewById(R.id.second_week_tue)
    private val second_week_third_day: FrameLayout = view.findViewById(R.id.second_week_wed)
    private val second_week_fourth_day: FrameLayout = view.findViewById(R.id.second_week_thu)
    private val second_week_fifth_day: FrameLayout = view.findViewById(R.id.second_week_fri)
    private val second_week_sixth_day: FrameLayout = view.findViewById(R.id.second_week_sat)
    private val second_week_seventh_day: FrameLayout = view.findViewById(R.id.second_week_sun)

    //THIRD WEEK
    private val third_week_first_day: FrameLayout = view.findViewById(R.id.third_week_mon)
    private val third_week_second_day: FrameLayout = view.findViewById(R.id.third_week_tue)
    private val third_week_third_day: FrameLayout = view.findViewById(R.id.third_week_wed)
    private val third_week_fourth_day: FrameLayout = view.findViewById(R.id.third_week_thu)
    private val third_week_fifth_day: FrameLayout = view.findViewById(R.id.third_week_fri)
    private val third_week_sixth_day: FrameLayout = view.findViewById(R.id.third_week_sat)
    private val third_week_seventh_day: FrameLayout = view.findViewById(R.id.third_week_sun)

    //FOURTH WEEK
    private val fourth_week_first_day: FrameLayout = view.findViewById(R.id.fourth_week_mon)
    private val fourth_week_second_day: FrameLayout = view.findViewById(R.id.fourth_week_tue)
    private val fourth_week_third_day: FrameLayout = view.findViewById(R.id.fourth_week_wed)
    private val fourth_week_fourth_day: FrameLayout = view.findViewById(R.id.fourth_week_thu)
    private val fourth_week_fifth_day: FrameLayout = view.findViewById(R.id.fourth_week_fri)
    private val fourth_week_sixth_day: FrameLayout = view.findViewById(R.id.fourth_week_sat)
    private val fourth_week_seventh_day: FrameLayout = view.findViewById(R.id.fourth_week_sun)

    //FIFTH WEEK
    private val fifth_week_first_day: FrameLayout = view.findViewById(R.id.fifth_week_mon)
    private val fifth_week_second_day: FrameLayout = view.findViewById(R.id.fifth_week_tue)
    private val fifth_week_third_day: FrameLayout = view.findViewById(R.id.fifth_week_wed)
    private val fifth_week_fourth_day: FrameLayout = view.findViewById(R.id.fifth_week_thu)
    private val fifth_week_fifth_day: FrameLayout = view.findViewById(R.id.fifth_week_fri)
    private val fifth_week_sixth_day: FrameLayout = view.findViewById(R.id.fifth_week_sat)
    private val fifth_week_seventh_day: FrameLayout = view.findViewById(R.id.fifth_week_sun)

    fun bind(month: Month) {
        with(month) {
            setMonthName(monthNumber)

            weeks[0].days[0].getEmotionViewFromDay(view.context)?.let { first_week_first_day.addView(it) }
            weeks[0].days[1].getEmotionViewFromDay(view.context)?.let { first_week_second_day.addView(it) }
            weeks[0].days[2].getEmotionViewFromDay(view.context)?.let { first_week_third_day.addView(it) }
            weeks[0].days[3].getEmotionViewFromDay(view.context)?.let { first_week_fourth_day.addView(it) }
            weeks[0].days[4].getEmotionViewFromDay(view.context)?.let { first_week_fifth_day.addView(it) }
            weeks[0].days[5].getEmotionViewFromDay(view.context)?.let { first_week_sixth_day.addView(it) }
            weeks[0].days[6].getEmotionViewFromDay(view.context)?.let { first_week_seventh_day.addView(it) }

            weeks[1].days[0].getEmotionViewFromDay(view.context)?.let { second_week_first_day.addView(it) }
            weeks[1].days[1].getEmotionViewFromDay(view.context)?.let { second_week_second_day.addView(it) }
            weeks[1].days[2].getEmotionViewFromDay(view.context)?.let { second_week_third_day.addView(it) }
            weeks[1].days[3].getEmotionViewFromDay(view.context)?.let { second_week_fourth_day.addView(it) }
            weeks[1].days[4].getEmotionViewFromDay(view.context)?.let { second_week_fifth_day.addView(it) }
            weeks[1].days[5].getEmotionViewFromDay(view.context)?.let { second_week_sixth_day.addView(it) }
            weeks[1].days[6].getEmotionViewFromDay(view.context)?.let { second_week_seventh_day.addView(it) }

            weeks[2].days[0].getEmotionViewFromDay(view.context)?.let { third_week_first_day.addView(it) }
            weeks[2].days[1].getEmotionViewFromDay(view.context)?.let { third_week_second_day.addView(it) }
            weeks[2].days[2].getEmotionViewFromDay(view.context)?.let { third_week_third_day.addView(it) }
            weeks[2].days[3].getEmotionViewFromDay(view.context)?.let { third_week_fourth_day.addView(it) }
            weeks[2].days[4].getEmotionViewFromDay(view.context)?.let { third_week_fifth_day.addView(it) }
            weeks[2].days[5].getEmotionViewFromDay(view.context)?.let { third_week_sixth_day.addView(it) }
            weeks[2].days[6].getEmotionViewFromDay(view.context)?.let { third_week_seventh_day.addView(it) }

            weeks[3].days[0].getEmotionViewFromDay(view.context)?.let { fourth_week_first_day.addView(it) }
            weeks[3].days[1].getEmotionViewFromDay(view.context)?.let { fourth_week_second_day.addView(it) }
            weeks[3].days[2].getEmotionViewFromDay(view.context)?.let { fourth_week_third_day.addView(it) }
            weeks[3].days[3].getEmotionViewFromDay(view.context)?.let { fourth_week_fourth_day.addView(it) }
            weeks[3].days[4].getEmotionViewFromDay(view.context)?.let { fourth_week_fifth_day.addView(it) }
            weeks[3].days[5].getEmotionViewFromDay(view.context)?.let { fourth_week_sixth_day.addView(it) }
            weeks[3].days[6].getEmotionViewFromDay(view.context)?.let { fourth_week_seventh_day.addView(it)}

            weeks[4].days[0].getEmotionViewFromDay(view.context)?.let { fifth_week_first_day.addView(it) }
            weeks[4].days[1].getEmotionViewFromDay(view.context)?.let { fifth_week_second_day.addView(it) }
            weeks[4].days[2].getEmotionViewFromDay(view.context)?.let { fifth_week_third_day.addView(it) }
            weeks[4].days[3].getEmotionViewFromDay(view.context)?.let { fifth_week_fourth_day.addView(it) }
            weeks[4].days[4].getEmotionViewFromDay(view.context)?.let { fifth_week_fifth_day.addView(it) }
            weeks[4].days[5].getEmotionViewFromDay(view.context)?.let { fifth_week_sixth_day.addView(it) }
            weeks[4].days[6].getEmotionViewFromDay(view.context)?.let { fifth_week_seventh_day.addView(it) }
        }
    }

    private fun Day.getEmotionViewFromDay(context: Context): EmotionView? = when(emotion?.type) {
        EmotionType.ZERO -> ZeroEmotionView(context = context).apply { parseEmotionInEmotionView(emotion) }
        EmotionType.PLUS -> PlusEmotionView(context = context).apply { parseEmotionInEmotionView(emotion) }
        EmotionType.MINUS -> MinusEmotionView(context = context).apply { parseEmotionInEmotionView(emotion) }
        else -> null
    }

    private fun EmotionView.parseEmotionInEmotionView(emotion: Emotion) {
        anxiety = emotion.anxiety
        joy = emotion.joy
        sadness = emotion.sadness
        calmness = emotion.calmness
    }

    private fun setMonthName(monthNumber: Int) {
        monthTitle.text = when(monthNumber) {
            1 -> view.context.getString(R.string.january)
            2 -> view.context.getString(R.string.february)
            3 -> view.context.getString(R.string.march)
            4 -> view.context.getString(R.string.april)
            5 -> view.context.getString(R.string.may)
            6 -> view.context.getString(R.string.june)
            7 -> view.context.getString(R.string.july)
            8 -> view.context.getString(R.string.august)
            9 -> view.context.getString(R.string.september)
            10 -> view.context.getString(R.string.october)
            11 -> view.context.getString(R.string.november)
            12 -> view.context.getString(R.string.december)
            else -> ""
        }
    }

}
