package gamefield.yourdays.presentation.components.animation

import android.animation.ValueAnimator
import android.content.res.Resources
import android.widget.TextView
import gamefield.yourdays.R

class DateTitleAnimation(
    private val titleFirstView: TextView,
    private val titleSecondView: TextView,
    resources: Resources
) {

    private val topPositionY: Float = resources.getDimension(R.dimen.top_position_y)
    private val bottomPositionY: Float = resources.getDimension(R.dimen.bottom_position_y)
    private val centerPositionY: Float = resources.getDimension(R.dimen.center_position_y)

    private val topTitleTextSize: Int = resources.getDimension(R.dimen.top_title_text_size).toInt()
    private val bottomTitleTextSize: Int =
        resources.getDimension(R.dimen.bottom_title_text_size).toInt()

    private var firstTitleEndPositionY: Float = topPositionY
    private var secondTitleEndPositionY: Float = bottomPositionY

    private var firstTitleEndTextSize: Int = topTitleTextSize
    private var secondTitleEndTextSize: Int = bottomTitleTextSize

    private var firstTitleEndAlpha: Float = VISIBLE
    private var secondTitleEndAlpha: Float = VISIBLE

    private var currentAnimationState: AnimationState? = null

    private var currentDateView: TextView = titleFirstView

    fun start(animationState: AnimationState, isFirstViewDate: Boolean) {
        if (currentAnimationState != null)
            endPreviousAnimation()
        swapText(isFirstViewDate)
        currentAnimationState = animationState
        calculateFirstAndSecondTitlePositions()

        initAnimation(
            titleFirstView,
            firstTitleEndPositionY,
            firstTitleEndTextSize,
            firstTitleEndAlpha
        )
        initAnimation(
            titleSecondView,
            secondTitleEndPositionY,
            secondTitleEndTextSize,
            secondTitleEndAlpha
        )
    }

    private fun initAnimation(
        textView: TextView,
        endYPosition: Float,
        endTextSize: Int,
        endAlpha: Float
    ) {
        ValueAnimator
            .ofFloat(textView.y, endYPosition)
            .setDuration(DURATION_MS)
            .apply {
                addUpdateListener {
                    textView.y = it.animatedValue as Float
                }
            }
            .start()
        ValueAnimator
            .ofInt(textView.layoutParams.height, endTextSize)
            .setDuration(DURATION_MS)
            .apply {
                addUpdateListener {
                    textView.layoutParams.height = it.animatedValue as Int
                    textView.requestLayout()
                }
            }
            .start()
        ValueAnimator
            .ofFloat(textView.alpha, endAlpha)
            .setDuration(DURATION_MS)
            .apply {
                addUpdateListener {
                    textView.alpha = it.animatedValue as Float
                }
            }
            .start()
    }

    fun setDateTitle(newDate: String) {
        currentDateView.text = newDate
    }

    private fun calculateFirstAndSecondTitlePositions() {
        when (currentAnimationState) {
            AnimationState.EMPTY_EMOTION_POSITIONS -> {
                firstTitleEndPositionY = topPositionY
                secondTitleEndPositionY = bottomPositionY

                firstTitleEndTextSize = topTitleTextSize
                secondTitleEndTextSize = bottomTitleTextSize

                firstTitleEndAlpha = VISIBLE
                secondTitleEndAlpha = VISIBLE
            }

            AnimationState.FILLING_EMOTION_POSITIONS -> {
                firstTitleEndPositionY = bottomPositionY
                secondTitleEndPositionY = topPositionY

                firstTitleEndTextSize = bottomTitleTextSize
                secondTitleEndTextSize = topTitleTextSize

                firstTitleEndAlpha = VISIBLE
                secondTitleEndAlpha = VISIBLE
            }

            AnimationState.FILLED_EMOTION_POSITIONS -> {
                firstTitleEndPositionY = centerPositionY
                secondTitleEndPositionY = bottomPositionY

                firstTitleEndTextSize = bottomTitleTextSize
                secondTitleEndTextSize = topTitleTextSize

                firstTitleEndAlpha = VISIBLE
                secondTitleEndAlpha = INVISIBLE
            }

            null -> Unit
        }
    }

    private fun endPreviousAnimation() {
        titleFirstView.y = firstTitleEndPositionY
        titleSecondView.y = secondTitleEndPositionY

        titleFirstView.layoutParams.height = firstTitleEndTextSize
        titleSecondView.layoutParams.height = secondTitleEndTextSize
        titleSecondView.requestLayout()
        titleFirstView.requestLayout()

        titleFirstView.alpha = firstTitleEndAlpha
        titleSecondView.alpha = secondTitleEndAlpha

        currentAnimationState = null
    }

    private fun swapText(isFirstViewDate: Boolean) {
        if (isFirstViewDate && currentDateView != titleFirstView ||
            !isFirstViewDate && currentDateView != titleSecondView
        ) {
            val text = titleFirstView.text
            titleFirstView.text = titleSecondView.text
            titleSecondView.text = text

            currentDateView = if (isFirstViewDate) titleFirstView else titleSecondView
        }
    }

    private companion object {
        const val DURATION_MS = 600L
        const val VISIBLE = 1f
        const val INVISIBLE = 0f
    }

    enum class AnimationState {
        EMPTY_EMOTION_POSITIONS,
        FILLING_EMOTION_POSITIONS,
        FILLED_EMOTION_POSITIONS
    }

}
