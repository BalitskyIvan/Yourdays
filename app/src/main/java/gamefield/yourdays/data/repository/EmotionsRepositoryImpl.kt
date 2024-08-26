package gamefield.yourdays.data.repository

import gamefield.yourdays.data.dao.MonthDao
import gamefield.yourdays.data.entity.FirstDayOfWeek
import gamefield.yourdays.data.entity.Month
import kotlinx.coroutines.flow.Flow

class EmotionsRepositoryImpl(
    private val monthDao: MonthDao
): EmotionsRepository {

    override fun getMonths(): Flow<List<Month>> = monthDao.getAll()

    override fun updateMonth(month: Month) {
        monthDao.updateMonth(month = month)
    }

    override fun addMonth(month: Month) {
        monthDao.insertMonth(month = month)
    }

    override fun addCalendarFirstDayOfWeek(firstDayOfWeek: Int) {
        monthDao.insertCalendarFirstDayOfWeek(FirstDayOfWeek(firstDayOfWeek = firstDayOfWeek))
    }

    override fun getCalendarFirstDayOfWeek(): FirstDayOfWeek = monthDao.getCalendarFirstDayOfWeek()

}
