package gamefield.yourdays.data.repository

import gamefield.yourdays.data.dao.MonthDao
import gamefield.yourdays.data.entity.FirstDayOfWeek
import gamefield.yourdays.data.entity.Month

class EmotionsRepositoryImpl(
    private val monthDao: MonthDao
): EmotionsRepository {

    override suspend fun getMonths(): List<Month> = monthDao.getAll()

    override suspend fun updateMonth(month: Month) {
        monthDao.updateMonth(month = month)
    }

    override suspend fun addMonth(month: Month) {
        monthDao.insertMonth(month = month)
    }

    override suspend fun addCalendarFirstDayOfWeek(firstDayOfWeek: Int) {
        monthDao.insertCalendarFirstDayOfWeek(FirstDayOfWeek(firstDayOfWeek = firstDayOfWeek))
    }

    override suspend fun getCalendarFirstDayOfWeek(): FirstDayOfWeek = monthDao.getCalendarFirstDayOfWeek()

}
