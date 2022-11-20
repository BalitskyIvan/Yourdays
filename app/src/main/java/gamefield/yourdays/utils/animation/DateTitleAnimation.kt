package gamefield.yourdays.utils.animation

import android.animation.ValueAnimator
import android.widget.TextView
import androidx.core.animation.doOnEnd

class DateTitleAnimation(
    private val titleFirstView: TextView,
    private val titleSecondView: TextView
) : Animation {

    private var titleFirstViewFirstY: Float = titleFirstView.y
    private var titleSecondViewFirstY: Float = titleSecondView.y

    private var isAnimationInProgress = false

    private var currentDateTitle: TextView = titleFirstView

    override fun start() {
        if (isAnimationInProgress)
            endPreviousAnimation()

        titleFirstViewFirstY = titleFirstView.y
        titleSecondViewFirstY = titleSecondView.y

        isAnimationInProgress = true
        ValueAnimator
            .ofFloat(titleFirstViewFirstY, titleSecondViewFirstY)
            .setDuration(600).apply {
                addUpdateListener {
                    titleSecondView.y = it.animatedValue as Float
                }
                doOnEnd {
                    isAnimationInProgress = false
                }
            }
            .start()
        ValueAnimator
            .ofFloat(titleSecondViewFirstY, titleFirstViewFirstY)
            .setDuration(500)
            .apply {
                addUpdateListener {
                    titleFirstView.y = it.animatedValue as Float
                }
            }
            .start()
        swapText()
    }

    fun setDateTitle(newDate: String) {
        currentDateTitle.text = newDate
    }

    private fun endPreviousAnimation() {
        titleSecondView.y = titleSecondViewFirstY
        titleFirstView.y = titleFirstViewFirstY
        isAnimationInProgress = false
    }

    private fun swapText() {
        val text = titleFirstView.text
        titleFirstView.text = titleSecondView.text
        titleSecondView.text = text
        currentDateTitle = if (currentDateTitle == titleFirstView) titleSecondView else titleFirstView
    }

}
