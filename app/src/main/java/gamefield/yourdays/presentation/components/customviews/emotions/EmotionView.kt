package gamefield.yourdays.presentation.components.customviews.emotions

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import gamefield.yourdays.R

abstract class EmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val alphaDivider: Float = 100f,
    forceLightenTheme: Boolean
) : View(context, attrs, defStyleAttr) {

    val strokePaintWidth = context.resources.getDimension(R.dimen.common_stroke_width)

    val horizontalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val verticalPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(
            if (!forceLightenTheme)
                R.color.common_stroke_color
            else
                R.color.dark_color
        )
        style = Paint.Style.STROKE;
        strokeWidth = strokePaintWidth;
        isAntiAlias = true
    }

    val invisiblePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(
            if (!forceLightenTheme)
                R.color.invisible_color
            else
                R.color.white_text_color
        )
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
        isAntiAlias = true
    }

    var isDrawStroke = false

    var worry = 0
        set(value) {
            field = value
            calculateHorizontalGradient()
        }
    var happiness = 0
        set(value) {
            field = value
            calculateHorizontalGradient()
        }
    var sadness = 0
        set(value) {
            field = value
            calculateVerticalGradient()
        }
    var productivity = 0
        set(value) {
            field = value
            calculateVerticalGradient()
        }

    val worryColor = context.getColor(R.color.worry)
    val happinessColor = context.getColor(R.color.happiness)
    val sadnessColor = context.getColor(R.color.sadness)
    val productivityColor = context.getColor(R.color.productivity)

    fun copyEmotions(prevEmotion: EmotionView) {
        this.worry = prevEmotion.worry
        this.happiness = prevEmotion.happiness
        this.sadness = prevEmotion.sadness
        this.productivity = prevEmotion.productivity
    }

    open fun calculateHorizontalGradient() {
        horizontalPaint.shader = LinearGradient(
            0f,
            height.toFloat(),
            0f,
            0f,
            Color.argb(
                (happiness / alphaDivider * 255).toInt(),
                happinessColor.red,
                happinessColor.green,
                happinessColor.blue
            ),
            Color.argb(
                (worry / alphaDivider * 255).toInt(),
                worryColor.red,
                worryColor.green,
                worryColor.blue
            ),
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    open fun calculateVerticalGradient() {
        verticalPaint.shader = LinearGradient(
            width / 2f + (width / 2f),
            0f,
            width / 2f - (width / 2f),
            0f,
            Color.argb(
                (productivity / alphaDivider * 255).toInt(),
                productivityColor.red,
                productivityColor.green,
                productivityColor.blue
            ),
            Color.argb(
                (sadness / alphaDivider * 255).toInt(),
                sadnessColor.red,
                sadnessColor.green,
                sadnessColor.blue
            ),
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateVerticalGradient()
        calculateHorizontalGradient()
    }

}
