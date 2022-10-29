package gamefield.yourdays.ui.customviews.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import gamefield.yourdays.R
import gamefield.yourdays.databinding.ViewOkButtonBinding

class OkButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewOkButtonBinding

    init {
        binding = ViewOkButtonBinding.inflate(LayoutInflater.from(context), this)
        focusable = FOCUSABLE
        isClickable = true
        background = context.getDrawable(R.drawable.button_ok_background)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                binding.buttonOkText.postDelayed({ binding.buttonOkText.setTextColor(context.getColor(R.color.white)) }, 50)

            }
            MotionEvent.ACTION_UP -> {
                binding.buttonOkText.postDelayed({ binding.buttonOkText.setTextColor(context.getColor(R.color.black)) }, 150)
            }
        }
        return super.onTouchEvent(event)
    }

}
