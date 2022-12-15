package gamefield.yourdays.domain.usecase.period_logic

import gamefield.yourdays.data.entity.Emotion
import gamefield.yourdays.data.entity.Month
import gamefield.yourdays.utils.export_screen.PickedDateData

class GetCurrentEmotionFromMonthListUseCase {

    operator fun invoke(monthList: List<Month>, date: PickedDateData): Emotion? {
        monthList.forEach { month ->
            if (date.month == month.monthNumber && date.year == month.year) {
                month.weeks.forEach { week ->
                    week.days.forEach { day ->
                        if (day.dayInMonth == date.day) {
                            return day.emotion
                        }
                    }
                }
            }
        }
        return null
    }

}
