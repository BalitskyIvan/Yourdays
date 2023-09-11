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
    fun getAll(): Flow<List<Month>>

    @Update
    fun updateMonth(month: Month)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMonth(month: Month)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCalendarFirstDayOfWeek(firstDay: FirstDayOfWeek)

    @Query("SELECT * FROM firstDayOfMonth")
    fun getCalendarFirstDayOfWeek(): FirstDayOfWeek

    @Query("DELETE FROM months")
    fun clearDatabase()

}
