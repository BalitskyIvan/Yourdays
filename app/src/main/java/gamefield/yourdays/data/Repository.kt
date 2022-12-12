package gamefield.yourdays.data

import android.icu.util.Calendar
import gamefield.yourdays.data.dao.MonthDao
import gamefield.yourdays.data.entity.FirstDayOfWeek
import gamefield.yourdays.data.entity.Month
import kotlinx.coroutines.flow.Flow

class Repository(
    private val monthDao: MonthDao
) {

    fun getMonths(): Flow<List<Month>> = monthDao.getAll()

    fun updateMonth(month: Month) {
        monthDao.updateMonth(month = month)
    }

    fun addMonth(month: Month) {
        monthDao.insertMonth(month = month)
    }

    fun addCalendarFirstDayOfWeek(firstDayOfWeek: Int) {
       monthDao.insertCalendarFirstDayOfWeek(FirstDayOfWeek(firstDayOfWeek = firstDayOfWeek))
    }

    fun getCalendarFirstDayOfWeek(): FirstDayOfWeek = monthDao.getCalendarFirstDayOfWeek()

    fun clear() {
        monthDao.clearDatabase()
    }

    companion object {

        @Volatile
        private var instance: Repository? = null

        fun getInstance(monthDao: MonthDao) =
            instance ?: synchronized(this) {
                instance ?: Repository(monthDao).also { instance = it }
            }
    }
}
