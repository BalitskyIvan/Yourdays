package gamefield.yourdays.domain.usecase.io

import android.content.Context
import android.icu.util.Calendar
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository

class GetCalendarFirstDayOfWeekUseCase(context: Context) {

    private val repository =
        Repository.getInstance(AppDatabase.getInstance(context = context).monthDao())

    operator fun invoke(): Int {
        val firstDayOfWeekInDatabase = repository.getCalendarFirstDayOfWeek()

        return if (firstDayOfWeekInDatabase == null) {
            var createdFirstDayOfWeek = Calendar.getInstance().firstDayOfWeek
            if (createdFirstDayOfWeek != 1 && createdFirstDayOfWeek != 2)
                createdFirstDayOfWeek = 1
            repository.addCalendarFirstDayOfWeek(firstDayOfWeek = createdFirstDayOfWeek)
            createdFirstDayOfWeek
        } else {
            firstDayOfWeekInDatabase.firstDayOfWeek
        }
    }

}
