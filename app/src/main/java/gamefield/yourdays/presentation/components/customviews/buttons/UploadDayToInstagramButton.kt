package gamefield.yourdays.presentation.components.customviews.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import gamefield.yourdays.R
import gamefield.yourdays.databinding.ViewUploadDayToInstagramButtonBinding

class UploadDayToInstagramButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewUploadDayToInstagramButtonBinding

    init {
        binding = ViewUploadDayToInstagramButtonBinding.inflate(LayoutInflater.from(context), this)
        focusable = FOCUSABLE
        isClickable = true
        background = AppCompatResources.getDrawable(
            context,
            R.drawable.button_upload_day_to_stories_background
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                binding.buttonUploadToStoriesText.setTextColor(context.getColor(R.color.invisible_color))
            }

            MotionEvent.ACTION_UP -> {
                binding.buttonUploadToStoriesText.setTextColor(context.getColor(R.color.common_text_color))
            }
        }
        return super.onTouchEvent(event)
    }

}