package gamefield.yourdays.domain.usecase.io

import android.icu.util.Calendar
import gamefield.yourdays.data.repository.EmotionsRepository

class GetCalendarFirstDayOfWeekUseCase(
    private val emotionsRepository: EmotionsRepository
) {

    operator fun invoke(): Int {
        val firstDayOfWeekInDatabase = emotionsRepository.getCalendarFirstDayOfWeek()

        return if (firstDayOfWeekInDatabase == null) {
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
