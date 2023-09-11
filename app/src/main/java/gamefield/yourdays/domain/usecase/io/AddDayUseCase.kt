package gamefield.yourdays.domain.usecase.io

import android.content.Context
import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.Repository
import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month

class AddDayUseCase(
    context: Context
) {

    private val repository = Repository.getInstance(
        AppDatabase.getInstance(context = context).monthDao()
    )

    operator fun invoke(month: Month, dayNumber: Int, emotion: Emotion) {
        month
            .weeks
            .forEach { week ->
                week
                    .days
                    .forEach { day ->
                        if (day.dayInMonth == dayNumber && day.emotion != null) {
                            day.emotion = emotion
                        }
                    }
            }
        repository.updateMonth(month)
    }

}
