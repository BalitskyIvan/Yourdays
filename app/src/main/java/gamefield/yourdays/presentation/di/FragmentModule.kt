package gamefield.yourdays.presentation.di

import gamefield.yourdays.presentation.screen.export_screen.DayPickerFragment
import gamefield.yourdays.presentation.screen.export_screen.ExportToInstagramScreenFragment
import gamefield.yourdays.presentation.screen.export_screen.MonthPickerFragment
import gamefield.yourdays.presentation.screen.main_screen.ChangeEmotionFragment
import gamefield.yourdays.presentation.screen.main_screen.DayPreviewFragment
import gamefield.yourdays.presentation.screen.main_screen.MainScreenEmotionFragment
import gamefield.yourdays.presentation.screen.main_screen.MainScreenFragment
import gamefield.yourdays.presentation.screen.main_screen.MonthPreviewFragment
import gamefield.yourdays.presentation.screen.onboarding.OnboardingFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module{
    fragment { DayPickerFragment() }
    fragment { ExportToInstagramScreenFragment() }
    fragment { MonthPickerFragment() }
    fragment { ChangeEmotionFragment() }
    fragment { DayPreviewFragment() }
    fragment { MainScreenEmotionFragment() }
    fragment { MainScreenFragment() }
    fragment { MonthPreviewFragment() }
    fragment { OnboardingFragment() }
}
