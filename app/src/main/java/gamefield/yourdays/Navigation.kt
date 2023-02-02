package gamefield.yourdays

import gamefield.yourdays.utils.analytics.AnalyticsEvent
import gamefield.yourdays.utils.main_screen.DateToExportData

interface Navigation {
    fun goToExportToInstagramScreen(dateToExportData: DateToExportData)
    fun goBackFromOnboarding()
    fun goBack()
}