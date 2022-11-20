package gamefield.yourdays.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gamefield.yourdays.R
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month

class MonthAdapter(
    private val onDayClickedAction: (moth: Int, day: Int, emotion: Emotion) -> Unit
): RecyclerView.Adapter<MonthViewHolder>() {

    val months = ArrayList<Month>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {

        return MonthViewHolder(
            view = LayoutInflater.from(parent.context).inflate(R.layout.card_period_month, parent, false),
            onDayClickedAction = onDayClickedAction
        )
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {

        holder.bind(month = months[position])
    }

    override fun getItemCount(): Int = months.size

}
