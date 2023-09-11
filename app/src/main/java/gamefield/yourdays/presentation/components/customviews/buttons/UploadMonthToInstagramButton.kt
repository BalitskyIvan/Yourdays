package gamefield.yourdays.presentation.components.customviews.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import gamefield.yourdays.R
import gamefield.yourdays.databinding.ViewUploadMonthToInstagramButtonBinding

class UploadMonthToInstagramButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: ViewUploadMonthToInstagramButtonBinding

    init {
        binding =
            ViewUploadMonthToInstagramButtonBinding.inflate(LayoutInflater.from(context), this)
        focusable = FOCUSABLE
        isClickable = true
        background = AppCompatResources.getDrawable(
            context,
            R.drawable.button_upload_day_to_stories_background
        )
    }

}
