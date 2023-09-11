package gamefield.yourdays.presentation.screen.main_screen.utils

import android.os.SystemClock
import gamefield.yourdays.extensions.RIPPLE_EFFECT_DELAY_MS

object ActionThrottler {

    private const val PREVENTION_OF_CLICK_AGAIN_COEFFICIENT = 2
    private const val DELAY_MS = PREVENTION_OF_CLICK_AGAIN_COEFFICIENT * RIPPLE_EFFECT_DELAY_MS
    private var lastActionTime = 0L

    fun throttleAction(action: () -> Unit): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        val diff = currentTime - lastActionTime

        return if (diff >= DELAY_MS) {
            lastActionTime = currentTime
            action.invoke()
            true
        } else {
            false
        }
    }

}
