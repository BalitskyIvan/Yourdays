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
import org.koin.androidx.fragment.dsl.fragmentOf
import org.koin.dsl.module

val fragmentModule = module{
    fragmentOf(::DayPickerFragment)
    fragmentOf(::ExportToInstagramScreenFragment)
    fragmentOf(::MonthPickerFragment)
    fragmentOf(::ChangeEmotionFragment)
    fragmentOf(::DayPreviewFragment)
    fragmentOf(::MainScreenEmotionFragment)
    fragmentOf(::MainScreenFragment)
    fragmentOf(::MonthPreviewFragment)
    fragmentOf(::OnboardingFragment)
}
