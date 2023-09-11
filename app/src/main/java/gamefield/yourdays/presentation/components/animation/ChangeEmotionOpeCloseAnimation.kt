package gamefield.yourdays.presentation.components.animation

import android.animation.ValueAnimator
import android.content.res.Resources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import gamefield.yourdays.R

class ChangeEmotionOpeCloseAnimation(
    private val resources: Resources,
    private val rootLayout: ConstraintLayout,
    private val animationEndAction: (Boolean) -> Unit
) : Animation {

    private var isOpenAction: Boolean = true

    override fun start() {
        var startHeight = 0
        var endHeight =
            resources.getDimension(R.dimen.change_emotion_container_height)
                .toInt()
        var startAlpha = 0f
        var endAlpha = 1f
        var alphaDuration = 900L

        if (!isOpenAction) {
            startHeight = endHeight
            endHeight = 0
            startAlpha = endAlpha
            endAlpha = 0f
            alphaDuration = 600L
        }

        ValueAnimator
            .ofInt(startHeight, endHeight)
            .setDuration(600).apply {
                addUpdateListener {
                    rootLayout.layoutParams.height = it.animatedValue as Int
                    rootLayout.requestLayout()
                }
                doOnEnd {
                    animationEndAction.invoke(isOpenAction)
                }
            }
            .start()
        ValueAnimator
            .ofFloat(startAlpha, endAlpha)
            .setDuration(alphaDuration)
            .apply {
                addUpdateListener {
                    rootLayout.alpha = it.animatedValue as Float
                    rootLayout.invalidate()
                }
            }
            .start()
        isOpenAction = !isOpenAction
    }

}
