package gamefield.yourdays.presentation.components.customviews.emotions

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

class PlusEmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    forceLightenTheme: Boolean = false
) : EmotionView(context, attrs, defStyleAttr, 100f, forceLightenTheme) {


    private var drawLeftHorizontal = 0f
    private var drawRightHorizontal = 0f
    private var drawTopHorizontal = 0f
    private var drawBottomHorizontal = 0f

    private var drawLeftVertical = 0f
    private var drawRightVertical = 0f
    private var drawTopVertical = 0f
    private var drawBottomVertical = 0f

    private var rectangleCornerRadius = 0f

    private val backgroundVerticalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val backgroundHorizontalPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun calculateVerticalGradient() {
        super.calculateVerticalGradient()
        backgroundVerticalPaint.shader = LinearGradient(
            width / 2f + (width / 2f),
            0f,
            width / 2f - (width / 2f),
            0f,
            Color.argb(
                (productivity / BACKGROUND_ALPHA_DIVIDER * 255).toInt(),
                productivityColor.red,
                productivityColor.green,
                productivityColor.blue
            ),
            Color.argb(
                (sadness / BACKGROUND_ALPHA_DIVIDER * 255).toInt(),
                sadnessColor.red,
                sadnessColor.green,
                sadnessColor.blue
            ),
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    override fun calculateHorizontalGradient() {
        super.calculateHorizontalGradient()
        backgroundHorizontalPaint.shader = LinearGradient(
            0f,
            height.toFloat(),
            0f,
            0f,
            Color.argb(
                (happiness / BACKGROUND_ALPHA_DIVIDER * 255).toInt(),
                happinessColor.red,
                happinessColor.green,
                happinessColor.blue

            ),
            Color.argb(
                (worry / BACKGROUND_ALPHA_DIVIDER * 255).toInt(),
                worryColor.red,
                worryColor.green,
                worryColor.blue
            ),
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectangleCornerRadius = height / 40f

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
        calculateHorizontalGradient()
        calculateVerticalGradient()
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
        if (happiness != 0 || worry != 0 || sadness != 0 || productivity != 0) {
            canvas?.drawRoundRect(
                drawLeftHorizontal - strokePaintWidth / 2,
                drawTopHorizontal - strokePaintWidth / 2,
                drawRightHorizontal + strokePaintWidth / 2,
                drawBottomHorizontal + strokePaintWidth / 2,
                rectangleCornerRadius,
                rectangleCornerRadius,
                backgroundVerticalPaint
            )
            canvas?.drawRoundRect(
                drawLeftVertical - strokePaintWidth / 2,
                drawTopVertical - strokePaintWidth / 2,
                drawRightVertical + strokePaintWidth / 2,
                drawBottomVertical + strokePaintWidth / 2,
                rectangleCornerRadius,
                rectangleCornerRadius,
                backgroundHorizontalPaint
            )
        }
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
            drawLeftVertical - strokePaintWidth / 2,
            drawTopVertical - strokePaintWidth / 2,
            drawRightVertical + strokePaintWidth / 2,
            drawBottomVertical + strokePaintWidth / 2,
            rectangleCornerRadius,
            rectangleCornerRadius,
            verticalPaint
        )
    }

    private companion object {
        const val BACKGROUND_ALPHA_DIVIDER = 100f
    }
}
