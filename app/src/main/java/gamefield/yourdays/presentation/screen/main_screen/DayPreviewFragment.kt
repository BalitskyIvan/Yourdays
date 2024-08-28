package gamefield.yourdays.presentation.screen.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gamefield.yourdays.databinding.FragmentDayPreviewFragmentBinding
import gamefield.yourdays.domain.models.EmotionType
import gamefield.yourdays.extensions.parseEmotionInEmotionView
import gamefield.yourdays.presentation.components.customviews.emotions.EmotionView
import gamefield.yourdays.presentation.components.customviews.emotions.EmptyEmotionView
import gamefield.yourdays.presentation.components.customviews.emotions.MinusEmotionView
import gamefield.yourdays.presentation.components.customviews.emotions.PlusEmotionView
import gamefield.yourdays.presentation.components.customviews.emotions.ZeroEmotionView
import gamefield.yourdays.presentation.screen.export_screen.view_model.ExportToInstagramViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DayPreviewFragment : Fragment() {

    private val viewModel: ExportToInstagramViewModel by viewModel()
    private lateinit var binding: FragmentDayPreviewFragmentBinding

    private lateinit var emptyEmotionView: EmptyEmotionView
    private lateinit var plusEmotionView: PlusEmotionView
    private lateinit var minusEmotionView: MinusEmotionView
    private lateinit var zeroEmotionView: ZeroEmotionView

    private var currentEmotion: EmotionView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDayPreviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emptyEmotionView = EmptyEmotionView(context = requireContext(), forceLightenTheme = true)
        plusEmotionView = PlusEmotionView(context = requireContext(), forceLightenTheme = true)
        minusEmotionView = MinusEmotionView(context = requireContext(), forceLightenTheme = true)
        zeroEmotionView = ZeroEmotionView(context = requireContext(), forceLightenTheme = true)

        binding.dayPreviewEmotionContainer.addView(emptyEmotionView)
        observeDayChangedEvent()
    }

    private fun observeDayChangedEvent() {
        viewModel.currentDayInPreviewChanged.observe(viewLifecycleOwner) { data ->
            with(binding) {
                dayPreviewDateText.text = data.first

                dayPreviewWorryProgress.isEnabled = false
                dayPreviewHappinessProgress.isEnabled = false
                dayPreviewSadnessProgress.isEnabled = false
                dayPreviewProductivityProgress.isEnabled = false

                currentEmotion = if (data.second == null) {
                    null
                } else {

                    when (data.second!!.type) {
                        EmotionType.NONE -> null
                        EmotionType.PLUS -> plusEmotionView
                        EmotionType.MINUS -> minusEmotionView
                        EmotionType.ZERO -> zeroEmotionView
                    }
                }

                if (currentEmotion != null) {
                    with(currentEmotion!!) {
                        parseEmotionInEmotionView(data.second!!)

                        dayPreviewWorryScore.text =
                            EMOTION_PROGRESS.format(worry / 10)
                        dayPreviewHappinessScore.text =
                            EMOTION_PROGRESS.format(happiness / 10)
                        dayPreviewSadnessScore.text =
                            EMOTION_PROGRESS.format(sadness / 10)
                        dayPreviewProductivityScore.text =
                            EMOTION_PROGRESS.format(productivity / 10)

                        dayPreviewWorryProgress.progress = worry
                        dayPreviewHappinessProgress.progress = happiness
                        dayPreviewSadnessProgress.progress = sadness
                        dayPreviewProductivityProgress.progress = productivity

                        dayPreviewWorryEmoji.visibility = worry.getVisibility()
                        dayPreviewWorryProgress.visibility = worry.getVisibility()
                        dayPreviewWorryScore.visibility = worry.getVisibility()
                        dayPreviewWorryTitle.visibility = worry.getVisibility()

                        dayPreviewHappinessEmoji.visibility = happiness.getVisibility()
                        dayPreviewHappinessProgress.visibility = happiness.getVisibility()
                        dayPreviewHappinessScore.visibility = happiness.getVisibility()
                        dayPreviewHappinessTitle.visibility = happiness.getVisibility()

                        dayPreviewSadnessEmoji.visibility = sadness.getVisibility()
                        dayPreviewSadnessProgress.visibility = sadness.getVisibility()
                        dayPreviewSadnessScore.visibility = sadness.getVisibility()
                        dayPreviewSadnessTitle.visibility = sadness.getVisibility()

                        dayPreviewProductivityEmoji.visibility = productivity.getVisibility()
                        dayPreviewProductivityProgress.visibility = productivity.getVisibility()
                        dayPreviewProductivityScore.visibility = productivity.getVisibility()
                        dayPreviewProductivityTitle.visibility = productivity.getVisibility()
                    }
                } else {
                    dayPreviewWorryScore.text = EMOTION_PROGRESS.format(0)
                    dayPreviewHappinessScore.text = EMOTION_PROGRESS.format(0)
                    dayPreviewSadnessScore.text = EMOTION_PROGRESS.format(0)
                    dayPreviewProductivityScore.text = EMOTION_PROGRESS.format(0)

                    dayPreviewWorryProgress.progress = 0
                    dayPreviewHappinessProgress.progress = 0
                    dayPreviewSadnessProgress.progress = 0
                    dayPreviewProductivityProgress.progress = 0
                }

                dayPreviewEmotionContainer.removeAllViews()
                dayPreviewEmotionContainer.addView(currentEmotion ?: emptyEmotionView)
            }
        }
    }

    private fun Int.getVisibility(): Int = if (this == 0) View.GONE else View.VISIBLE

    companion object {
        private const val EMOTION_PROGRESS = "%s/10"
    }
}
