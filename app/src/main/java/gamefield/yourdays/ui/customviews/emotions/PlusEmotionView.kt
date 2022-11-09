package gamefield.yourdays.ui.customviews.emotions

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

class PlusEmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : EmotionView(context, attrs, defStyleAttr, 200f) {


    private var drawLeftHorizontal = 0f
    private var drawRightHorizontal = 0f
    private var drawTopHorizontal = 0f
    private var drawBottomHorizontal = 0f

    private var drawLeftVertical = 0f
    private var drawRightVertical = 0f
    private var drawTopVertical = 0f
    private var drawBottomVertical = 0f

    private var rectangleCornerRadius = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectangleCornerRadius = height  / 40f

        drawLeftHorizontal = width / 2f + (width / 8f)
        drawTopHorizontal = height / 2f + (height / 2.1f)
        drawRightHorizontal = width / 2f - (width / 8f)
        drawBottomHorizontal = height / 2f - (height / 2.1f)

        drawLeftVertical = width / 2f + (width / 2.1f)
        drawTopVertical = height / 2f + (height / 8f)
        drawRightVertical = width / 2f - (width / 2.1f)
        drawBottomVertical = height / 2f - (height / 8f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(
            drawLeftHorizontal,
            drawTopHorizontal,
            drawRightHorizontal,
            drawBottomHorizontal,
            rectangleCornerRadius,
            rectangleCornerRadius,
            if (!isDrawStroke) horizontalPaint else strokePaint
        )
        canvas?.drawRoundRect(
            drawLeftVertical,
            drawTopVertical,
            drawRightVertical,
            drawBottomVertical,
            rectangleCornerRadius,
            rectangleCornerRadius,
            invisiblePaint
        )
        canvas?.drawRoundRect(
            drawLeftVertical,
            drawTopVertical,
            drawRightVertical,
            drawBottomVertical,
            rectangleCornerRadius,
            rectangleCornerRadius,
            if (!isDrawStroke) verticalPaint else strokePaint
        )
        canvas?.drawRoundRect(
            drawLeftHorizontal - strokePaintWidth / 2,
            drawTopHorizontal - strokePaintWidth,
            drawRightHorizontal + strokePaintWidth / 2,
            drawBottomHorizontal + strokePaintWidth,
            rectangleCornerRadius,
            rectangleCornerRadius,
            invisiblePaint
        )
        canvas?.drawRoundRect(
            drawLeftHorizontal - strokePaintWidth / 2,
            drawTopHorizontal - strokePaintWidth / 2,
            drawRightHorizontal + strokePaintWidth / 2,
            drawBottomHorizontal + strokePaintWidth / 2,
            rectangleCornerRadius,
            rectangleCornerRadius,
            horizontalPaint
        )
        canvas?.drawRoundRect(
            drawLeftVertical  - strokePaintWidth / 2,
            drawTopVertical - strokePaintWidth / 2,
            drawRightVertical  + strokePaintWidth / 2,
            drawBottomVertical + strokePaintWidth / 2,
            rectangleCornerRadius,
            rectangleCornerRadius,
            verticalPaint
        )
    }

}
