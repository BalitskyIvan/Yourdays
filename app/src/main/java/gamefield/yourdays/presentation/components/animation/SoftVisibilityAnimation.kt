package gamefield.yourdays.presentation.components.animation

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData

class SoftVisibilityAnimation(
    private val alphaLiveData: MutableLiveData<Float>,
    startState: State
) {

    private var currentState: State = startState
    private var alreadyDisappeared = false

    fun start(state: State) {
        if (currentState == state)
            return
        currentState = state
        when (state) {
            State.APPEAR -> {
                startAnimation(INVISIBLE_ALPHA, VISIBLE_ALPHA)
            }

            State.DISAPPEAR -> {
                alreadyDisappeared = true
                startAnimation(VISIBLE_ALPHA, INVISIBLE_ALPHA)
            }

            State.APPEAR_FOR_TWO_SECONDS -> {
                alreadyDisappeared = false
                startAnimation(INVISIBLE_ALPHA, VISIBLE_ALPHA)
                Handler(Looper.getMainLooper()).postDelayed({
                    if (!alreadyDisappeared) {
                        currentState = State.DISAPPEAR
                        startAnimation(VISIBLE_ALPHA, INVISIBLE_ALPHA)
                    }
                }, 4000)
            }
        }
    }

    private fun startAnimation(startAlpha: Float, endAlpha: Float) {
        ValueAnimator
            .ofFloat(startAlpha, endAlpha)
            .setDuration(600).apply {
                addUpdateListener { alpha ->
                    alphaLiveData.postValue(alpha.animatedValue as Float)
                }
            }
            .start()
    }

    private companion object {
        const val INVISIBLE_ALPHA = 0f
        const val VISIBLE_ALPHA = 1f
    }

    enum class State {
        APPEAR,
        APPEAR_FOR_TWO_SECONDS,
        DISAPPEAR
    }
}