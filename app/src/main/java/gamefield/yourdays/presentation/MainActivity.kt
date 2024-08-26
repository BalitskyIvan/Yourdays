package gamefield.yourdays.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import gamefield.yourdays.R
import gamefield.yourdays.presentation.screen.onboarding.OnboardingFragment
import gamefield.yourdays.presentation.screen.export_screen.ExportToInstagramScreenFragment
import gamefield.yourdays.presentation.screen.main_screen.MainScreenFragment
import gamefield.yourdays.domain.analytics.AnalyticsEvent
import gamefield.yourdays.domain.analytics.AnalyticsTracks
import gamefield.yourdays.domain.analytics.LogEventUseCase
import gamefield.yourdays.domain.analytics.main_screen.AppClosedEvent
import gamefield.yourdays.domain.analytics.main_screen.AppOpenedEvent
import gamefield.yourdays.presentation.screen.main_screen.view_model.DateToExportData
import org.koin.android.ext.android.inject
import java.util.UUID

class MainActivity : AppCompatActivity(), Navigation, AnalyticsTracks {

    private val mainScreenFragment = MainScreenFragment.newInstance()
    private val onboardingScreenFragment = OnboardingFragment.newInstance()

    private val logEventUseCase: LogEventUseCase by inject()
    private val firebaseAnalytics: FirebaseAnalytics by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isNeedToShowOnboarding =
            getPreferences(MODE_PRIVATE).getBoolean(NEED_TO_SHOW_ONBOARDING_KEY, true)
        initAnalytics(firebaseAnalytics)

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

    private fun initAnalytics(firebaseAnalytics: FirebaseAnalytics) {
        getPreferences(MODE_PRIVATE).getString(ANALYTICS_USER_ID_KEY, null).apply {
            if (this == null) {
                val userId = UUID.randomUUID().toString()
                firebaseAnalytics.setUserId(userId)
                getPreferences(MODE_PRIVATE)
                    .edit()
                    .putString(ANALYTICS_USER_ID_KEY, userId)
                    .apply()
                goBack()
            } else {
               firebaseAnalytics.setUserId(this)
            }
        }
    }

    private companion object {
        const val NEED_TO_SHOW_ONBOARDING_KEY = "ONBOARDING_KEY"
        const val ANALYTICS_USER_ID_KEY = "ANALYTICS_USER_ID_KEY"
    }
}
