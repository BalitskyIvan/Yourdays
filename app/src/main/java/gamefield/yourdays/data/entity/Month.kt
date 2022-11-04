package gamefield.yourdays.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import gamefield.yourdays.data.Converters

@Entity(tableName = "months")
data class Month(
    @PrimaryKey
    val id: Int,
    val monthName: String,

    @TypeConverters(Converters::class)
    val months: List<Week>
)
