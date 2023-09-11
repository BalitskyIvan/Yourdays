package gamefield.yourdays.presentation.components.customviews.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import gamefield.yourdays.R
import gamefield.yourdays.databinding.ViewOkButtonBinding

class StrokeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewOkButtonBinding

    init {
        binding = ViewOkButtonBinding.inflate(LayoutInflater.from(context), this)
        context.withStyledAttributes(attrs, R.styleable.StrokeButton) {
            binding.buttonOkText.text = getString(R.styleable.StrokeButton_buttonText)
                ?: context.getString(R.string.ok_text)
        }
        focusable = FOCUSABLE
        isClickable = true
        background = context.getDrawable(R.drawable.button_ok_background)
    }

    fun setButtonText(text: String) {
        binding.buttonOkText.text = text
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                binding.buttonOkText.postDelayed(
                    {
                        binding.buttonOkText.setTextColor(
                            context.getColor(
                                R.color.invisible_color
                            )
                        )
                    },
                    50
                )

            }

            MotionEvent.ACTION_UP -> {
                binding.buttonOkText.postDelayed(
                    {
                        binding.buttonOkText.setTextColor(
                            context.getColor(
                                R.color.common_text_color
                            )
                        )
                    },
                    150
                )
            }
        }
        return super.onTouchEvent(event)
    }

}
