package gamefield.yourdays.domain.usecase.period_logic

import android.content.Context
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.presentation.screen.export_screen.view_model.PickedDateData

class GetDateStrFromDateUseCase(private val context: Context) {

    operator fun invoke(dateData: PickedDateData): String {
        val month = dateData.month.getMonthName(
            isUppercase = true,
            isNeedToAddYear = false,
            context = context
        )
        return "$month ${dateData.day}"
    }

}
