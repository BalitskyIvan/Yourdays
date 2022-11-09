package gamefield.yourdays.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("DELETE FROM months")
    fun clearDatabase()

}