package gamefield.yourdays.extensions

import android.view.View
import gamefield.yourdays.utils.ActionThrottler

const val RIPPLE_EFFECT_DELAY_MS = 150L

fun View.setOnRippleClickListener(listener: () -> Unit) {
    setOnClickListener {
        ActionThrottler.throttleAction {
            postDelayed({ if (hasWindowFocus()) listener() }, RIPPLE_EFFECT_DELAY_MS)
        }
    }
}
