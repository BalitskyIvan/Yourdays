package gamefield.yourdays

import android.app.Application
import gamefield.yourdays.data.di.repositoryModule
import gamefield.yourdays.di.appModule
import gamefield.yourdays.domain.di.useCaseModule
import gamefield.yourdays.presentation.di.fragmentModule
import gamefield.yourdays.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule, repositoryModule, useCaseModule, viewModelModule, fragmentModule)
            fragmentFactory()
        }
    }
}
