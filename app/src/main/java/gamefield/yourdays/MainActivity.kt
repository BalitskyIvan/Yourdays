package gamefield.yourdays

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gamefield.yourdays.ui.fragments.onboarding.OnboardingFragment
import gamefield.yourdays.ui.fragments.screens.ExportToInstagramScreenFragment
import gamefield.yourdays.ui.fragments.screens.MainScreenFragment
import gamefield.yourdays.utils.main_screen.DateToExportData

class MainActivity : AppCompatActivity(), Navigation {

    private val mainScreenFragment = MainScreenFragment.newInstance()
    private val onboardingScreenFragment = OnboardingFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isNeedToShowOnboarding =
            getPreferences(MODE_PRIVATE).getBoolean(NEED_TO_SHOW_ONBOARDING_KEY, true)


        setContentView(R.layout.activity_main)

        if (isNeedToShowOnboarding) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_screen_container, onboardingScreenFragment)
                .commitNow()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_screen_container, mainScreenFragment)
                .commitNow()
        }
    }

    override fun goToExportToInstagramScreen(dateToExportData: DateToExportData) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.main_screen_container, ExportToInstagramScreenFragment.newInstance(
                    day = dateToExportData.day,
                    month = dateToExportData.month,
                    year = dateToExportData.year,
                    isExportDay = dateToExportData.isExportDay
                )
            )
            .commitNow()
    }

    override fun goBackFromOnboarding() {
        getPreferences(MODE_PRIVATE)
            .edit()
            .putBoolean(NEED_TO_SHOW_ONBOARDING_KEY, false)
            .apply()
        goBack()
    }

    override fun goBack() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, mainScreenFragment)
            .commitNow()
    }

    private companion object {
        const val NEED_TO_SHOW_ONBOARDING_KEY = "ONBOARDING_KEY"
    }
}
