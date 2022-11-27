package gamefield.yourdays.ui.customviews.emotions

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

class ZeroEmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EmotionView(context, attrs, defStyleAttr, ALPHA_DIVIDER) {

    private companion object {
        const val ALPHA_DIVIDER = 100f
    }

    private var circleX = 0f
    private var circleY = 0f
    private var radius = 0f
    private var innerRadius = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleX = width / 2f
        circleY = height / 2f
        radius = width / 2.1f
        innerRadius = width / 4.1f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(circleX, circleY, radius, verticalPaint)
        canvas?.drawCircle(circleX, circleY, radius, horizontalPaint)
        canvas?.drawCircle(circleX, circleY, innerRadius, invisiblePaint)
        if (isDrawStroke) {
            canvas?.drawCircle(circleX, circleY, radius, strokePaint)
            canvas?.drawCircle(circleX, circleY, innerRadius, strokePaint)
        }
    }

}
