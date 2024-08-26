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
import org.koin.dsl.module

val useCaseModule = module {
    single<LogEventUseCase> { LogEventUseCase(analytics = get()) }
    single<AddDayUseCase> { AddDayUseCase(emotionsRepository = get()) }
    single<FillNewMonthUseCase> { FillNewMonthUseCase(calendar = get(), emotionsRepository = get()) }
    single<GetCalendarFirstDayOfWeekUseCase> { GetCalendarFirstDayOfWeekUseCase(emotionsRepository = get()) }
    single<FillDaysBeforeNowUseCase> { FillDaysBeforeNowUseCase(emotionsRepository = get()) }
    single<IsNeedToAddDaysInMonthUseCase> { IsNeedToAddDaysInMonthUseCase(calendar = get()) }
    single<GetAllMonthsListUseCase> {
        GetAllMonthsListUseCase(
            calendar = get(),
            emotionsRepository = get(),
            fillNewMonthUseCase = get(),
            getCalendarFirstDayOfWeekUseCase = get(),
            isNeedToAddDaysInMonthUseCase = get(),
            fillDaysBeforeNowUseCase = get()
        )
    }
    single<GetCurrentEmotionFromMonthListUseCase> { GetCurrentEmotionFromMonthListUseCase() }
    single<GetDateStrFromDateUseCase> { GetDateStrFromDateUseCase(resources = get()) }
    single<GetMonthsInMonthsListUseCase> { GetMonthsInMonthsListUseCase(resources = get()) }
    single<GetYearsInMonthsListUseCase> { GetYearsInMonthsListUseCase(calendar = get()) }
}
