package gamefield.yourdays.ui.customviews.emotions

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

abstract class EmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val alphaDivider: Float = 100f
) : View(context, attrs, defStyleAttr) {

    val strokePaintWidth = 3F

    val horizontalPaint = Paint(Paint.FILTER_BITMAP_FLAG)

    val verticalPaint = Paint(Paint.FILTER_BITMAP_FLAG)

    val strokePaint = Paint(Paint.FILTER_BITMAP_FLAG).apply {
        color = Color.argb(0.5f, 0f, 0f, 0f);
        style = Paint.Style.STROKE;
        strokeWidth = strokePaintWidth;
        isAntiAlias = true;
    }

    val invisiblePaint = Paint(Paint.FILTER_BITMAP_FLAG).apply {
        color = Color.WHITE
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

    private fun calculateHorizontalGradient() {
        horizontalPaint.shader = LinearGradient(
            0f,
            height.toFloat(),
            0f,
            0f,
            Color.argb(joy / alphaDivider, 235 / 255f, 255 / 255f, 0 / 255f),
            Color.argb(anxiety / alphaDivider, 255 / 255f, 20 / 255f, 20 / 255f),
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
            Color.argb(calmness / alphaDivider, 125 / 255f, 255 / 255f, 216 / 255f),
            Color.argb(sadness / alphaDivider, 20 / 255f, 0 / 255f, 255 / 255f),
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
