package gamefield.yourdays.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gamefield.yourdays.R
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import java.util.Calendar

class MonthAdapter(
    private val onDayClickedAction: (moth: Int, day: Int, year: Int, emotion: Emotion) -> Unit
): RecyclerView.Adapter<MonthViewHolder>() {

    val months = ArrayList<Month>()

    var firstDayOfWeek = Calendar.getInstance().firstDayOfWeek

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {

        return MonthViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.card_period_month, parent, false),
            onDayClickedAction = onDayClickedAction
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {

        holder.bind(month = months[position], firstDayOfWeek)
    }

    override fun getItemCount(): Int = months.size

}
