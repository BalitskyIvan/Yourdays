package gamefield.yourdays.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import gamefield.yourdays.data.entity.FirstDayOfWeek
import gamefield.yourdays.data.entity.Month
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthDao {

    @Query("SELECT * FROM months")
    suspend fun getAll(): List<Month>

    @Update
    suspend fun updateMonth(month: Month)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonth(month: Month)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCalendarFirstDayOfWeek(firstDay: FirstDayOfWeek)

    @Query("SELECT * FROM firstDayOfMonth")
    suspend fun getCalendarFirstDayOfWeek(): FirstDayOfWeek

}
