package gamefield.yourdays.domain.di

import gamefield.yourdays.domain.analytics.LogEventUseCase
import gamefield.yourdays.domain.usecase.io.AddDayUseCase
import gamefield.yourdays.domain.usecase.io.GetAllMonthsListUseCase
import gamefield.yourdays.domain.usecase.io.GetCalendarFirstDayOfWeekUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetCurrentEmotionFromMonthListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetDateStrFromDateUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetMonthsInMonthsListUseCase
import gamefield.yourdays.domain.usecase.period_logic.GetYearsInMonthsListUseCase
import gamefield.yourdays.domain.usecase.time_logic.FillDaysBeforeNowUseCase
import gamefield.yourdays.domain.usecase.time_logic.FillNewMonthUseCase
import gamefield.yourdays.domain.usecase.time_logic.IsNeedToAddDaysInMonthUseCase
import gamefield.yourdays.domain.usecase.ui.SaveAndGetUriUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::LogEventUseCase)
    singleOf(::AddDayUseCase)
    singleOf(::FillNewMonthUseCase)
    singleOf(::GetCalendarFirstDayOfWeekUseCase)
    singleOf(::FillDaysBeforeNowUseCase)
    singleOf(::IsNeedToAddDaysInMonthUseCase)
    singleOf(::GetAllMonthsListUseCase)
    singleOf(::GetCurrentEmotionFromMonthListUseCase)
    singleOf(::GetDateStrFromDateUseCase)
    singleOf(::GetMonthsInMonthsListUseCase)
    singleOf(::SaveAndGetUriUseCase)
    singleOf(::GetYearsInMonthsListUseCase)
}
