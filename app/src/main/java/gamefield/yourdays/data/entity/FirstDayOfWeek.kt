package gamefield.yourdays.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "firstDayOfMonth")
data class FirstDayOfWeek(
    @PrimaryKey
    val firstDayOfWeek: Int
)
