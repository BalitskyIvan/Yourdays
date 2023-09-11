package gamefield.yourdays.presentation

import gamefield.yourdays.presentation.screen.main_screen.view_model.DateToExportData

interface Navigation {
    fun goToExportToInstagramScreen(dateToExportData: DateToExportData)
    fun goBackFromOnboarding()
    fun goBack()
}
