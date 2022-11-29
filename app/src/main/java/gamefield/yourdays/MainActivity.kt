package gamefield.yourdays

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gamefield.yourdays.ui.fragments.screens.ExportToInstagramScreenFragment
import gamefield.yourdays.ui.fragments.screens.MainScreenFragment
import gamefield.yourdays.utils.main_screen.DateToExportData
import gamefield.yourdays.utils.main_screen.DaySelectedContainer

class MainActivity : AppCompatActivity(), Navigation {

    private val mainScreenFragment = MainScreenFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, mainScreenFragment)
            .commitNow()
    }

    override fun goToExportToInstagramScreen(dateToExportData: DateToExportData) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, ExportToInstagramScreenFragment.newInstance(
                day = dateToExportData.day,
                month = dateToExportData.month,
                year = dateToExportData.year
            ))
            .commitNow()
    }

    override fun goBack() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, mainScreenFragment)
            .commitNow()
    }
}
