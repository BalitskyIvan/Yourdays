package gamefield.yourdays

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import gamefield.yourdays.ui.fragments.onboarding.OnboardingFragment
import gamefield.yourdays.ui.fragments.screens.ExportToInstagramScreenFragment
import gamefield.yourdays.ui.fragments.screens.MainScreenFragment
import gamefield.yourdays.utils.analytics.AnalyticsEvent
import gamefield.yourdays.utils.analytics.AnalyticsTracks
import gamefield.yourdays.utils.analytics.LogEventUseCase
import gamefield.yourdays.utils.analytics.main_screen.AppClosedEvent
import gamefield.yourdays.utils.analytics.main_screen.AppOpenedEvent
import gamefield.yourdays.utils.main_screen.DateToExportData
import java.util.UUID

class MainActivity : AppCompatActivity(), Navigation, AnalyticsTracks {

    private val mainScreenFragment = MainScreenFragment.newInstance()
    private val onboardingScreenFragment = OnboardingFragment.newInstance()
    private lateinit var logEventUseCase: LogEventUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isNeedToShowOnboarding =
            getPreferences(MODE_PRIVATE).getBoolean(NEED_TO_SHOW_ONBOARDING_KEY, true)

        initAnalytics()
        logEventUseCase = LogEventUseCase(analytics = Firebase.analytics)

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

    override fun onStart() {
        super.onStart()
        logEventUseCase.invoke(AppOpenedEvent())
    }

    override fun onStop() {
        super.onStop()
        logEventUseCase.invoke(AppClosedEvent())
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

    override fun logEvent(event: AnalyticsEvent) {
        logEventUseCase.invoke(event)
    }

    override fun goBack() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, mainScreenFragment)
            .commitNow()
    }

    private fun initAnalytics() {
        getPreferences(MODE_PRIVATE).getString(ANALYTICS_USER_ID_KEY, null).apply {
            if (this == null) {
                val userId = UUID.randomUUID().toString()
                Firebase.analytics.setUserId(userId)
                getPreferences(MODE_PRIVATE)
                    .edit()
                    .putString(ANALYTICS_USER_ID_KEY, userId)
                    .apply()
                goBack()
            } else {
                Firebase.analytics.setUserId(this)
            }
        }
    }

    private companion object {
        const val NEED_TO_SHOW_ONBOARDING_KEY = "ONBOARDING_KEY"
        const val ANALYTICS_USER_ID_KEY = "ANALYTICS_USER_ID_KEY"
    }
}
