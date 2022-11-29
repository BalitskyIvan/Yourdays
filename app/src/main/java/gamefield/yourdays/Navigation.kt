package gamefield.yourdays

import gamefield.yourdays.utils.main_screen.DateToExportData

interface Navigation {
    fun goToExportToInstagramScreen(dateToExportData: DateToExportData)
    fun goBack()
}