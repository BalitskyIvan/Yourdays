package gamefield.yourdays.ui.customviews.emotions

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet

class MinusEmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EmotionView(context, attrs, defStyleAttr, ALPHA_DIVIDER) {

    private companion object {
        const val ALPHA_DIVIDER = 200f
    }

    private var drawLeftVertical = 0f
    private var drawRightVertical = 0f
    private var drawTopVertical = 0f
    private var drawBottomVertical = 0f

    private var rectangleCornerRadius = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectangleCornerRadius = height  / 40f

        drawLeftVertical = width / 2f + (width / 2.1f)
        drawTopVertical = height / 2f + (height / 8f)
        drawRightVertical = width / 2f - (width / 2.1f)
        drawBottomVertical = height / 2f - (height / 8f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(
            drawLeftVertical,
            drawTopVertical,
            drawRightVertical,
            drawBottomVertical,
            rectangleCornerRadius,
            rectangleCornerRadius,
            verticalPaint
        )
        canvas?.drawRoundRect(
            drawLeftVertical,
            drawTopVertical,
            drawRightVertical,
            drawBottomVertical,
            rectangleCornerRadius,
            rectangleCornerRadius,
            horizontalPaint
        )
        if (isDrawStroke) {
            canvas?.drawRoundRect(
                drawLeftVertical,
                drawTopVertical,
                drawRightVertical,
                drawBottomVertical,
                rectangleCornerRadius,
                rectangleCornerRadius,
                strokePaint
            )
        }
    }
}
