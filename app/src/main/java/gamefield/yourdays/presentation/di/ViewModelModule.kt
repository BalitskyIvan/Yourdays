package gamefield.yourdays.presentation.di

import gamefield.yourdays.presentation.screen.export_screen.view_model.ExportToInstagramViewModel
import gamefield.yourdays.presentation.screen.main_screen.view_model.MainScreenEmotionViewModel
import gamefield.yourdays.presentation.screen.main_screen.view_model.MainScreenViewModel
import gamefield.yourdays.presentation.screen.onboarding.view_model.EmptyEmotionViewViewModel
import gamefield.yourdays.presentation.screen.onboarding.view_model.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<MainScreenViewModel> {
        MainScreenViewModel(
            addDayUseCase = get(),
            getAllMonthsListUseCase = get(),
            logEventUseCase = get()
        )
    }
    viewModel<ExportToInstagramViewModel> {
        ExportToInstagramViewModel(
            getMonthsInMonthsListUseCase = get(),
            getDateStrFromDateUseCase = get(),
            getAllMonthsListUseCase = get(),
            getYearsInMonthsListUseCase = get(),
            getCurrentEmotionFromMonthListUseCase = get(),
            logEventUseCase = get()
        )
    }
    viewModel<MainScreenEmotionViewModel> { MainScreenEmotionViewModel() }
    viewModel<EmptyEmotionViewViewModel> { EmptyEmotionViewViewModel() }
    viewModel<OnboardingViewModel> { OnboardingViewModel() }
}
