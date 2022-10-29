package gamefield.yourdays

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gamefield.yourdays.ui.fragments.screens.MainScreenFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_screen_container, MainScreenFragment.newInstance())
            .commitNow()
    }
}
