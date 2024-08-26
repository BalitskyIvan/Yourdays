package gamefield.yourdays.domain.usecase.period_logic

import android.content.res.Resources
import gamefield.yourdays.extensions.getMonthName
import gamefield.yourdays.presentation.screen.export_screen.view_model.PickedDateData

class GetDateStrFromDateUseCase(private val resources: Resources) {

    operator fun invoke(dateData: PickedDateData): String {
        val month = dateData.month.getMonthName(
            isUppercase = true,
            isNeedToAddYear = false,
            resources = resources
        )
        return "$month ${dateData.day}"
    }

}
