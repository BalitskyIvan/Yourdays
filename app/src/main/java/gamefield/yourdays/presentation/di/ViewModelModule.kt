package gamefield.yourdays.presentation.di

import gamefield.yourdays.presentation.screen.export_screen.view_model.ExportToInstagramViewModel
import gamefield.yourdays.presentation.screen.main_screen.view_model.MainScreenEmotionViewModel
import gamefield.yourdays.presentation.screen.main_screen.view_model.MainScreenViewModel
import gamefield.yourdays.presentation.screen.onboarding.view_model.EmptyEmotionViewViewModel
import gamefield.yourdays.presentation.screen.onboarding.view_model.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::ExportToInstagramViewModel)
    viewModelOf(::MainScreenEmotionViewModel)
    viewModelOf(::EmptyEmotionViewViewModel)
    viewModelOf(::OnboardingViewModel)
}
