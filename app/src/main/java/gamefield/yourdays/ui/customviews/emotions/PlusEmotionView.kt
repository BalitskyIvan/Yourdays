package gamefield.yourdays.ui.customviews.emotions

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class PlusEmotionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    var size = 100

    init {
        paint.shader = LinearGradient(0f, 0f, width.toFloat(), height.toFloat(), Color.YELLOW, Color.RED, Shader.TileMode.MIRROR)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(
            width / 2f + (width / 800f) * size,
            height / 2f + (height / 200f) * size,
            width / 2f - (width / 800f) * size,
            height / 2f - (height / 200f) * size,
            paint
        )
        canvas?.drawRect(
            width / 2f + (width / 200f) * size,
            height / 2f + (height / 800f) * size,
            width / 2f - (width / 200f) * size,
            height / 2f - (height / 800f) * size,
            paint
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}