package gamefield.yourdays.data.repository

import gamefield.yourdays.data.entity.FirstDayOfWeek
import gamefield.yourdays.data.entity.Month

interface EmotionsRepository {
    suspend fun getMonths(): List<Month>

    suspend fun updateMonth(month: Month)

    suspend fun addMonth(month: Month)

    suspend fun addCalendarFirstDayOfWeek(firstDayOfWeek: Int)

    suspend fun getCalendarFirstDayOfWeek(): FirstDayOfWeek
}
