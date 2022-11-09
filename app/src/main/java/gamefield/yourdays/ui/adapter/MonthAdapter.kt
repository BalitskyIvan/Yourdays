package gamefield.yourdays.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gamefield.yourdays.R
import gamefield.yourdays.data.entity.Month

class MonthAdapter(): RecyclerView.Adapter<MonthViewHolder>() {

    val months = ArrayList<Month>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {

        return MonthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_period_month, parent, false))
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {

        holder.bind(month = months[position])
    }

    override fun getItemCount(): Int = months.size

}
