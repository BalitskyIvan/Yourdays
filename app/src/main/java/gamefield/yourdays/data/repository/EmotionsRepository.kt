package gamefield.yourdays.data.repository

import gamefield.yourdays.data.entity.FirstDayOfWeek
import gamefield.yourdays.data.entity.Month
import kotlinx.coroutines.flow.Flow

interface EmotionsRepository {
    fun getMonths(): Flow<List<Month>>

    fun updateMonth(month: Month)

    fun addMonth(month: Month)

    fun addCalendarFirstDayOfWeek(firstDayOfWeek: Int)

    fun getCalendarFirstDayOfWeek(): FirstDayOfWeek
}
