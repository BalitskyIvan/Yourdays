package gamefield.yourdays.di

import android.content.Context
import android.content.res.Resources
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.dsl.module
import java.util.Calendar

val appModule = module {
    single<Calendar> { Calendar.getInstance() }
    single<Resources> { get<Context>().resources }
    single<FirebaseAnalytics> { FirebaseAnalytics.getInstance(get()) }
}
