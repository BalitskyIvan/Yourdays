package gamefield.yourdays.data

import gamefield.yourdays.data.dao.MonthDao
import gamefield.yourdays.data.entity.Month
import kotlinx.coroutines.flow.Flow

class Repository(
    private val monthDao: MonthDao
) {

    suspend fun getMonths(): Flow<List<Month>> = monthDao.getAll()

    suspend fun updateMonth(month: Month) {
        monthDao.updateMonth(month = month)
    }

    suspend fun addMonth(month: Month) {
        monthDao.insertMonth(month = month)
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
