package gamefield.yourdays.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import gamefield.yourdays.data.Converters
import java.util.UUID

@Entity(tableName = "months")
data class Month(
    @PrimaryKey
    val id: UUID,
    val monthNumber: Int,
    val year: Int,

    @TypeConverters(Converters::class)
    var weeks: List<Week>
)
