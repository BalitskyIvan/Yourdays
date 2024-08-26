package gamefield.yourdays.di

import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.dsl.module
import java.util.Calendar

val appModule = module {
    single<Calendar> { Calendar.getInstance() }
    single<FirebaseAnalytics> { FirebaseAnalytics.getInstance(get()) }
}
