package gamefield.yourdays.utils.animation

import android.animation.ValueAnimator
import android.widget.TextView

class DateTitleAnimation(
    private val titleFirstView: TextView,
    private val titleSecondView: TextView
) : Animation {

    private val titleFirstViewFirstY: Float = titleFirstView.y
    private val titleSecondViewFirstY: Float = titleSecondView.y

    override fun start() {
        val titleFirstY = titleFirstView.y
        val titleSecondY = titleSecondView.y
        ValueAnimator
            .ofFloat(titleFirstY, titleSecondY)
            .setDuration(600).apply {
                addUpdateListener {
                    titleSecondView.y = it.animatedValue as Float
                }
            }
            .start()
        ValueAnimator
            .ofFloat(titleSecondY, titleFirstY)
            .setDuration(500)
            .apply {
                addUpdateListener {
                    titleFirstView.y = it.animatedValue as Float
                }
            }
            .start()
        val text = titleFirstView.text
        titleFirstView.text = titleSecondView.text
        titleSecondView.text = text
    }

}
