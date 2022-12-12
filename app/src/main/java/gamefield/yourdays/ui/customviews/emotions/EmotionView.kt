package gamefield.yourdays.ui.customviews.emotions

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
    private val alphaDivider: Float = 100f
) : View(context, attrs, defStyleAttr) {

    val strokePaintWidth = context.resources.getDimension(R.dimen.common_stroke_width)

    val horizontalPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val verticalPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val strokePaint = Paint(Paint.FILTER_BITMAP_FLAG).apply {
        color = context.getColor(R.color.common_stroke_color)
        style = Paint.Style.STROKE;
        strokeWidth = strokePaintWidth;
        isAntiAlias = true;
    }

    val invisiblePaint = Paint(Paint.FILTER_BITMAP_FLAG).apply {
        color = context.getColor(R.color.invisible_color)
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER);
    }

    var isDrawStroke = false

    var anxiety = 0
        set(value) {
            field = value
            calculateHorizontalGradient()
        }
    var joy = 0
        set(value) {
            field = value
            calculateHorizontalGradient()
        }
    var sadness = 0
        set(value) {
            field = value
            calculateVerticalGradient()
        }
    var calmness = 0
        set(value) {
            field = value
            calculateVerticalGradient()
        }

    private val anxietyColor = context.getColor(R.color.worry)
    private val joyColor = context.getColor(R.color.happiness)
    private val sadnessColor = context.getColor(R.color.sadness)
    private val calmnessColor = context.getColor(R.color.productivity)

    fun copyEmotions(prevEmotion: EmotionView) {
        this.anxiety = prevEmotion.anxiety
        this.joy = prevEmotion.joy
        this.sadness = prevEmotion.sadness
        this.calmness = prevEmotion.calmness
    }

    private fun calculateHorizontalGradient() {
        horizontalPaint.shader = LinearGradient(
            0f,
            height.toFloat(),
            0f,
            0f,
            Color.argb((joy / alphaDivider * 255).toInt(), joyColor.red, joyColor.green, joyColor.blue),
            Color.argb((anxiety / alphaDivider * 255).toInt(), anxietyColor.red, anxietyColor.green, anxietyColor.blue),
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    private fun calculateVerticalGradient() {
        verticalPaint.shader = LinearGradient(
            width / 2f + (width / 2f),
            0f,
            width / 2f - (width / 2f),
            0f,
            Color.argb((calmness / alphaDivider * 255).toInt(), calmnessColor.red, calmnessColor.green, calmnessColor.blue),
            Color.argb((sadness / alphaDivider * 255).toInt(), sadnessColor.red, sadnessColor.green, sadnessColor.blue),
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateVerticalGradient()
        calculateHorizontalGradient()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

}
