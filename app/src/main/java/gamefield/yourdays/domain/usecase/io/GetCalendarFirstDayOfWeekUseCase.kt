package gamefield.yourdays.domain.usecase.io

import android.icu.util.Calendar
import gamefield.yourdays.data.repository.EmotionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCalendarFirstDayOfWeekUseCase(
    private val emotionsRepository: EmotionsRepository
) {

    suspend operator fun invoke(): Int {
        return withContext(Dispatchers.IO) {
            val firstDayOfWeekInDatabase = emotionsRepository.getCalendarFirstDayOfWeek()

             if (firstDayOfWeekInDatabase == null) {
                var createdFirstDayOfWeek = Calendar.getInstance().firstDayOfWeek
                if (createdFirstDayOfWeek != 1 && createdFirstDayOfWeek != 2)
                    createdFirstDayOfWeek = 1
                emotionsRepository.addCalendarFirstDayOfWeek(firstDayOfWeek = createdFirstDayOfWeek)
                createdFirstDayOfWeek
            } else {
                firstDayOfWeekInDatabase.firstDayOfWeek
            }
        }
    }

}
