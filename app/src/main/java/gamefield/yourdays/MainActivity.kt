package gamefield.yourdays

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gamefield.yourdays.ui.fragments.screens.ExportToInstagramScreenFragment
import gamefield.yourdays.ui.fragments.screens.MainScreenFragment

class MainActivity : AppCompatActivity(), Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, MainScreenFragment.newInstance())
            .commitNow()
    }

    override fun goToExportToInstagramScreen() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, ExportToInstagramScreenFragment.newInstance())
            .commitNow()
    }

    override fun goBack() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, MainScreenFragment.newInstance())
            .commitNow()
    }
}
