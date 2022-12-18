package gamefield.yourdays.ui.customviews.emotions

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import gamefield.yourdays.databinding.ViewEmptyEmotionBinding
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.viewmodels.EmptyEmotionViewViewModel

class EmptyEmotionView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    forceLightenTheme: Boolean = false
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewEmptyEmotionBinding.inflate(LayoutInflater.from(context), this)

    private val lifecycleOwner = context as LifecycleOwner

    private val viewModel = ViewModelProvider(context as ViewModelStoreOwner).get(EmptyEmotionViewViewModel::class.java)

    private val minusEmotionView: MinusEmotionView = MinusEmotionView(context = context, forceLightenTheme = forceLightenTheme).apply { isDrawStroke = true }
    private val plusEmotionView: PlusEmotionView = PlusEmotionView(context = context, forceLightenTheme = forceLightenTheme).apply { isDrawStroke = true }
    private val zeroEmotionView: ZeroEmotionView = ZeroEmotionView(context = context, forceLightenTheme = forceLightenTheme).apply { isDrawStroke = true }
    private var currentEmotion: EmotionView = plusEmotionView

    init {
        binding.emptyEmotionContainer.addView(plusEmotionView)

        viewModel.currentEmotionType.observe(lifecycleOwner) { newEmotionType ->
            binding.emptyEmotionContainer.removeView(currentEmotion)
            val nextEmotion = when (newEmotionType) {
                EmotionType.PLUS -> plusEmotionView
                EmotionType.ZERO -> zeroEmotionView
                EmotionType.MINUS -> minusEmotionView
                else -> plusEmotionView
            }
            currentEmotion = nextEmotion
            binding.emptyEmotionContainer.addView(currentEmotion)
        }
        viewModel.emotionContainerAlpha.observe(lifecycleOwner) { alpha ->
            binding.emptyEmotionContainer.alpha = alpha
        }
    }

}
