package gamefield.yourdays.data.di

import gamefield.yourdays.data.AppDatabase
import gamefield.yourdays.data.repository.EmotionsRepository
import gamefield.yourdays.data.repository.EmotionsRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AppDatabase> { AppDatabase.getInstance(context = get()) }
    single<EmotionsRepository> { EmotionsRepositoryImpl(get<AppDatabase>().monthDao()) }
}
