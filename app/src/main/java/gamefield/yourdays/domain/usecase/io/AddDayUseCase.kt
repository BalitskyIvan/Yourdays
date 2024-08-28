package gamefield.yourdays.domain.usecase.io

import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.data.repository.EmotionsRepository

class AddDayUseCase(
    private val emotionsRepository: EmotionsRepository
) {

    suspend operator fun invoke(month: Month, dayNumber: Int, emotion: Emotion) {
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
        emotionsRepository.updateMonth(month)
    }

}
